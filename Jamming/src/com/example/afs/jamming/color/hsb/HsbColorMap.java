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
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.example.afs.jamming.color.base.BaseColorMap;
import com.example.afs.jamming.color.rgb.Color;
import com.example.afs.jamming.rowmapper.MappedBlock;
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
  public void calibrate(Iterable<MappedBlock> mappedBlocks) {
    Set<HsbColor> newCalibratedColorSet = new TreeSet<>(new ColorMapComparator());
    for (MappedBlock mappedBlock : mappedBlocks) {
      newCalibratedColorSet.add(new HsbColor(mappedBlock.getBlock().getAverageRgb()));
    }
    if (newCalibratedColorSet.size() != colorMap.size()) {
      System.err.println("Cannot calibrate color map: expected " + colorMap.size() + " item(s), found " + newCalibratedColorSet.size() + " item(s)");
    } else {
      TreeMap<HsbColor, Composable> newCalibratedColorMap = new TreeMap<>(new ColorMapComparator());
      Iterator<HsbColor> newCalibratedColorSetIterator = newCalibratedColorSet.iterator();
      Iterator<Entry<HsbColor, Composable>> oldColorMapIterator = colorMap.entrySet().iterator();
      while (newCalibratedColorSetIterator.hasNext() && oldColorMapIterator.hasNext()) {
        HsbColor newCalibratedColor = newCalibratedColorSetIterator.next();
        float hue = newCalibratedColor.getHue();
        hue += 2; // to include new calibrated color in map
        HsbColor newColor = new HsbColor(hue, newCalibratedColor.getSaturation(), newCalibratedColor.getBrightness());
        Entry<HsbColor, Composable> oldColorMapEntry = oldColorMapIterator.next();
        newCalibratedColorMap.put(newColor, oldColorMapEntry.getValue());
      }
      colorMap = newCalibratedColorMap;
    }

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
    s.append("\n");
    for (Entry<HsbColor, Composable> entry : colorMap.entrySet()) {
      s.append(entry.getKey());
      s.append(" ");
      s.append(entry.getValue());
      s.append("\n");
    }
    return s.toString();
  }
}
