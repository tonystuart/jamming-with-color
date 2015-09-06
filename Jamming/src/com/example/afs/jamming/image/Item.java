// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.image;

import java.util.ArrayList;

public class Item {
  private int bottom;
  private ArrayList<Extent> extents = new ArrayList<>();
  private int left = Integer.MAX_VALUE;
  private int right;
  private int top = Integer.MAX_VALUE;

  public void addExtent(Extent newExtent) {
    Extent existingExtent = getExtent(newExtent.getY());
    if (existingExtent == null) {
      extents.add(newExtent);
      existingExtent = newExtent;
    } else {
      existingExtent.setEndX(newExtent.getEndX());
    }
    top = Math.min(top, newExtent.getY());
    bottom = Math.max(bottom, newExtent.getY());
    left = Math.min(left, newExtent.getStartX());
    right = Math.max(right, newExtent.getEndX());
  }

  public int getBottom() {
    return bottom;
  }

  public Extent getExtent(int y) {
    return top <= y && y <= bottom ? extents.get(y - top) : null;
  }

  public ArrayList<Extent> getExtents() {
    return extents;
  }

  public int getHeight() {
    return (bottom - top) + 1; // +1 because top and bottom are inclusive
  }

  public int getLeft() {
    return left;
  }

  public int getRight() {
    return right;
  }

  public int getTop() {
    return top;
  }

  public int getWidth() {
    return (right - left) + 1; // +1 because left and right are inclusive
  }

  public void merge(Item that) {
    if (that.top >= this.top && that.bottom <= this.bottom) {
      mergeFast(that);
    } else {
      int first = Math.min(this.top, that.top);
      int last = Math.max(this.bottom, that.bottom);
      ArrayList<Extent> newExtents = new ArrayList<>(last - first + 1);
      for (int row = first; row <= last; row++) {
        int startX = Integer.MAX_VALUE;
        int endX = 0;
        if (this.top <= row && row <= this.bottom) {
          Extent extent = this.getExtent(row);
          startX = Math.min(startX, extent.getStartX());
          endX = Math.max(endX, extent.getEndX());
        }
        if (that.top <= row && row <= that.bottom) {
          Extent extent = that.getExtent(row);
          startX = Math.min(startX, extent.getStartX());
          endX = Math.max(endX, extent.getEndX());
        }
        Extent extent = new Extent(startX, row);
        extent.setEndX(endX);
        newExtents.add(extent);
      }
      extents = newExtents;
      updateLimits(that);
    }
  }

  public String toString() {
    return "Item [x=" + left + "->" + right + ", y=" + top + "->" + bottom + ", w=" + getWidth() + ", h=" + getHeight() + "]";
  }

  private void mergeFast(Item that) {
    int first = Math.max(this.top, that.top);
    int last = Math.min(this.bottom, that.bottom);
    for (int i = first; i <= last; i++) {
      Extent thisExtent = this.extents.get(i - this.top);
      Extent thatExtent = that.extents.get(i - that.top);
      thisExtent.setStartX(Math.min(thisExtent.getStartX(), thatExtent.getStartX()));
      thisExtent.setEndX(Math.max(thisExtent.getEndX(), thatExtent.getEndX()));
    }
    updateLimits(that);
  }

  private void updateLimits(Item that) {
    top = Math.min(this.top, that.top);
    left = Math.min(this.left, that.left);
    bottom = Math.max(this.bottom, that.bottom);
    right = Math.max(this.right, that.right);
  }

}
