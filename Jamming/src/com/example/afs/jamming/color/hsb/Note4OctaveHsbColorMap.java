// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.color.hsb;

import com.example.afs.jamming.sound.Note;

public class Note4OctaveHsbColorMap extends HsbColorMap {

  private static final int FIRST = 36;
  private static final int LAST = 84;

  public Note4OctaveHsbColorMap() {
    int noteCount = LAST - FIRST;
    int colorRange = 100 / noteCount;
    int hue = 0;
    for (int i = FIRST; i < LAST; i++) {
      HsbColor color = new HsbColor(hue);
      Note Note = new Note(i);
      add(color, Note);
      hue += colorRange;
    }
  }

}
