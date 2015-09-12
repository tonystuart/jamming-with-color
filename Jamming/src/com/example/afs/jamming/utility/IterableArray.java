// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.utility;

import java.util.Iterator;

public class IterableArray<T> implements Iterable<T> {
  public class ArrayIterator implements Iterator<T> {
    private int offset;

    @Override
    public boolean hasNext() {
      return offset < array.length;
    }

    @Override
    public T next() {
      return array[offset++];
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  private T[] array;

  @SafeVarargs
  public IterableArray(T... array) {
    this.array = array;
  }

  @Override
  public Iterator<T> iterator() {
    return new ArrayIterator();
  }

}
