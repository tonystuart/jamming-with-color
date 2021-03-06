// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.image;

public class Extent {

  private int endX;
  private int startX;
  private int y;

  public Extent(int startX, int y) {
    this.startX = startX;
    this.y = y;
  }

  public int getEndX() {
    return endX;
  }

  public int getStartX() {
    return startX;
  }

  public int getY() {
    return y;
  }

  public void setEndX(int endX) {
    this.endX = endX;
  }

  public void setStartX(int startX) {
    this.startX = startX;
  }

  @Override
  public String toString() {
    return "[startX=" + startX + ", endX=" + endX + ", y=" + y + "]";
  }

}
