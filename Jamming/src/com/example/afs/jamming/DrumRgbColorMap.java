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
    add(ColorPalette.pink, DrumSet.closedHiHat);
    add(ColorPalette.lightPink, DrumSet.closedHiHat);
    add(ColorPalette.hotPink, DrumSet.closedHiHat);
    add(ColorPalette.deepPink, DrumSet.closedHiHat);
    add(ColorPalette.paleVioletRed, DrumSet.closedHiHat);
    add(ColorPalette.mediumVioletRed, DrumSet.closedHiHat);
    add(ColorPalette.lightSalmon, DrumSet.acousticBassDrum);
    add(ColorPalette.salmon, DrumSet.acousticBassDrum);
    add(ColorPalette.darkSalmon, DrumSet.acousticBassDrum);
    add(ColorPalette.lightCoral, DrumSet.acousticBassDrum);
    add(ColorPalette.indianRed, DrumSet.acousticBassDrum);
    add(ColorPalette.crimson, DrumSet.acousticBassDrum);
    add(ColorPalette.fireBrick, DrumSet.acousticBassDrum);
    add(ColorPalette.darkRed, DrumSet.acousticBassDrum);
    add(ColorPalette.red, DrumSet.acousticBassDrum);
    add(ColorPalette.orangeRed, DrumSet.acousticSnare);
    add(ColorPalette.tomato, DrumSet.acousticSnare);
    add(ColorPalette.coral, DrumSet.acousticSnare);
    add(ColorPalette.darkOrange, DrumSet.acousticSnare);
    add(ColorPalette.orange, DrumSet.acousticSnare);
    add(ColorPalette.yellow, DrumSet.crashCymbal1);
    add(ColorPalette.lightYellow, DrumSet.crashCymbal1);
    add(ColorPalette.lemonChiffon, DrumSet.crashCymbal1);
    add(ColorPalette.lightGoldenrodYellow, DrumSet.crashCymbal1);
    add(ColorPalette.papayaWhip, DrumSet.crashCymbal1);
    add(ColorPalette.moccasin, DrumSet.crashCymbal1);
    add(ColorPalette.peachPuff, DrumSet.crashCymbal1);
    add(ColorPalette.paleGoldenrod, DrumSet.crashCymbal1);
    add(ColorPalette.khaki, DrumSet.crashCymbal1);
    add(ColorPalette.darkKhaki, DrumSet.crashCymbal1);
    add(ColorPalette.gold, DrumSet.crashCymbal1);
    add(ColorPalette.cornsilk, DrumSet.crashCymbal1);
    add(ColorPalette.blanchedAlmond, DrumSet.crashCymbal1);
    add(ColorPalette.bisque, DrumSet.crashCymbal1);
    add(ColorPalette.navajoWhite, DrumSet.crashCymbal1);
    add(ColorPalette.wheat, DrumSet.crashCymbal1);
    add(ColorPalette.burlyWood, DrumSet.crashCymbal1);
    add(ColorPalette.tan, DrumSet.crashCymbal1);
    add(ColorPalette.rosyBrown, DrumSet.crashCymbal1);
    add(ColorPalette.sandyBrown, DrumSet.crashCymbal1);
    add(ColorPalette.goldenrod, DrumSet.crashCymbal1);
    add(ColorPalette.darkGoldenrod, DrumSet.crashCymbal1);
    add(ColorPalette.peru, DrumSet.crashCymbal1);
    add(ColorPalette.chocolate, DrumSet.crashCymbal1);
    add(ColorPalette.saddleBrown, DrumSet.crashCymbal1);
    add(ColorPalette.sienna, DrumSet.crashCymbal1);
    add(ColorPalette.brown, DrumSet.crashCymbal1);
    add(ColorPalette.maroon, DrumSet.crashCymbal1);
    add(ColorPalette.darkOliveGreen, DrumSet.handClap);
    add(ColorPalette.olive, DrumSet.handClap);
    add(ColorPalette.oliveDrab, DrumSet.handClap);
    add(ColorPalette.yellowGreen, DrumSet.handClap);
    add(ColorPalette.limeGreen, DrumSet.handClap);
    add(ColorPalette.lime, DrumSet.handClap);
    add(ColorPalette.lawnGreen, DrumSet.handClap);
    add(ColorPalette.chartreuse, DrumSet.handClap);
    add(ColorPalette.greenYellow, DrumSet.handClap);
    add(ColorPalette.springGreen, DrumSet.handClap);
    add(ColorPalette.mediumSpringGreen, DrumSet.handClap);
    add(ColorPalette.lightGreen, DrumSet.handClap);
    add(ColorPalette.paleGreen, DrumSet.handClap);
    add(ColorPalette.darkSeaGreen, DrumSet.handClap);
    add(ColorPalette.mediumSeaGreen, DrumSet.handClap);
    add(ColorPalette.seaGreen, DrumSet.handClap);
    add(ColorPalette.forestGreen, DrumSet.handClap);
    add(ColorPalette.green, DrumSet.handClap);
    add(ColorPalette.darkGreen, DrumSet.handClap);
    add(ColorPalette.mediumAquamarine, DrumSet.highTom);
    add(ColorPalette.aqua, DrumSet.highTom);
    add(ColorPalette.cyan, DrumSet.highTom);
    add(ColorPalette.lightCyan, DrumSet.highTom);
    add(ColorPalette.paleTurquoise, DrumSet.highTom);
    add(ColorPalette.aquamarine, DrumSet.highTom);
    add(ColorPalette.turquoise, DrumSet.highTom);
    add(ColorPalette.mediumTurquoise, DrumSet.highTom);
    add(ColorPalette.darkTurquoise, DrumSet.highTom);
    add(ColorPalette.lightSeaGreen, DrumSet.highTom);
    add(ColorPalette.cadetBlue, DrumSet.highTom);
    add(ColorPalette.darkCyan, DrumSet.highTom);
    add(ColorPalette.teal, DrumSet.highTom);
    add(ColorPalette.lightSteelBlue, DrumSet.lowTom);
    add(ColorPalette.powderBlue, DrumSet.lowTom);
    add(ColorPalette.lightBlue, DrumSet.lowTom);
    add(ColorPalette.skyBlue, DrumSet.lowTom);
    add(ColorPalette.lightSkyBlue, DrumSet.lowTom);
    add(ColorPalette.deepSkyBlue, DrumSet.lowTom);
    add(ColorPalette.dodgerBlue, DrumSet.lowTom);
    add(ColorPalette.cornflowerBlue, DrumSet.lowTom);
    add(ColorPalette.steelBlue, DrumSet.lowTom);
    add(ColorPalette.royalBlue, DrumSet.lowTom);
    add(ColorPalette.blue, DrumSet.lowTom);
    add(ColorPalette.mediumBlue, DrumSet.lowTom);
    add(ColorPalette.darkBlue, DrumSet.lowTom);
    add(ColorPalette.navy, DrumSet.lowTom);
    add(ColorPalette.midnightBlue, DrumSet.lowTom);
    add(ColorPalette.lavender, DrumSet.lowFloorTom);
    add(ColorPalette.thistle, DrumSet.lowFloorTom);
    add(ColorPalette.plum, DrumSet.lowFloorTom);
    add(ColorPalette.violet, DrumSet.lowFloorTom);
    add(ColorPalette.orchid, DrumSet.lowFloorTom);
    add(ColorPalette.fuchsia, DrumSet.lowFloorTom);
    add(ColorPalette.magenta, DrumSet.lowFloorTom);
    add(ColorPalette.mediumOrchid, DrumSet.lowFloorTom);
    add(ColorPalette.mediumPurple, DrumSet.lowFloorTom);
    add(ColorPalette.blueViolet, DrumSet.lowFloorTom);
    add(ColorPalette.darkViolet, DrumSet.lowFloorTom);
    add(ColorPalette.darkOrchid, DrumSet.lowFloorTom);
    add(ColorPalette.darkMagenta, DrumSet.lowFloorTom);
    add(ColorPalette.purple, DrumSet.lowFloorTom);
    add(ColorPalette.indigo, DrumSet.lowFloorTom);
    add(ColorPalette.darkSlateBlue, DrumSet.lowFloorTom);
    add(ColorPalette.rebeccaPurple, DrumSet.lowFloorTom);
    add(ColorPalette.slateBlue, DrumSet.lowFloorTom);
    add(ColorPalette.mediumSlateBlue, DrumSet.lowFloorTom);
  }

}
