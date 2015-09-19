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

import com.example.afs.jamming.color.base.ColorMaps;
import com.example.afs.jamming.command.Command;
import com.example.afs.jamming.command.Command.Type;
import com.example.afs.jamming.command.Monitor;
import com.example.afs.jamming.command.OptionParser;
import com.example.afs.jamming.command.Options;
import com.example.afs.jamming.command.RaspistillWatcher;
import com.example.afs.jamming.command.Trace.TraceOption;
import com.example.afs.jamming.image.Frame;
import com.example.afs.jamming.image.ImageViewer;
import com.example.afs.jamming.image.ImageViewer.Availability;
import com.example.afs.jamming.image.Scene;
import com.example.afs.jamming.rowmapper.MappedBlock;
import com.example.afs.jamming.sound.Converter;
import com.example.afs.jamming.sound.MarkerComposable;
import com.example.afs.jamming.sound.Player;

public class Jamming {

  public static void main(String[] args) {
    OptionParser parser = new OptionParser();
    Options options = parser.parseOptions(args);
    Jamming jamming = new Jamming(options);
    jamming.jam();
    System.exit(0);
  }

  private ImageViewer afterImageViewer;
  private ImageViewer beforeImageViewer;
  private Frame currentFrame;
  private boolean isPlaying;
  private boolean isRunning;
  private int loopCount;
  private int midiChannel;
  private int midiProgram;
  private boolean midiProgramLoop;
  private float midiTempoFactor;
  private Monitor monitor;

