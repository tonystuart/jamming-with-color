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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.imageio.ImageIO;
import javax.sound.midi.Sequence;

import com.example.afs.jamming.command.Command;
import com.example.afs.jamming.command.CommandLineParser;
import com.example.afs.jamming.command.MidiChannelCommand;
import com.example.afs.jamming.command.MidiProgramCommand;
import com.example.afs.jamming.command.MidiTempoFactorCommand;
import com.example.afs.jamming.command.Monitor;
import com.example.afs.jamming.command.Options;
import com.example.afs.jamming.command.RaspistillWatcher;
import com.example.afs.jamming.command.Trace.TraceOption;
import com.example.afs.jamming.image.Frame;
import com.example.afs.jamming.image.ImageViewer;
import com.example.afs.jamming.image.ImageViewer.Availability;
import com.example.afs.jamming.image.Scene;
import com.example.afs.jamming.sound.Converter;
import com.example.afs.jamming.sound.Player;

public class Jamming {

  public static void main(String[] args) {
    CommandLineParser parser = new CommandLineParser();
    Options options = parser.parseOptions(args);
    Jamming jamming = new Jamming(options);
    jamming.jam();
    System.exit(0);
  }

  private ImageViewer afterImageViewer;
  private ImageViewer beforeImageViewer;
  private int loopCount;
  private int midiChannel;
  private int midiProgram;
  private boolean midiProgramLoop;
  private float midiTempoFactor;
  private Monitor monitor;
  private Options options;
  private Player player;
  private Frame previousFrame;

  private BlockingQueue<Command> queue;
  private RaspistillWatcher raspistillWatcher;

  public Jamming(Options options) {
    this.options = options;
    midiChannel = options.getMidiChannel();
    midiProgram = options.getMidiProgram();
    midiTempoFactor = options.getMidiTempoFactor();
    midiProgramLoop = options.isMidiProgramLoop();
    if (options.isDisplayImage()) {
      beforeImageViewer = new ImageViewer();
      afterImageViewer = new ImageViewer();
    }
    raspistillWatcher = new RaspistillWatcher(options);
    queue = new LinkedBlockingQueue<>();
    player = new Player(queue, midiTempoFactor);
    monitor = new Monitor(queue);
    monitor.start();
  }

  public void jam() {
    boolean isRunning = true;
    boolean isPlaying = true;
    processFrame();
    while (isRunning) {
      Command command = getNextCommand();
      switch (command.getEvent()) {
        case CALIBRATE:
          calibrate();
          break;
        case CHANNEL:
          midiChannel = ((MidiChannelCommand) command).getMidiChannel();
          break;
        case END_OF_TRACK:
          if (isPlaying) {
            processFrame();
          }
          break;
        case LOOP:
          midiProgramLoop = !midiProgramLoop;
          break;
        case MAP:
          displayColorMap();
          break;
        case NEXT:
          player.stop();
          processFrame();
          break;
        case PAUSE:
          isPlaying = false;
          player.stop();
          break;
        case PROGRAM:
          midiProgram = ((MidiProgramCommand) command).getMidiProgram();
          break;
        case QUIT:
          isRunning = false;
          player.close();
          break;
        case RESUME:
          isPlaying = true;
          player.stop();
          processFrame();
          break;
        case TEMPO:
          midiTempoFactor = ((MidiTempoFactorCommand) command).getMidiTempoFactor();
          player.setTempoFactor(midiTempoFactor);
          break;
        default:
          throw new UnsupportedOperationException(command.toString());
      }
    };
  }

  private void displayColorMap() {
    System.out.println("Current color map");
    System.out.println(options.getColorMap());
  }

  private void calibrate() {
    processFrame();
    options.getColorMap().calibrate(previousFrame.getScene().getMappedBlocks());
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

  private int getMidiProgram() {
    if (midiProgramLoop) {
      midiProgram = (midiProgram + 1) % 127;
    }
    return midiProgram;
  }

  private Command getNextCommand() {
    try {
      return queue.take();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void play(Frame frame) {
    if (options.isPlayAudio()) {
      Scene scene = frame.getScene();
      if (scene.containsBlocks()) {
        Converter converter = new Converter(options, frame.getMidiChannel(), frame.getMidiProgram());
        Sequence sequence = converter.convert(scene);
        player.play(sequence);
      }
    }
  }

  private void processFrame() {
    String filename = raspistillWatcher.takePhoto();
    BufferedImage image = getImage(filename, loopCount);
    if (options.isDisplayImage()) {
      beforeImageViewer.display(image, "Before " + loopCount, Availability.TRANSIENT);
    }
    Scene scene = new Scene(options, image);
    Frame currentFrame = new Frame(scene, midiChannel, getMidiProgram());
    if (currentFrame.isDifferentFrom(previousFrame)) {
      if (options.isDisplayImage()) {
        afterImageViewer.display(image, "After " + loopCount, Availability.PERSISTENT);
      }
      play(currentFrame);
      previousFrame = currentFrame;
    } else {
      play(previousFrame);
    }
    loopCount++;
  }

}
