// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

public class Chord7ColorHsbColorMap extends HsbColorMap {

  // "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
  // 0           2          4    5          7          9          11   12         14         16   17         19         21         23
  public Chord7ColorHsbColorMap() {
    add(HsbColorPalette.RED, new ScaleBasedChord("C", 60, 0, 4, 7));
    add(HsbColorPalette.ORANGE, new ScaleBasedChord("Dmin", 60, 2, 5, 9));
    add(HsbColorPalette.YELLOW, new ScaleBasedChord("Emin", 60, 4, 7, 11));
    add(HsbColorPalette.GREEN, new ScaleBasedChord("F", 60, 5, 9, 12));
    add(HsbColorPalette.CYAN, new ScaleBasedChord("G", 60, 7, 11, 14));
    add(HsbColorPalette.BLUE, new ScaleBasedChord("Amin", 60, 9, 12, 16));
    add(HsbColorPalette.MAGENTA, new ScaleBasedChord("Bdim", 60, 11, 14, 17));
  }

}
