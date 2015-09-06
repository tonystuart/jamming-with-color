// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.rowmapper;

import com.example.afs.jamming.image.Block;

public class MultipleRowMapper implements RowMapper {
  private int currentBottom;
  private int currentRow = -1;
  private int rowSpacing;
  private int width;

  public MultipleRowMapper(int rowSpacing, int width) {
    this.rowSpacing = rowSpacing;
    this.width = width;
  }

  @Override
  public Limits getLimits(Block block) {
    if (currentRow == -1 || block.getItem().getTop() > (currentBottom + rowSpacing)) {
      currentRow++;
    }
    currentBottom = Math.max(currentBottom, block.getItem().getBottom());
    int left = (currentRow * width) + (block.getItem().getLeft() % width);
    int right = (currentRow * width) + (block.getItem().getRight() % width);
    return new Limits(currentRow, left, right);
  }

  @Override
  public int getTotalWidth() {
    return (currentRow + 1) * width;
  }
}