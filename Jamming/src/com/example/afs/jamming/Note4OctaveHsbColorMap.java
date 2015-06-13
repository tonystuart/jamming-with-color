// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

public class Note4OctaveHsbColorMap extends HsbColorMap {

  private static final int FIRST = 36;
  private static final int LAST = 84;

  private static final String[] names = new String[] {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

  public Note4OctaveHsbColorMap() {
    int noteCount = LAST - FIRST;
    int colorRange = 100 / noteCount;
    int hue = 0;
    for (int i = FIRST; i < LAST; i++) {
      HsbColor color = new HsbColor(hue, 100, 100);
      int index = i % names.length;
      int octave = (i / names.length) - 1;
      String name = names[index] + octave;
      Key key = new Key(name, i);
      add(color, key);
      hue += colorRange;
    }
  }

}
