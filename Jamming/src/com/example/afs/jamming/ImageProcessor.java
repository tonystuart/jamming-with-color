// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.example.afs.jamming.Trace.TraceOption;

public class ImageProcessor {

  private class Task implements Callable<List<Item>> {

    private int firstRow;
    private List<Item> items;
    private int lastRow;

    public Task(int firstRow, int lastRow) {
      this.firstRow = firstRow;
      this.lastRow = lastRow;
    }

    @Override
    public List<Item> call() throws Exception {
      items = new ItemFinder().findItems(image, firstRow, lastRow, options.getBackgroundCondition(), options.getBackgroundThreshold(), options.getObjectMinimumSize());
      return items;
    }

    public int getFirstRow() {
      return firstRow;
    }

    public List<Item> getItems() {
      return items;
    }

    public int getLastRow() {
      return lastRow;
    }

    @Override
    public String toString() {
      return "Task [firstRow=" + firstRow + ", lastRow=" + lastRow + ", items=" + items + "]";
    }

  }

  private class TimeKeeper {
    private long startTimeNs;
    private int threadCount;

    public TimeKeeper(int threadCount) {
      this.threadCount = threadCount;
      if (options.getTrace().isSet(TraceOption.PERFORMANCE)) {
        startTimeNs = System.nanoTime();
      }
    }

    public void report() {
      if (options.getTrace().isSet(TraceOption.PERFORMANCE)) {
        long finishTimeNs = System.nanoTime();
        long elapsedTimeNs = finishTimeNs - startTimeNs;
        long elaspedTimeMs = TimeUnit.NANOSECONDS.toMillis(elapsedTimeNs);
        long pixelCount = (long) image.getWidth() * (long) image.getHeight();
        System.out.println("Processed " + pixelCount + " pixels in " + elaspedTimeMs + " milliseconds using " + threadCount + " thread(s)");
      }
    }
  }

  private ArrayList<Item> adjacentItemFlyweight = new ArrayList<>();
  private BufferedImage image;
  private Options options;

  public ImageProcessor(BufferedImage image, Options options) {
    this.image = image;
    this.options = options;
  }

  public int despeckle(List<Item> items, int minimumSize) {
    int despeckleCount = 0;
    Iterator<Item> iterator = items.iterator();
    while (iterator.hasNext()) {
      Item item = iterator.next();
      if (item.getWidth() < minimumSize || item.getHeight() < minimumSize) {
        for (Extent extent : item.getExtents()) {
          int endX = extent.getEndX();
          int y = extent.getY();
          for (int x = extent.getStartX(); x <= endX; x++) {
            image.setRGB(x, y, 0);
          }
        }
        iterator.remove();
        despeckleCount++;
      }
    }
    return despeckleCount;
  }

