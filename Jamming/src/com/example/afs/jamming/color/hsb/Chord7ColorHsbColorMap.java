// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.color.hsb;

import com.example.afs.jamming.sound.ScaleBasedChord;

public class Chord7ColorHsbColorMap extends HsbColorMap {

  public static final HsbColor blue = new HsbColor("Blue", 0x00FFFF);
  public static final HsbColor green = new HsbColor("Green", 0x00FF00);
  public static final HsbColor indigo = new HsbColor("Indigo", 0x0000FF);
  public static final HsbColor orange = new HsbColor("Orange", 0xFF8000);
  public static final HsbColor red = new HsbColor("Red", 0xFF0000);
  public static final HsbColor violet = new HsbColor("Violet", 0xFF00FF);
  public static final HsbColor yellow = new HsbColor("Yellow", 0xFFFF00);

  // "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
  // 0           2          4    5          7          9          11   12         14         16   17         19         21         23
  public Chord7ColorHsbColorMap() {
    add(red, new ScaleBasedChord("C (I)", 60, 0, 4, 7));
    add(orange, new ScaleBasedChord("Dmin (ii)", 60, 2, 5, 9));
    add(yellow, new ScaleBasedChord("Emin (iii)", 60, 4, 7, 11));
    add(green, new ScaleBasedChord("F (IV)", 60, 5, 9, 12));
    add(blue, new ScaleBasedChord("G (V)", 60, 7, 11, 14));
    add(indigo, new ScaleBasedChord("Amin (vi)", 60, 9, 12, 16));
    add(violet, new ScaleBasedChord("Bdim (vii\u00B0)", 60, 11, 14, 17));
  }

}
