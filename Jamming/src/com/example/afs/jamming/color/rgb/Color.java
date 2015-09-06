// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.color.rgb;

public class Color {

  public static int getBlue(int rgb) {
    return rgb & 0xff;
  }

  public static int getColor(int red, int green, int blue) {
    return (red << 16) | (green << 8) | blue;
  }

  public static int getDistance(Color c1, Color c2) {
    int r1 = c1.getRed();
    int g1 = c1.getGreen();
    int b1 = c1.getBlue();
    int r2 = c2.getRed();
    int g2 = c2.getGreen();
    int b2 = c2.getBlue();
    return (int) Math.sqrt(Math.pow(r2 - r1, 2) + Math.pow(g2 - g1, 2) + Math.pow(b2 - b1, 2));
  }

  public static int getDistance(int rgb1, Color c2) {
    int r1 = Color.getRed(rgb1);
    int g1 = Color.getGreen(rgb1);
    int b1 = Color.getBlue(rgb1);
    int r2 = c2.getRed();
    int g2 = c2.getGreen();
    int b2 = c2.getBlue();
    return (int) Math.sqrt(Math.pow(r2 - r1, 2) + Math.pow(g2 - g1, 2) + Math.pow(b2 - b1, 2));
  }

  public static int getGreen(int rgb) {
    return (rgb >> 8) & 0xff;
  }

  public static int getRed(int rgb) {
    return (rgb >> 16) & 0xff;
  }

  public static boolean lessThan(int left, int right) {
    return ((left >> 16) & 0xff) < ((right >> 16) & 0xff) && ((left >> 8) & 0xff) < ((right >> 8) & 0xff) && (left & 0xff) < (right & 0xff);
  }

  protected int blue;
  protected int green;
  protected String name;
  protected int red;
  protected int rgb;

  public Color(int rgb) {
    this(null, rgb);
  }

  public Color(String name, int rgb) {
    this.name = name;
    this.rgb = rgb;
    this.red = Color.getRed(rgb);
    this.green = Color.getGreen(rgb);
    this.blue = Color.getBlue(rgb);
  }

  public int getBlue() {
    return blue;
  }

  public int getGreen() {
    return green;
  }

  public String getName() {
    return name;
  }

  public int getRed() {
    return red;
  }

  public int getRgb() {
    return rgb;
  }

  @Override
  public String toString() {
    String s = String.format("%06x", rgb);
    if (name != null) {
      s += " (" + name + ")";
    }
    return s;
  }

}
