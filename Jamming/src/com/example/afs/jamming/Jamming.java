// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.example.afs.jamming.command.CommandLineParser;
import com.example.afs.jamming.command.Options;
import com.example.afs.jamming.command.RaspistillWatcher;
import com.example.afs.jamming.command.Trace.TraceOption;
import com.example.afs.jamming.image.ImageViewer;
import com.example.afs.jamming.image.Scene;
import com.example.afs.jamming.sound.Player;

public class Jamming {

  public static void main(String[] args) {
    CommandLineParser parser = new CommandLineParser();
    Options options = parser.parseOptions(args);
    Jamming jamming = new Jamming(options);
    jamming.jam();
  }

  private ImageViewer afterImageViewer;
  private ImageViewer beforeImageViewer;
  private Options options;
  private Player player;
  private RaspistillWatcher raspistillWatcher;

  public Jamming(Options options) {
    this.options = options;
    if (options.isDisplayImage()) {
      beforeImageViewer = new ImageViewer();
      afterImageViewer = new ImageViewer();
    }
    raspistillWatcher = new RaspistillWatcher(options);
  }

  public void jam() {
    int loopCount = 0;
    Scene oldScene = null;
    for (;;) {
      String filename = raspistillWatcher.takePhoto();
      BufferedImage image = getImage(filename, loopCount);
      if (options.isDisplayImage()) {
        beforeImageViewer.display(image, "Before " + loopCount);
      }
      Scene newScene = new Scene(options, image, loopCount);
      if (newScene.isDifferentFrom(oldScene)) {
        if (options.isDisplayImage()) {
          afterImageViewer.display(image, "After " + loopCount);
        }
        playScene(newScene);
        oldScene = newScene;
      } else {
        playScene(oldScene);
      }
      loopCount++;
    }
  }

  private BufferedImage getImage(String fileName, int loopCount) {
    if (options.getTrace().isSet(TraceOption.INPUT)) {
      System.out.println("Reading next image");
    }
    File file = new File(fileName);
    BufferedImage image;
    try {
      image = ImageIO.read(file);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return image;
  }

  private Player getPlayer() {
    if (player == null) {
      player = new Player(options.getMidiTempoFactor());
    }
    return player;
  }

  private void playScene(Scene scene) {
    if (options.isPlayAudio()) {
      if (scene.containsBlocks()) {
        getPlayer().play(scene);
      }
    }
  }

}
