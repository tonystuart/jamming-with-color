// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.color.hsb;

import com.example.afs.jamming.color.rgb.Color;

public class HsbColor extends Color {

  private float[] hsbValues = new float[3];

  public HsbColor(float hue) {
    this(null, hue, 1f, 1f);
  }

  public HsbColor(int rgb) {
    this(null, rgb);
  }

  public HsbColor(String name, float hue) {
    this(name, hue, 1f, 1f);
  }

  public HsbColor(String name, float hue, float saturation, float brightness) {
    super(name, java.awt.Color.HSBtoRGB(hue, saturation, brightness) & 0xffffff); // mask off alpha channel
    hsbValues[0] = hue;
    hsbValues[1] = saturation;
    hsbValues[2] = brightness;
  }

  public HsbColor(String name, int rgb) {
    super(name, rgb);
    java.awt.Color.RGBtoHSB(red, green, blue, hsbValues);
  }

  public float getBrightness() {
    return hsbValues[2];
  }

  public float getHue() {
    return hsbValues[0];
  }

  public float getSaturation() {
    return hsbValues[1];
  }

  @Override
  public String toString() {
    String s = String.format("%06x %.2f/%.2f/%.2f", getRgb(), hsbValues[0], hsbValues[1], hsbValues[2]);
    String name = getName();
    if (name != null) {
      s += " (" + name + ")";
    }
    return s;
  }

}
