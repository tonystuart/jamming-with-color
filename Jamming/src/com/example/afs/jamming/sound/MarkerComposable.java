// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.sound;

public abstract class MarkerComposable implements Composable {

  // http://www.somascape.org/midi/tech/mfile.html

  public static final int MARKER = 6;
  public static final String MARKER_BEGIN = "B:";
  public static final String MARKER_END = "E:";

  @Override
  public void addToTrack(TrackBuilder trackBuilder, long tick, int channel, int velocity, int duration, int blockIndex) {
    addMarker(trackBuilder, tick, channel, MARKER_BEGIN, blockIndex);
    addToTrack(trackBuilder, tick, channel, velocity, duration);
    addMarker(trackBuilder, tick + duration, channel, MARKER_END, blockIndex);
  }

  private void addMarker(TrackBuilder trackBuilder, long tick, int channel, String markerType, int blockIndex) {
    String marker = markerType + blockIndex;
    trackBuilder.addMetaMessage(tick, channel, MARKER, marker.getBytes(), marker.length());
  }

}
