// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

public abstract class HsbColorMap extends BaseColorMap {

  private TreeMap<HsbColor, Composable> colorMap = new TreeMap<>(new Comparator<HsbColor>() {
    @Override
    public int compare(HsbColor o1, HsbColor o2) {
      return (int) (o1.getHue() - o2.getHue());
    }
  });

  public void add(HsbColor color, Composable composable) {
    colorMap.put(color, composable);
  }

  @Override
  public Entry<? extends Color, Composable> findClosestEntry(int rgb) {
    HsbColor color = new HsbColor(rgb);
    Entry<HsbColor, Composable> closestEntry = colorMap.ceilingEntry(color);
    if (closestEntry == null) {
      closestEntry = colorMap.firstEntry();
    }
    System.out.println("color=" + color.getHue() + ", found=" + closestEntry.getKey().getHue());
    return closestEntry;
  }
}
