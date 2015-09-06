// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.sound;


public class Sound implements Composable {

  private String name;
  private int value;

  public Sound(String name, int value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public void addToTrack(TrackBuilder trackBuilder, long tick, int channel, int velocity, int duration) {
    trackBuilder.addNote(tick, channel, value, velocity, duration);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name + " (" + value + ")";
  }

}
