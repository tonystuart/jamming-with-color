// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.image;

import com.example.afs.jamming.color.base.ColorMap;

public class Frame {
  private int midiChannel;
  private int midiProgram;
  private Scene scene;
  private ColorMap colorMap;

  public Frame(Scene scene, int midiChannel, int midiProgram, ColorMap colorMap) {
    this.scene = scene;
    this.midiChannel = midiChannel;
    this.midiProgram = midiProgram;
    this.colorMap = colorMap;
  }

  public int getMidiChannel() {
    return midiChannel;
  }

  public int getMidiProgram() {
    return midiProgram;
  }

  public Scene getScene() {
    return scene;
  }

  public boolean isDifferentFrom(Frame that) {
    return that == null || this.midiChannel != that.midiChannel || this.midiProgram != that.midiProgram || this.colorMap != that.colorMap || this.scene.isDifferentFrom(that.scene);
  }

  @Override
  public String toString() {
    return "Frame [midiChannel=" + midiChannel + ", midiProgram=" + midiProgram + ", scene=" + scene + "]";
  }

}