// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

public class DrumRgbColorMap extends RgbColorMap {

  public DrumRgbColorMap() {
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
    add(RgbColorPalette.pink, DrumSet.closedHiHat);
    add(RgbColorPalette.lightPink, DrumSet.closedHiHat);
    add(RgbColorPalette.hotPink, DrumSet.closedHiHat);
    add(RgbColorPalette.deepPink, DrumSet.closedHiHat);
    add(RgbColorPalette.paleVioletRed, DrumSet.closedHiHat);
    add(RgbColorPalette.mediumVioletRed, DrumSet.closedHiHat);
    add(RgbColorPalette.lightSalmon, DrumSet.acousticBassDrum);
    add(RgbColorPalette.salmon, DrumSet.acousticBassDrum);
    add(RgbColorPalette.darkSalmon, DrumSet.acousticBassDrum);
    add(RgbColorPalette.lightCoral, DrumSet.acousticBassDrum);
    add(RgbColorPalette.indianRed, DrumSet.acousticBassDrum);
    add(RgbColorPalette.crimson, DrumSet.acousticBassDrum);
    add(RgbColorPalette.fireBrick, DrumSet.acousticBassDrum);
    add(RgbColorPalette.darkRed, DrumSet.acousticBassDrum);
    add(RgbColorPalette.red, DrumSet.acousticBassDrum);
    add(RgbColorPalette.orangeRed, DrumSet.acousticSnare);
    add(RgbColorPalette.tomato, DrumSet.acousticSnare);
    add(RgbColorPalette.coral, DrumSet.acousticSnare);
    add(RgbColorPalette.darkOrange, DrumSet.acousticSnare);
    add(RgbColorPalette.orange, DrumSet.acousticSnare);
    add(RgbColorPalette.yellow, DrumSet.crashCymbal1);
    add(RgbColorPalette.lightYellow, DrumSet.crashCymbal1);
    add(RgbColorPalette.lemonChiffon, DrumSet.crashCymbal1);
    add(RgbColorPalette.lightGoldenrodYellow, DrumSet.crashCymbal1);
    add(RgbColorPalette.papayaWhip, DrumSet.crashCymbal1);
    add(RgbColorPalette.moccasin, DrumSet.crashCymbal1);
    add(RgbColorPalette.peachPuff, DrumSet.crashCymbal1);
    add(RgbColorPalette.paleGoldenrod, DrumSet.crashCymbal1);
    add(RgbColorPalette.khaki, DrumSet.crashCymbal1);
    add(RgbColorPalette.darkKhaki, DrumSet.crashCymbal1);
    add(RgbColorPalette.gold, DrumSet.crashCymbal1);
    add(RgbColorPalette.cornsilk, DrumSet.crashCymbal1);
    add(RgbColorPalette.blanchedAlmond, DrumSet.crashCymbal1);
    add(RgbColorPalette.bisque, DrumSet.crashCymbal1);
    add(RgbColorPalette.navajoWhite, DrumSet.crashCymbal1);
    add(RgbColorPalette.wheat, DrumSet.crashCymbal1);
    add(RgbColorPalette.burlyWood, DrumSet.crashCymbal1);
    add(RgbColorPalette.tan, DrumSet.crashCymbal1);
    add(RgbColorPalette.rosyBrown, DrumSet.crashCymbal1);
    add(RgbColorPalette.sandyBrown, DrumSet.crashCymbal1);
    add(RgbColorPalette.goldenrod, DrumSet.crashCymbal1);
    add(RgbColorPalette.darkGoldenrod, DrumSet.crashCymbal1);
    add(RgbColorPalette.peru, DrumSet.crashCymbal1);
    add(RgbColorPalette.chocolate, DrumSet.crashCymbal1);
    add(RgbColorPalette.saddleBrown, DrumSet.crashCymbal1);
    add(RgbColorPalette.sienna, DrumSet.crashCymbal1);
    add(RgbColorPalette.brown, DrumSet.crashCymbal1);
    add(RgbColorPalette.maroon, DrumSet.crashCymbal1);
    add(RgbColorPalette.darkOliveGreen, DrumSet.handClap);
    add(RgbColorPalette.olive, DrumSet.handClap);
    add(RgbColorPalette.oliveDrab, DrumSet.handClap);
    add(RgbColorPalette.yellowGreen, DrumSet.handClap);
    add(RgbColorPalette.limeGreen, DrumSet.handClap);
    add(RgbColorPalette.lime, DrumSet.handClap);
    add(RgbColorPalette.lawnGreen, DrumSet.handClap);
    add(RgbColorPalette.chartreuse, DrumSet.handClap);
    add(RgbColorPalette.greenYellow, DrumSet.handClap);
    add(RgbColorPalette.springGreen, DrumSet.handClap);
    add(RgbColorPalette.mediumSpringGreen, DrumSet.handClap);
    add(RgbColorPalette.lightGreen, DrumSet.handClap);
    add(RgbColorPalette.paleGreen, DrumSet.handClap);
    add(RgbColorPalette.darkSeaGreen, DrumSet.handClap);
    add(RgbColorPalette.mediumSeaGreen, DrumSet.handClap);
    add(RgbColorPalette.seaGreen, DrumSet.handClap);
    add(RgbColorPalette.forestGreen, DrumSet.handClap);
    add(RgbColorPalette.green, DrumSet.handClap);
    add(RgbColorPalette.darkGreen, DrumSet.handClap);
    add(RgbColorPalette.mediumAquamarine, DrumSet.highTom);
    add(RgbColorPalette.aqua, DrumSet.highTom);
    add(RgbColorPalette.cyan, DrumSet.highTom);
    add(RgbColorPalette.lightCyan, DrumSet.highTom);
    add(RgbColorPalette.paleTurquoise, DrumSet.highTom);
    add(RgbColorPalette.aquamarine, DrumSet.highTom);
    add(RgbColorPalette.turquoise, DrumSet.highTom);
    add(RgbColorPalette.mediumTurquoise, DrumSet.highTom);
    add(RgbColorPalette.darkTurquoise, DrumSet.highTom);
    add(RgbColorPalette.lightSeaGreen, DrumSet.highTom);
    add(RgbColorPalette.cadetBlue, DrumSet.highTom);
    add(RgbColorPalette.darkCyan, DrumSet.highTom);
    add(RgbColorPalette.teal, DrumSet.highTom);
    add(RgbColorPalette.lightSteelBlue, DrumSet.lowTom);
    add(RgbColorPalette.powderBlue, DrumSet.lowTom);
    add(RgbColorPalette.lightBlue, DrumSet.lowTom);
    add(RgbColorPalette.skyBlue, DrumSet.lowTom);
    add(RgbColorPalette.lightSkyBlue, DrumSet.lowTom);
    add(RgbColorPalette.deepSkyBlue, DrumSet.lowTom);
    add(RgbColorPalette.dodgerBlue, DrumSet.lowTom);
    add(RgbColorPalette.cornflowerBlue, DrumSet.lowTom);
    add(RgbColorPalette.steelBlue, DrumSet.lowTom);
    add(RgbColorPalette.royalBlue, DrumSet.lowTom);
    add(RgbColorPalette.blue, DrumSet.lowTom);
    add(RgbColorPalette.mediumBlue, DrumSet.lowTom);
    add(RgbColorPalette.darkBlue, DrumSet.lowTom);
    add(RgbColorPalette.navy, DrumSet.lowTom);
    add(RgbColorPalette.midnightBlue, DrumSet.lowTom);
    add(RgbColorPalette.lavender, DrumSet.lowFloorTom);
    add(RgbColorPalette.thistle, DrumSet.lowFloorTom);
    add(RgbColorPalette.plum, DrumSet.lowFloorTom);
    add(RgbColorPalette.violet, DrumSet.lowFloorTom);
    add(RgbColorPalette.orchid, DrumSet.lowFloorTom);
    add(RgbColorPalette.fuchsia, DrumSet.lowFloorTom);
    add(RgbColorPalette.magenta, DrumSet.lowFloorTom);
    add(RgbColorPalette.mediumOrchid, DrumSet.lowFloorTom);
    add(RgbColorPalette.mediumPurple, DrumSet.lowFloorTom);
    add(RgbColorPalette.blueViolet, DrumSet.lowFloorTom);
    add(RgbColorPalette.darkViolet, DrumSet.lowFloorTom);
    add(RgbColorPalette.darkOrchid, DrumSet.lowFloorTom);
    add(RgbColorPalette.darkMagenta, DrumSet.lowFloorTom);
    add(RgbColorPalette.purple, DrumSet.lowFloorTom);
    add(RgbColorPalette.indigo, DrumSet.lowFloorTom);
    add(RgbColorPalette.darkSlateBlue, DrumSet.lowFloorTom);
    add(RgbColorPalette.rebeccaPurple, DrumSet.lowFloorTom);
    add(RgbColorPalette.slateBlue, DrumSet.lowFloorTom);
    add(RgbColorPalette.mediumSlateBlue, DrumSet.lowFloorTom);
  }

}
