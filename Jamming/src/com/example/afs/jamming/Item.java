// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

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
    updateLimits(existingExtent);
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
    // Optimized based on item finder design
    assert this.top <= that.top; // first item in adjacency list is always the top most
    int first = Math.max(this.top, that.top); // from first overlapping extent to last overlapping extent
    int last = Math.min(this.bottom, that.bottom); // may have already added extent for items to left on current row
    for (int i = first; i <= last; i++) {
      Extent thisExtent = this.extents.get(i - this.top);
      Extent thatExtent = that.extents.get(i - that.top);
      thisExtent.setStartX(Math.min(thisExtent.getStartX(), thatExtent.getStartX()));
      thisExtent.setEndX(Math.max(thisExtent.getEndX(), thatExtent.getEndX()));
      updateLimits(thisExtent);
    }
  }

  public String toString() {
    return "top=" + top + ", left=" + left + ", width=" + getWidth() + ", height=" + getHeight();
  }

  private void updateLimits(Extent newExtent) {
    left = Math.min(left, newExtent.getStartX());
    top = Math.min(top, newExtent.getY());
    right = Math.max(right, newExtent.getEndX());
    bottom = Math.max(bottom, newExtent.getY());
  }
}
