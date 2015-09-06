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

public class ScaleBasedChord implements Composable {

  private int[] intervals;
  private String name;
  private int tonic;

  public ScaleBasedChord(String name, int tonic, int... intervals) {
    this.name = name;
    this.tonic = tonic;
    this.intervals = intervals;
  }

  @Override
  public void addToTrack(TrackBuilder trackBuilder, long tick, int channel, int velocity, int duration) {
    for (int interval : intervals) {
      trackBuilder.addNote(tick, channel, tonic + interval, velocity, duration);
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "[name=" + name + ", tonic=" + tonic + ", intervals=" + Arrays.toString(intervals) + "]";
  }

}