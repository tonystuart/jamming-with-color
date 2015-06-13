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
  private Item item;
  private Key key;

  public Block(Item item, Color color, Key key, int averageRgb) {
    this.item = item;
    this.color = color;
    this.key = key;
    this.averageRgb = averageRgb;
  }

  public boolean fuzzyEquals(Block that, int minimumSize) {
    boolean fuzzyEquals = fuzzyEquals(this.item.getTop(), that.item.getTop(), minimumSize) && //
        fuzzyEquals(this.item.getLeft(), that.item.getLeft(), minimumSize) && //
        fuzzyEquals(this.item.getWidth(), that.item.getWidth(), minimumSize) && //
        fuzzyEquals(this.item.getHeight(), that.item.getHeight(), minimumSize);
    return fuzzyEquals;
  }

  public Color getColor() {
    return color;
  }

  public Item getItem() {
    return item;
  }

  public Key getKey() {
    return key;
  }

  public String toString() {
    return item + ", average=" + averageRgb + ", distance=" + Color.getDistance(averageRgb, color) + ", color=" + color + ", key=" + key;
  }

  private boolean fuzzyEquals(int thisInt, int thatInt, int minimumSize) {
    return Math.abs(thisInt - thatInt) < minimumSize;
  }
}
