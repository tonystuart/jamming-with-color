// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

import com.example.afs.jamming.Converter.TickOrigin;
import com.example.afs.jamming.ItemFinder.Background;
import com.example.afs.jamming.Trace.TraceOption;

public class Options {

  public static final int DRUM_CHANNEL = 9;
  public static final int STANDARD_DRUM_KIT = 1;

  private Background backgroundCondition = Background.LESS_THAN;
  private int backgroundThreshold = Color.getColor(64, 64, 64);
  private ColorMap colorMap = ColorMaps.getSingleton().getDefault();
  private String imageBaseFilename = "Jamming";
  private int imageBrightness = 40;
  private String imageCaptureProgram = "raspistill";
  private int imageHeight = 768;
  private int imageRotation = 90;
  private int imageWidth = 1024;
  private boolean isDisplayImage = true;
  private boolean isMidiProgramLoop = false;
  private boolean isPlayAudio = true;
  private boolean isVerbose = false;
  private int midiChannel = DRUM_CHANNEL;
  private int midiProgram = STANDARD_DRUM_KIT;
  private float midiTempoFactor = 1.0f;
  private TickOrigin midiTickOrigin = TickOrigin.MIDPOINT;
  private int midiVelocity = 127;
  private int objectFuzziness = 10;
  private int objectMinimumSize = 30;
  private int rowSpacing = 0;
  private int threads = 0;
  private Trace trace = new Trace(TraceOption.CONVERSION, TraceOption.PERFORMANCE);

  public Background getBackgroundCondition() {
    return backgroundCondition;
  }

  public int getBackgroundThreshold() {
    return backgroundThreshold;
  }

  public ColorMap getColorMap() {
    return colorMap;
  }

  public String getImageBaseFilename() {
    return imageBaseFilename;
  }

  public int getImageBrightness() {
    return imageBrightness;
  }

  public String getImageCaptureProgram() {
    return imageCaptureProgram;
  }

  public int getImageHeight() {
    return imageHeight;
  }

  public String getImageLatestFilename() {
    return imageBaseFilename + ".jpg";
  }

  public String getImageOutputFilename() {
    return imageBaseFilename + ".tmp";
  }

  public int getImageRotation() {
    return imageRotation;
  }

  public int getImageWidth() {
    return imageWidth;
  }

  public int getMidiChannel() {
    return midiChannel;
  }

  public int getMidiProgram() {
    return midiProgram;
  }

  public float getMidiTempoFactor() {
    return midiTempoFactor;
  }

  public TickOrigin getMidiTickOrigin() {
    return midiTickOrigin;
  }

  public int getMidiVelocity() {
    return midiVelocity;
  }

  public int getObjectFuzziness() {
    return objectFuzziness;
  }

  public int getObjectMinimumSize() {
    return objectMinimumSize;
  }

  public int getRowSpacing() {
    return rowSpacing;
  }

  public int getThreads() {
    return threads;
  }

  public Trace getTrace() {
    return trace;
  }

  public boolean isDisplayImage() {
    return isDisplayImage;
  }

  public boolean isMidiProgramLoop() {
    return isMidiProgramLoop;
  }

  public boolean isPlayAudio() {
    return isPlayAudio;
  }

  public boolean isVerbose() {
    return isVerbose;
  }

  public void setBackgroundCondition(Background backgroundCondition) {
    this.backgroundCondition = backgroundCondition;
  }

  public void setBackgroundThreshold(int backgroundRgb) {
    this.backgroundThreshold = backgroundRgb;
  }

  public void setColorMap(ColorMap colorMap) {
    this.colorMap = colorMap;
  }

  public void setDisplayImage(boolean isImage) {
    this.isDisplayImage = isImage;
  }

  public void setImageBaseFilename(String imageBaseFilename) {
    this.imageBaseFilename = imageBaseFilename;
  }

  public void setImageBrightness(int imageBrightness) {
    this.imageBrightness = imageBrightness;
  }

  public void setImageCaptureProgram(String imageCaptureProgram) {
    this.imageCaptureProgram = imageCaptureProgram;
  }

  public void setImageHeight(int imageHeight) {
    this.imageHeight = imageHeight;
  }

  public void setImageRotation(int imageRotation) {
    this.imageRotation = imageRotation;
  }

  public void setImageWidth(int imageWidth) {
    this.imageWidth = imageWidth;
  }

  public void setMidiChannel(int channel) {
    this.midiChannel = channel;
  }

  public void setMidiProgram(int program) {
    this.midiProgram = program;
  }

  public void setMidiProgramLoop(boolean isMidiProgramLoop) {
    this.isMidiProgramLoop = isMidiProgramLoop;
  }

  public void setMidiTempoFactor(float tempoFactor) {
    this.midiTempoFactor = tempoFactor;
  }

  public void setMidiTickOrigin(TickOrigin tickOrigin) {
    this.midiTickOrigin = tickOrigin;
  }

  public void setMidiVelocity(int velocity) {
    this.midiVelocity = velocity;
  }

  public void setObjectFuzziness(int fuzziness) {
    this.objectFuzziness = fuzziness;
  }

  public void setObjectMinimumSize(int minimumSize) {
    this.objectMinimumSize = minimumSize;
  }

  public void setPlayAudio(boolean isAudio) {
    this.isPlayAudio = isAudio;
  }

  public void setRowSpacing(int rowSpacing) {
    this.rowSpacing = rowSpacing;
  }

  public void setThreads(int threads) {
    this.threads = threads;
  }

  public void setTrace(Trace trace) {
    this.trace = trace;
  }

  public void setVerbose(boolean isVerbose) {
    this.isVerbose = isVerbose;
  }

}
