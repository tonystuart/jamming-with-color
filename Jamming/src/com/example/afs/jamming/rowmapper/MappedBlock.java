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

public class MappedBlock {
  private Block block;
  private int left;
  private int right;
  private int row;

  public MappedBlock(Block block, int row, int left, int right) {
    this.block = block;
    this.row = row;
    this.left = left;
    this.right = right;
  }

  public Block getBlock() {
    return block;
  }

  public int getLeft() {
    return left;
  }

  public int getRight() {
    return right;
  }

  public int getRow() {
    return row;
  }

  @Override
  public String toString() {
    return "MappedBlock [row=" + row + ", block=" + block + "]";
  }

}