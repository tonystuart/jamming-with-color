// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.sound;

import java.util.Arrays;

public class ArpeggiatedChord extends MarkerComposable {

  private String name;
  private int[] notes;

  public ArpeggiatedChord(String name, int... notes) {
    this.name = name;
    this.notes = notes;
  }

  @Override
  public void addToTrack(TrackBuilder trackBuilder, long tick, int channel, int velocity, int totalDuration) {
    int duration = totalDuration / notes.length;
    for (int note : notes) {
      trackBuilder.addNote(tick, channel, note, velocity, duration);
      tick += duration;
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "[name=" + name + ", notes=" + Arrays.toString(notes) + "]";
  }

}