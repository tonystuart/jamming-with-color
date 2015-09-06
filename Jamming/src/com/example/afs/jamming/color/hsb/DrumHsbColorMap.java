// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.color.hsb;

import com.example.afs.jamming.sound.DrumSet;

public class DrumHsbColorMap extends HsbColorMap {

  public DrumHsbColorMap() {
    // Note that Java MIDI only has 11 distinct drum sounds:
    //    Acoustic bass drum - red
    //    Side stick (tick)
    //    Acoustic snare - orange
    //    Low floor tom - purple
    //    Closed hi-hat - pink
    //    High floor tom
    //    Low tom
    //    Low-mid tom
    //    Hi-mid tom
    //    Crash cymbal 1 - yellow, brown
    //    High tom - cyan
    // We map them to color ranges as follows:
    //    pink - closedHiHat
    //    red - acousticBassDrum
    //    orange - acousticSnare
    //    yellow - crashCymbal1
    //    brown - crashCymbal1
    //    green - handClap
    //    cyan - highTom
    //    blue - lowTom
    //    purple - lowFloorTom
    add(HsbColorPalette.RED, DrumSet.acousticBassDrum);
    add(HsbColorPalette.ORANGE, DrumSet.acousticSnare);
    add(HsbColorPalette.YELLOW, DrumSet.acousticSnare);
    add(HsbColorPalette.GREEN, DrumSet.closedHiHat);
    add(HsbColorPalette.CYAN, DrumSet.crashCymbal1);
    add(HsbColorPalette.BLUE, DrumSet.lowFloorTom);
    add(HsbColorPalette.MAGENTA, DrumSet.highFloorTom);
  }

}
