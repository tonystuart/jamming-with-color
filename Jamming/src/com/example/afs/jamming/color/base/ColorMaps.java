// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.color.base;

import java.util.LinkedHashMap;
import java.util.Map;

import com.example.afs.jamming.color.hsb.Chord7ColorArpeggiatedHsbColorMap;
import com.example.afs.jamming.color.hsb.Chord7ColorHsbColorMap;
import com.example.afs.jamming.color.hsb.DrumHsbColorMap;
import com.example.afs.jamming.color.hsb.Note1OctaveLowHsbColorMap;
import com.example.afs.jamming.color.hsb.Note2OctaveHsbColorMap;
import com.example.afs.jamming.color.hsb.Note4OctaveHsbColorMap;
import com.example.afs.jamming.color.hsb.TechnoMultiColorHsbColorMap;
import com.example.afs.jamming.color.rgb.Chord7ColorRgbColorMap;
import com.example.afs.jamming.color.rgb.DrumRgbColorMap;

public final class ColorMaps {

  public static final ColorMaps instance = new ColorMaps();

  public static ColorMaps getSingleton() {
    return instance;
  }

  private Map<String, ColorMap> colorMaps = new LinkedHashMap<>();

  public ColorMaps() {
    // http://www.javaworld.com/article/2077477/learn-java/java-tip-113--identify-subclasses-at-runtime.html
    register(new Chord7ColorArpeggiatedHsbColorMap());
    register(new Chord7ColorHsbColorMap());
    register(new Chord7ColorRgbColorMap());
    register(new DrumHsbColorMap());
    register(new DrumRgbColorMap());
    register(new Note1OctaveLowHsbColorMap());
    register(new Note2OctaveHsbColorMap());
    register(new Note4OctaveHsbColorMap());
    register(new TechnoMultiColorHsbColorMap());
  }

  public ColorMap get(String name) {
    return colorMaps.get(name);
  }

  public ColorMap getDefault() {
    return colorMaps.size() == 0 ? null : colorMaps.values().iterator().next();
  }

  public Iterable<String> getNames() {
    return colorMaps.keySet();
  }

  public void put(String name, ColorMap colorMap) {
    colorMaps.put(name, colorMap);
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (String name : colorMaps.keySet()) {
      if (s.length() > 0) {
        s.append("\n");
      }
      s.append(name);
    }
    return s.toString();
  }

  private void register(ColorMap colorMap) {
    put(colorMap.getName(), colorMap);
  }
}