  private Options options;
  private Player player;
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
    isRunning = true;
    isPlaying = true;
    processFrame();
    while (isRunning) {
      Command command = getNextCommand();
      processCommand(command);
    };
  }

  private void displayHelp() {
    System.out.println("\nConsole monitor commands:");
    System.out.println("  Calibrate def - calibrate current color map");
    System.out.println("  Channel n - select midi channel n (zero based)");
    System.out.println("  Image - toggle image processing");
    System.out.println("  Loop - toggle loop through midi programs");
    System.out.println("  Map [map] - display/set current color map");
    System.out.println("  Next - stop current frame and play next");
    System.out.println("  Pause - pause play until resume");
    System.out.println("  Program n - select midi program n");
    System.out.println("  Quit - terminate program");
    System.out.println("  Resume - reset and/or resume play");
    System.out.println("  Rows - display/set row spacing");
    System.out.println("  Tempo f - set midi tempo to f");
    System.out.println("  Tron t - set trace option t on");
    System.out.println("  Troff t - set trace option t off");
    System.out.println("\nMap options (name or number):");
    int mapNumber = 1;
    for (String name : ColorMaps.getSingleton().getNames()) {
      System.out.println("  " + (mapNumber++) + " - " + name);
    }
    System.out.println("\nTrace options:");
    for (Enum<?> option : TraceOption.class.getEnumConstants()) {
      System.out.println("  " + option.name());
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
      } else {
        try {
          Thread.sleep(1000);
          queue.add(new Command(Type.END_OF_TRACK));
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  private void processCommand(Command command) {
    try {
      if (command.isType(Type.MARKER)) {
        processMarkerEvent(command.getCommand());
      } else if (command.isType(Type.END_OF_TRACK)) {
        if (isPlaying) {
          processFrame();
        }
      } else if (command.matches("CAlibrate")) {
        options.getColorMap().calibrate(command.getOperands());
      } else if (command.matches("CHannel")) {
        midiChannel = Integer.parseInt(command.getToken(1));
      } else if (command.matches("Help")) {
        displayHelp();
      } else if (command.matches("Image")) {
        processImageCommand();
      } else if (command.matches("Loop")) {
        processMidiLoopCommand();
      } else if (command.matches("Map")) {
        processMapCommand(command.getOperands());
      } else if (command.matches("Next")) {
        player.stop();
        processFrame();
      } else if (command.matches("PAuse")) {
        isPlaying = false;
        player.stop();
      } else if (command.matches("PRogram")) {
        midiProgram = Integer.parseInt(command.getToken(1));
      } else if (command.matches("Quit")) {
        isRunning = false;
        player.close();
      } else if (command.matches("Resume")) {
        isPlaying = true;
        player.stop();
        processFrame();
      } else if (command.matches("Rows")) {
        processRowCommand(command.getOperands());
      } else if (command.matches("Tempo")) {
        midiTempoFactor = Float.parseFloat(command.getToken(1));
        player.setTempoFactor(midiTempoFactor);
      } else if (command.matches("TROFF")) {
        options.getTrace().clear(TraceOption.valueOf(command.getToken(1).toUpperCase()));
        player.setTempoFactor(midiTempoFactor);
      } else if (command.matches("TRON")) {
        options.getTrace().set(TraceOption.valueOf(command.getToken(1).toUpperCase()));
        player.setTempoFactor(midiTempoFactor);
      } else if (!command.isEmpty()) {
        throw new UnsupportedOperationException(command.toString());
      }
    } catch (RuntimeException e) {
      System.err.println("An exception occurred while processing the command");
      System.err.println(e);
      System.err.println("Enter help for a complete list of commands");
    }
  }

  private void processFrame() {
    if (options.isProcessImage()) {
      String filename = raspistillWatcher.takePhoto();
      BufferedImage image = getImage(filename, loopCount);
      if (options.isDisplayImage()) {
        beforeImageViewer.display(image, "Before " + loopCount, Availability.TRANSIENT);
      }
      afterImageViewer.clearHighlights();
      Scene scene = new Scene(options, image);
      Frame nextFrame = new Frame(scene, midiChannel, getMidiProgram(), options.getColorMap());
      if (nextFrame.isDifferentFrom(currentFrame)) {
        if (options.isDisplayImage()) {
          afterImageViewer.display(image, "After " + loopCount, Availability.PERSISTENT);
        }
        currentFrame = nextFrame;
      }
    } else {
      afterImageViewer.clearHighlights();
    }
    if (currentFrame != null) {
      play(currentFrame);
    }
    loopCount++;
  }

  private void processImageCommand() {
    options.setProcessImage(!options.isProcessImage());
    if (options.isProcessImage()) {
      System.out.println("Image processing is enabled");
    } else {
      System.out.println("Image processing is disabled");
    }
  }

  private void processMapCommand(String[] operands) {
    if (operands.length == 0) {
      System.out.println("Current color map");
      System.out.println(options.getColorMap());
    } else if (operands.length == 1) {
      String mapName;
      try {
        int mapNumber = Integer.parseInt(operands[0]);
        mapName = ColorMaps.getSingleton().getNames()[mapNumber - 1];
      } catch (NumberFormatException e) {
        mapName = operands[0];
      }
      options.setColorMap(ColorMaps.getSingleton().get(mapName));
      System.out.println(options.getColorMap());
    }
  }

  private void processMarkerEvent(String marker) {
    if (marker.startsWith(MarkerComposable.MARKER_BEGIN)) {
      int blockIndex = Integer.parseInt(marker.substring(MarkerComposable.MARKER_BEGIN.length()));
      MappedBlock mappedBlock = currentFrame.getScene().getMappedBlocks()[blockIndex];
      afterImageViewer.addHighlight(blockIndex, mappedBlock);
    } else if (marker.startsWith(MarkerComposable.MARKER_END)) {
      int blockIndex = Integer.parseInt(marker.substring(MarkerComposable.MARKER_END.length()));
      MappedBlock mappedBlock = currentFrame.getScene().getMappedBlocks()[blockIndex];
      afterImageViewer.removeHighlight(blockIndex, mappedBlock);
    }
  }

  private void processMidiLoopCommand() {
    midiProgramLoop = !midiProgramLoop;
    if (midiProgramLoop) {
      System.out.println("Midi program looping is enabled");
    } else {
      System.out.println("Midi program looping is disabled");
    }
  }

  private void processRowCommand(String[] operands) {
    if (operands.length == 0) {
      System.out.println("Current row spacing is " + options.getRowSpacing());
    } else if (operands.length == 1) {
      options.setRowSpacing(Integer.parseInt(operands[0]));
    }
  }

}
