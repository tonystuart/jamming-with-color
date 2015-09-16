// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.color.hsb;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.example.afs.jamming.color.base.BaseColorMap;
import com.example.afs.jamming.color.rgb.Color;
import com.example.afs.jamming.sound.Composable;

public abstract class HsbColorMap extends BaseColorMap {

  private class ColorMapComparator implements Comparator<HsbColor> {
    @Override
    public int compare(HsbColor o1, HsbColor o2) {
      int deltaHue;
      if (o1.getHue() < o2.getHue()) {
        deltaHue = -1;
      } else if (o1.getHue() > o2.getHue()) {
        deltaHue = +1;
      } else {
        deltaHue = 0;
      }
      return deltaHue;
    }
  }

  private TreeMap<HsbColor, Composable> colorMap = new TreeMap<>(new ColorMapComparator());

  public void add(HsbColor color, Composable composable) {
    colorMap.put(color, composable);
  }

  @Override
  public void calibrate(String[] hueValues) {
    if (hueValues.length != colorMap.size()) {
      throw new IllegalArgumentException("Cannot calibrate color map: expected " + colorMap.size() + " hue values, found " + hueValues.length + " hue values");
    }
    int hueIndex = 0;
    TreeMap<HsbColor, Composable> newColorMap = new TreeMap<>(new ColorMapComparator());
    for (Entry<HsbColor, Composable> entry : colorMap.entrySet()) {
      String hue = hueValues[hueIndex++];
      HsbColor newColor = new HsbColor(entry.getKey().getName(), Float.parseFloat(hue));
      newColorMap.put(newColor, entry.getValue());
    }
    colorMap = newColorMap;
  }

  @Override
  public Entry<? extends Color, Composable> findClosestEntry(int rgb) {
    HsbColor color = new HsbColor(rgb);
    Entry<HsbColor, Composable> closestEntry = colorMap.ceilingEntry(color);
    if (closestEntry == null) {
      closestEntry = colorMap.firstEntry();
    }
    return closestEntry;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("name=");
    s.append(getName());
    for (Entry<HsbColor, Composable> entry : colorMap.entrySet()) {
      s.append("\n");
      s.append(entry.getKey());
      s.append(" ");
      s.append(entry.getValue());
    }
    return s.toString();
  }
}
