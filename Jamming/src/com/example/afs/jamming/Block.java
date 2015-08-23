// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

public class Block {
  private int averageRgb;
  private Color color;
  private Composable composable;
  private Item item;

  public Block(Item item, Color color, Composable composable, int averageRgb) {
    this.item = item;
    this.color = color;
    this.composable = composable;
    this.averageRgb = averageRgb;
  }

  public boolean fuzzyEquals(Block that, int minimumSize) {
    boolean fuzzyEquals = fuzzyEquals(this.item.getTop(), that.item.getTop(), minimumSize) && //
        fuzzyEquals(this.item.getLeft(), that.item.getLeft(), minimumSize) && //
        fuzzyEquals(this.item.getWidth(), that.item.getWidth(), minimumSize) && //
        fuzzyEquals(this.item.getHeight(), that.item.getHeight(), minimumSize);
    return fuzzyEquals;
  }

  public int getAverageRgb() {
    return averageRgb;
  }

  public Color getColor() {
    return color;
  }

  public Composable getComposable() {
    return composable;
  }

  public Item getItem() {
    return item;
  }

  public String toString() {
    return item + ", averageRgb=" + Integer.toHexString(averageRgb) + ", distance=" + Color.getDistance(averageRgb, color) + ", color=" + color + ", composable=" + composable;
  }

  private boolean fuzzyEquals(int thisInt, int thatInt, int minimumSize) {
    return Math.abs(thisInt - thatInt) < minimumSize;
  }
}
