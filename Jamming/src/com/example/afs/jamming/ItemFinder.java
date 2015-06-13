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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ItemFinder {

  public enum Background {
    GREATER_THAN, LESS_THAN
  }

  private enum State {
    DROPOUT, INITIAL, ITEM
  }

  private Set<Item> currentItems = new HashSet<>();
  private List<Item> items = new LinkedList<>();
  private Set<Item> marks = new HashSet<>();

  public List<Item> findItems(BufferedImage image, Background background, int threshold, int minimumSize) {
    Extent extent = null;
    int width = image.getWidth();
    int height = image.getHeight();
    int backgroundRgb = getBackgroundRgb(background, threshold);
    for (int y = 0; y < height; y++) {
      marks.clear();
      for (Item currentItem : currentItems) {
        marks.add(currentItem);
      }
      State state = State.INITIAL;
      int dropoutX = 0;
      int dropoutMax = 0;
      for (int x = 0; x < width; x++) {
        int rgb = image.getRGB(x, y);
        boolean isBackground = isBackground(background, threshold, rgb);
        switch (state) {
          case DROPOUT:
            if (isBackground) {
              if (x == dropoutMax) {
                state = State.INITIAL;
                for (int i = dropoutX; i <= x; i++) {
                  image.setRGB(i, y, backgroundRgb);
                }
                addExtent(extent, dropoutX);
                extent = null;
              }
            } else {
              state = State.ITEM;
            }
            break;
          case INITIAL:
            if (isBackground) {
              image.setRGB(x, y, backgroundRgb);
            } else {
              state = State.ITEM;
              extent = new Extent(x, y);
            }
            break;
          case ITEM:
            if (isBackground) {
              state = State.DROPOUT;
              dropoutX = x;
              dropoutMax = Math.min(x + minimumSize, width);
            }
            break;
          default:
            throw new UnsupportedOperationException(state.toString());
        }
      }
      if (extent != null) {
        addExtent(extent, width);
        extent = null;
      }
      for (Item item : marks) {
        currentItems.remove(item);
      }
    }
    return items;
  }

  private void addExtent(Extent extent, int x) {
    extent.setEndX(x - 1);
    Item item = findAjacentItem(extent);
    if (item == null) {
      item = new Item();
      items.add(item);
    } else {
      marks.remove(item);
    }
    item.add(extent);
    currentItems.add(item);
  }

  private Item findAjacentItem(Extent extent) {
    for (Item currentItem : currentItems) {
      if (isAdjacentToPrevious(currentItem, extent)) {
        return currentItem;
      }
    }
    return null;
  }

  private int getBackgroundRgb(Background background, int threshold) {
    int backgroundRgb;
    switch (background) {
      case GREATER_THAN:
        backgroundRgb = 0xffffff;
        break;
      case LESS_THAN:
        backgroundRgb = 0;
        break;
      default:
        throw new UnsupportedOperationException(background.toString());
    }
    return backgroundRgb;
  }

  private boolean isAdjacentToPrevious(Item item, Extent currentExtent) {
    boolean isAdjacent;
    Extent previousExtent = item.getExtents().peekLast();
    if (previousExtent == null) {
      isAdjacent = false;
    } else if (previousExtent.getStartX() <= currentExtent.getEndX() && previousExtent.getEndX() > currentExtent.getStartX()) {
      isAdjacent = true;
    } else {
      isAdjacent = false;
    }
    return isAdjacent;
  }

  private boolean isBackground(Background background, int threshold, int rgb) {
    boolean isBackground;
    switch (background) {
      case GREATER_THAN:
        isBackground = Color.lessThan(threshold, rgb);
        break;
      case LESS_THAN:
        isBackground = Color.lessThan(rgb, threshold);
        break;
      default:
        throw new UnsupportedOperationException(background.toString());
    }
    return isBackground;
  }

}
