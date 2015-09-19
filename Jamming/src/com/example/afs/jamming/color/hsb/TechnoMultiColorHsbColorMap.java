// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.color.hsb;

import com.example.afs.jamming.sound.ArpeggiatedChord;

public class TechnoMultiColorHsbColorMap extends HsbColorMap {

  public static final HsbColor blue = new HsbColor("Blue", .70f);
  public static final HsbColor green = new HsbColor("Green", .50f);
  public static final HsbColor red = new HsbColor("Red", .10f);
  public static final HsbColor yellow = new HsbColor("Yellow", .20f);

  // "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
  // 0           2          4    5          7          9          11   12         14         16   17         19         21         23
  public TechnoMultiColorHsbColorMap() {
    add(red, new ArpeggiatedChord("A/C/E/B", 69, 72, 76, 71));
    add(yellow, new ArpeggiatedChord("E/G/B/C", 64, 67, 71, 72));
    add(green, new ArpeggiatedChord("F/A/C/D", 65, 69, 72, 74));
    add(blue, new ArpeggiatedChord("G/B/D/E/", 67, 71, 74, 76));
  }

}
