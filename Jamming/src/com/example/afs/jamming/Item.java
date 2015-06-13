// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

import java.util.LinkedList;

public class Item {
  private int bottom;
  private LinkedList<Extent> extents = new LinkedList<>();
  private int left = Integer.MAX_VALUE;
  private int right;
  private int top = Integer.MAX_VALUE;

  public void add(Extent extent) {
    extents.add(extent);
    left = Math.min(left, extent.getStartX());
    top = Math.min(top, extent.getY());
    right = Math.max(right, extent.getEndX());
    bottom = Math.max(bottom, extent.getY());
  }

  public int getBottom() {
    return bottom;
  }

  public LinkedList<Extent> getExtents() {
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

  public String toString() {
    return "top=" + top + ", left=" + left + ", width=" + getWidth() + ", height=" + getHeight();
  }
}