  public List<Item> extractItems() {
    try {
      int threadCount = options.getThreads();
      List<Item> items;
      if (threadCount == 0) {
        threadCount = Runtime.getRuntime().availableProcessors();
      }
      TimeKeeper timeKeeper = new TimeKeeper(threadCount);
      if (threadCount == 1) {
        items = extractSerial();
      } else {
        items = extractParallel(threadCount);
      }
      timeKeeper.report();
      return items;
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public int getAverageRgb(Item item) {
    int totalRed = 0;
    int totalGreen = 0;
    int totalBlue = 0;
    int totalPixels = 0;
    // Pass 1: calculate average
    for (Extent extent : item.getExtents()) {
      int endX = extent.getEndX();
      int y = extent.getY();
      for (int x = extent.getStartX(); x <= endX; x++) {
        int rgb = image.getRGB(x, y);
        totalRed += Color.getRed(rgb);
        totalGreen += Color.getGreen(rgb);
        totalBlue += Color.getBlue(rgb);
        totalPixels++;
      }
    }
    int averageRed = totalRed / totalPixels;
    int averageGreen = totalGreen / totalPixels;
    int averageBlue = totalBlue / totalPixels;
    int averageRgb = Color.getColor(averageRed, averageGreen, averageBlue);
    return averageRgb;
  }

  public void setAverageRgb(Item item, int averageRgb) {
    for (Extent extent : item.getExtents()) {
      int endX = extent.getEndX();
      int y = extent.getY();
      for (int x = extent.getStartX(); x <= endX; x++) {
        image.setRGB(x, y, averageRgb);
      }
    }
  }

  private List<Item> extractParallel(int threadCount) throws InterruptedException {
    List<Task> tasks = map(threadCount);
    ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
    threadPool.invokeAll(tasks);
    threadPool.shutdown();
    List<Item> items = reduce(tasks);
    return items;
  }

  private List<Item> extractSerial() {
    List<Item> items = new ItemFinder().findItems(image, 0, image.getHeight() - 1, options.getBackgroundCondition(), options.getBackgroundThreshold(), options.getObjectMinimumSize());
    return items;
  }

  private List<Item> findVerticallyAdjacentItems(Item topItem, Task bottomTask) {
    adjacentItemFlyweight.clear();
    Iterator<Item> bottomIterator = bottomTask.getItems().iterator();
    boolean isInRange = true;
    while (bottomIterator.hasNext() && isInRange) {
      Item bottomItem = bottomIterator.next();
      if (bottomItem.getTop() <= bottomTask.getFirstRow() && bottomItem.getLeft() <= topItem.getRight()) {
        if (isVerticallyAdjacent(topItem, bottomItem)) {
          adjacentItemFlyweight.add(bottomItem);
        }
      } else {
        isInRange = false;
      }
    }
    return adjacentItemFlyweight;
  }

  private boolean isVerticallyAdjacent(Item topItem, Item bottomItem) {
    boolean isAdjacent = false;
    int topsBottomIndex = topItem.getBottom();
    Extent topsBottom = topItem.getExtent(topsBottomIndex);
    Extent bottomsTop = bottomItem.getExtent(topsBottomIndex + 1); // because tops get merged into bottoms
    if (topsBottom != null && topsBottom.getStartX() <= bottomsTop.getEndX() && topsBottom.getEndX() >= bottomsTop.getStartX()) {
      isAdjacent = true;
    }
    return isAdjacent;
  }

  private List<Task> map(int threadCount) {
    int taskTop = 0;
    int lastTask = threadCount - 1;
    int imageHeight = image.getHeight();
    int rowsPerTask = imageHeight / threadCount;
    List<Task> tasks = new LinkedList<>();
    for (int i = 0; i < threadCount; i++) {
      int taskHeight = i == lastTask ? imageHeight : taskTop + rowsPerTask;
      int taskBottom = taskHeight - 1; // -1 because bottom is inclusive
      tasks.add(new Task(taskTop, taskBottom));
      taskTop = taskBottom + 1;
    }
    return tasks;
  }

  private List<Item> reduce(List<Task> tasks) {
    int taskCount = tasks.size();
    for (int i = 0, j = 1; j < taskCount; i++, j++) {
      Task topTask = tasks.get(i);
      Task bottomTask = tasks.get(j);
      stitch(topTask, bottomTask);
    }
    List<Item> items = new LinkedList<>();
    for (Task task : tasks) {
      for (Item item : task.getItems()) {
        items.add(item);
      }
    }
    return items;
  }

  private void stitch(Task topTask, Task bottomTask) {
    Iterator<Item> topIterator = topTask.getItems().iterator();
    while (topIterator.hasNext()) {
      Item topItem = topIterator.next();
      if (topItem.getBottom() == topTask.getLastRow()) {
        boolean isStitched = stitchItem(topItem, topTask, bottomTask);
        if (isStitched) {
          topIterator.remove();
        }
      }
    }
  }

  private boolean stitchItem(Item topItem, Task topTask, Task bottomTask) {
    Item masterItem = null;
    boolean isStitched = false;
    List<Item> adjacentItems = findVerticallyAdjacentItems(topItem, bottomTask);
    for (Item adjacentItem : adjacentItems) {
      if (masterItem == null) {
        masterItem = adjacentItem;
        masterItem.merge(topItem);
        isStitched = true;
      } else {
        masterItem.merge(adjacentItem);
        bottomTask.getItems().remove(adjacentItem);
      }
    }
    return isStitched;
  }

}
