// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

public class HsbColor extends Color {

  private float brightness;
  private float hue;
  private float saturation;

  public HsbColor(int rgb) {
    this(null, rgb);
  }

  public HsbColor(int hue, int saturation, int brightness) {
    super(0);
    this.hue = hue;
    this.saturation = saturation;
    this.brightness = brightness;
  }

  public HsbColor(String name, int rgb) {
    super(name, rgb);

    int componentMax = (red > green) ? (blue > red ? blue : red) : (blue > green ? blue : green);
    int componentMin = (red < green) ? (blue < red ? blue : red) : (blue < green ? blue : green);

    brightness = (100 * componentMax) / 255;
    int range = componentMax - componentMin;
    if (componentMax != 0) {
      saturation = (100 * range) / componentMax;
    } else {
      saturation = 0;
    }
    if (saturation == 0) {
      hue = 0;
    } else {
      float deltaMaxRed = (100 * (componentMax - red)) / range;
      float deltaMaxGreen = (100 * (componentMax - green)) / range;
      float deltaMaxBlue = (100 * (componentMax - blue)) / range;
      if (red == componentMax) {
        hue = deltaMaxBlue - deltaMaxGreen;
      } else if (green == componentMax) {
        hue = 200 + deltaMaxRed - deltaMaxBlue;
      } else {
        hue = 400 + deltaMaxGreen - deltaMaxRed;
      }
      hue = hue / 6;
      if (hue < 0) {
        hue = hue + 100;
      }
    }
  }

  public float getBrightness() {
    return brightness;
  }

  public float getHue() {
    return hue;
  }

  public float getSaturation() {
    return saturation;
  }

  @Override
  public String toString() {
    return super.toString() + " " + hue + "/" + saturation + "/" + brightness;
  }

}
