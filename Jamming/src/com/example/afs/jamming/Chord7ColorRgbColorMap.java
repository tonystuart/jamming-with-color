// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

public class Chord7ColorRgbColorMap extends RgbColorMap {

  public static final Color beige = new Color("Beige", 0xc79a84);
  public static final Color blue = new Color("Blue", 0x5e8a7f);
  public static final Color brown = new Color("Brown", 0xb16444);
  public static final Color green = new Color("Green", 0x589259);
  public static final Color indigo = new Color("Indigo", 0x606b80);
  public static final Color lemon = new Color("Lemon", 0xc6c477);
  public static final Color orange = new Color("Orange", 0xc18a5d);
  public static final Color red = new Color("Red", 0xc45055);
  public static final Color violet = new Color("Violet", 0x926172);
  public static final Color white = new Color("White", 0xbbc0ba);
  public static final Color yellow = new Color("Yellow", 0xc1c175);

  // "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
  // 0           2          4    5          7          9          11   12         14         16   17         19         21         23
  public Chord7ColorRgbColorMap() {
    add(red, new ScaleBasedChord("C", 60, 0, 4, 7));
    add(orange, new ScaleBasedChord("Dmin", 60, 2, 5, 9));
    add(yellow, new ScaleBasedChord("Emin", 60, 4, 7, 11));
    add(green, new ScaleBasedChord("F", 60, 5, 9, 12));
    add(blue, new ScaleBasedChord("G", 60, 7, 11, 14));
    add(indigo, new ScaleBasedChord("Amin", 60, 9, 12, 16));
    add(violet, new ScaleBasedChord("Bdim", 60, 11, 14, 17));
  }

}
