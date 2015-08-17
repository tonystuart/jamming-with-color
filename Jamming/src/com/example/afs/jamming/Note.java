// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

public class Note extends Sound {

  private static final String[] NAMES = new String[] {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

  public static String getNoteName(int noteValue) {
    int nameIndex = noteValue % NAMES.length;
    int octave = (noteValue / NAMES.length) - 1;
    String name = NAMES[nameIndex] + octave;
    return name;
  }

  public Note(int value) {
    super(getNoteName(value), value);
  }

}
