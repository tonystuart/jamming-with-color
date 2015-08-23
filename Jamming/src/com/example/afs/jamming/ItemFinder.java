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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

// Be sure to consider special cases:
//
// See Test-M.png:
//
// **********
// **********
//  ** ** **
//  ** **
//     **
//
// See Test-W.png:
//
//     **
//  ** **
//  ** ** **
// **********
// **********
//
// See Test-M-Detached.png:
//
// **********
// **********
//  **    **
//  ** ** **
//     **
//
// See Test-W-Detached.png:
//
//     **
//  ** ** **
//  **    **
// **********
// **********
//

public class ItemFinder {

  public enum Background {
    GREATER_THAN, LESS_THAN
  }

  private enum State {
    INITIAL, ITEM
  }

  private ArrayList<Item> adjacentItemFlyweight = new ArrayList<>();
  private Set<Item> currentItems = new LinkedHashSet<>(); // Use LinkedHashSet to ensure consistent ordering
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
      for (int x = 0; x < width; x++) {
        int rgb = image.getRGB(x, y);
        boolean isBackground = isBackground(background, threshold, rgb);
        switch (state) {
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
              state = State.INITIAL;
              extent.setEndX(x - 1);
              addExtent(extent);
              extent = null;
              image.setRGB(x, y, backgroundRgb);
            }
            break;
          default:
            throw new UnsupportedOperationException(state.toString());
        }
      }
      if (extent != null) {
        extent.setEndX(width - 1);
        addExtent(extent);
        extent = null;
      }
      for (Item item : marks) {
        currentItems.remove(item);
      }
    }
    return items;
  }

  private void addExtent(Extent extent) {
    Item masterItem = null;
    List<Item> adjacentItems = findAjacentItems(extent);
    if (adjacentItems.size() == 0) {
      masterItem = new Item();
      items.add(masterItem);
    } else {
      for (Item adjacentItem : adjacentItems) {
        if (masterItem == null) {
          masterItem = adjacentItem;
          marks.remove(masterItem);
        } else {
          masterItem.merge(adjacentItem);
          items.remove(adjacentItem);
        }
      }
    }
    masterItem.addExtent(extent);
    currentItems.add(masterItem);
  }

  private List<Item> findAjacentItems(Extent extent) {
    adjacentItemFlyweight.clear();
    for (Item currentItem : currentItems) {
      if (isAdjacentToPrevious(currentItem, extent)) {
        adjacentItemFlyweight.add(currentItem);
      }
    }
    return adjacentItemFlyweight;
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
    boolean isAdjacent = false;
    int previousBottom = currentExtent.getY() - 1;
    Extent previousExtent = item.getExtent(previousBottom);
    if (previousExtent != null && previousExtent.getStartX() <= currentExtent.getEndX() && previousExtent.getEndX() > currentExtent.getStartX()) {
      isAdjacent = true;
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
