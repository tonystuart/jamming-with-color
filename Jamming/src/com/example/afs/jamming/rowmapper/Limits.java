// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.rowmapper;

public class Limits {
  private int left;
  private int right;
  private int row;

  public Limits(int row, int left, int right) {
    this.row = row;
    this.left = left;
    this.right = right;
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

}