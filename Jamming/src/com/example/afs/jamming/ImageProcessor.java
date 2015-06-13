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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.example.afs.jamming.ItemFinder.Background;

public class ImageProcessor {

  private BufferedImage image;
  private List<Item> items;

  public ImageProcessor(BufferedImage image, Background background, int threshold, int minimumSize) {
    this.image = image;
    items = new ItemFinder().findItems(image, background, threshold, minimumSize);
  }

  public void averageItemColors() {
    for (Item item : items) {
      int averageRgb = getAverageRgb(item);
      setAverageRgb(item, averageRgb);
    }
  }

  public int despeckle(int minimumSize) {
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

  public List<Item> getItems() {
    return Collections.unmodifiableList(items);
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
}
