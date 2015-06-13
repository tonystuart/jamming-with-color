// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.example.afs.jamming.Converter.TickOrigin;
import com.example.afs.jamming.ItemFinder.Background;

public class Options {
  public static final int BACKGROUND_THRESHOLD = Color.getColor(64, 64, 64);
  public static final int DRUM_CHANNEL = 9;
  public static final int LOOP_DELAY_MS = 500;
  public static final int MINIMUM_SIZE = 50;
  public static final int STANDARD_DRUM_KIT = 1;
  private static final int DEFAULT_VELOCITY = 127;

  private Background backgroundCondition = Background.LESS_THAN;
  private int backgroundThreshold = BACKGROUND_THRESHOLD;
  private int channel = DRUM_CHANNEL;
  private ColorMap colorMap = ColorMaps.getSingleton().getDefault();
  private List<String> fileNames = new LinkedList<>();
  private int fuzziness = 20;
  private boolean isAudio = true;
  private boolean isImage = true;
  private boolean isProgramLoop = false;
  private boolean isVerbose = false;
  private int loopDelay = LOOP_DELAY_MS;
  private int minimumSize = MINIMUM_SIZE;
  private int program = STANDARD_DRUM_KIT;
  private float tempoFactor = 1.0f;
  private TickOrigin tickOrigin = TickOrigin.MIDPOINT;
  private int velocity = DEFAULT_VELOCITY;

  public void addFile(String fileName) {
    fileNames.add(fileName);
  }

  public Background getBackgroundCondition() {
    return backgroundCondition;
  }

  public int getBackgroundThreshold() {
    return backgroundThreshold;
  }

  public int getChannel() {
    return channel;
  }

  public ColorMap getColorMap() {
    return colorMap;
  }

  public List<String> getFileNames() {
    return Collections.unmodifiableList(fileNames);
  }

  public int getFuzziness() {
    return fuzziness;
  }

  public int getLoopDelay() {
    return loopDelay;
  }

  public int getMinimumSize() {
    return minimumSize;
  }

  public int getProgram() {
    return program;
  }

  public float getTempoFactor() {
    return tempoFactor;
  }

  public TickOrigin getTickOrigin() {
    return tickOrigin;
  }

  public int getVelocity() {
    return velocity;
  }

  public boolean isAudio() {
    return isAudio;
  }

  public boolean isImage() {
    return isImage;
  }

  public boolean isProgramLoop() {
    return isProgramLoop;
  }

  public boolean isVerbose() {
    return isVerbose;
  }

  public void setAudio(boolean isAudio) {
    this.isAudio = isAudio;
  }

  public void setBackgroundCondition(Background backgroundCondition) {
    this.backgroundCondition = backgroundCondition;
  }

  public void setBackgroundThreshold(int backgroundRgb) {
    this.backgroundThreshold = backgroundRgb;
  }

  public void setChannel(int channel) {
    this.channel = channel;
  }

  public void setColorMap(ColorMap colorMap) {
    this.colorMap = colorMap;
  }

  public void setFuzziness(int fuzziness) {
    this.fuzziness = fuzziness;
  }

  public void setImage(boolean isImage) {
    this.isImage = isImage;
  }

  public void setLoopDelay(int loopDelay) {
    this.loopDelay = loopDelay;
  }

  public void setMinimumSize(int minimumSize) {
    this.minimumSize = minimumSize;
  }

  public void setProgram(int program) {
    this.program = program;
  }

  public void setProgramLoop(boolean isProgramLoop) {
    this.isProgramLoop = isProgramLoop;
  }

  public void setTempoFactor(float tempoFactor) {
    this.tempoFactor = tempoFactor;
  }

  public void setTickOrigin(TickOrigin tickOrigin) {
    this.tickOrigin = tickOrigin;
  }

  public void setVelocity(int velocity) {
    this.velocity = velocity;
  }

  public void setVerbose(boolean isVerbose) {
    this.isVerbose = isVerbose;
  }
}
