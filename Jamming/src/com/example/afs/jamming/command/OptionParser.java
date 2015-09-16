// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.command;

import com.example.afs.jamming.Jamming;
import com.example.afs.jamming.color.base.ColorMaps;
import com.example.afs.jamming.command.Raspistill.WhiteBalance;
import com.example.afs.jamming.command.Trace.TraceOption;
import com.example.afs.jamming.image.ItemFinder.Background;
import com.example.afs.jamming.sound.Converter.TickOrigin;

public class OptionParser {

  public void exitWithUsage(String invalidOption) {
    System.err.println("Unrecognized option: " + invalidOption);
    exitWithUsage();
  }

  public Options parseOptions(String[] args) {
    Options options = new Options();
    for (String arg : args) {
      try {
        String[] tokens = arg.split("=");
        if (tokens.length > 0 && tokens[0].startsWith("--")) {
          if (tokens.length == 2 && tokens[0].equals("--backgroundCondition")) {
            options.setBackgroundCondition(Background.valueOf(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--backgroundThreshold")) {
            options.setBackgroundThreshold(Integer.parseInt(tokens[1], 16));
          } else if (tokens.length == 2 && tokens[0].equals("--colorMap")) {
            options.setColorMap(ColorMaps.getSingleton().get(tokens[1]));
          } else if (tokens[0].equals("--help")) {
            exitWithUsage();
          } else if (tokens.length == 2 && tokens[0].equals("--imageBaseFilename")) {
            options.setImageBaseFilename(tokens[1]);
          } else if (tokens.length == 2 && tokens[0].equals("--imageBrightness")) {
            options.setImageBrightness(Integer.parseInt(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--imageCaptureProgram")) {
            options.setImageCaptureProgram(tokens[1]);
          } else if (tokens.length == 2 && tokens[0].equals("--imageHeight")) {
            options.setImageHeight(Integer.parseInt(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--imageRotation")) {
            options.setImageRotation(Integer.parseInt(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--imageWhiteBalance")) {
            options.setImageWhiteBalance(WhiteBalance.valueOf(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--imageWhiteBalanceGain")) {
            options.setImageWhiteBalanceGain(tokens[1]);
          } else if (tokens.length == 2 && tokens[0].equals("--imageWidth")) {
            options.setImageWidth(Integer.parseInt(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--isDisplayImage")) {
            options.setDisplayImage(Boolean.valueOf(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--isPlayAudio")) {
            options.setPlayAudio(Boolean.valueOf(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--isVerbose")) {
            setVerbose(options, Boolean.valueOf(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--midiBaseVelocity")) {
            options.setMidiBaseVelocity(Integer.parseInt(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--midiChannel")) {
            options.setMidiChannel(Integer.parseInt(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--midiProgram")) {
            options.setMidiProgram(Integer.parseInt(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--midiTempoFactor")) {
            options.setMidiTempoFactor(Float.parseFloat(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--midiTickOrigin")) {
            options.setMidiTickOrigin(TickOrigin.valueOf(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--objectFuzziness")) {
            options.setObjectFuzziness(Integer.parseInt(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--objectMinimumSize")) {
            options.setObjectMinimumSize(Integer.parseInt(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--rowSpacing")) {
            options.setRowSpacing(Integer.parseInt(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--threads")) {
            options.setThreads(Integer.parseInt(tokens[1]));
          } else if (tokens.length == 2 && tokens[0].equals("--trace")) {
            options.getTrace().set(TraceOption.valueOf(tokens[1]));
          } else {
            exitWithUsage(arg);
          }
        } else {
          exitWithUsage(arg);
        }
      } catch (RuntimeException e) {
        e.printStackTrace();
        exitWithUsage(arg);
      }
    }
    return options;
  }

  private void exitWithUsage() {
    Options options = new Options();
    System.err.println("Usage: java " + Jamming.class.getName() + " options file");
    System.err.println("Where: options is one or more of the following:");
    System.err.println("       --backgroundCondition=" + getOptions(Background.class) + " (defaults to " + options.getBackgroundCondition() + ")");
    System.err.println("       --backgroundThreshold=hex-rgb-value (defaults to " + Integer.toHexString(options.getBackgroundThreshold()) + ")");
    System.err.println("       --colorMap=color map (defaults to " + options.getColorMap().getName() + ")");
    System.err.println("       --imageBaseFilename=name (defaults to " + options.getImageBaseFilename() + ")");
    System.err.println("       --imageBrightness=0-100 (defaults to " + options.getImageBrightness() + ")");
    System.err.println("       --imageCaptureProgram=path (defaults to " + options.getImageCaptureProgram() + ")");
    System.err.println("       --imageHeight=0-1944 pixels (defaults to " + options.getImageHeight() + ")");
    System.err.println("       --imageRotation=0-359 degrees (defaults to " + options.getImageRotation() + ")");
    System.err.println("       --imageWhiteBalance=" + getOptions(WhiteBalance.class) + " (defaults to " + options.getImageWhiteBalance() + ")");
    System.err.println("       --imageWhiteBalanceGain=red,blue (defaults to " + options.getImageWhiteBalanceGain() + ", only used with imageWhiteBalance=off)");
    System.err.println("       --imageWidth=0-2592 pixels (defaults to " + options.getImageWidth() + ")");
    System.err.println("       --isDisplayImage=false|true (defaults to " + options.isDisplayImage() + ")");
    System.err.println("       --isPlayAudio=false|true (defaults to " + options.isPlayAudio() + ")");
    System.err.println("       --isVerbose=false|true (defaults to " + options.isVerbose() + ", clears/sets all trace flags)");
    System.err.println("       --midiBaseVelocity=1-127 (defaults to " + options.getMidiBaseVelocity() + ", audio volume, lower numbers permit greater dynamic range)");
    System.err.println("       --midiChannel=channel (defaults to " + options.getMidiChannel() + ")");
    System.err.println("       --midiProgram=midi program number (defaults to " + options.getMidiProgram() + ")");
    System.err.println("       --midiTempoFactor=0.0-1.0 (defaults to " + options.getMidiTempoFactor() + ")");
    System.err.println("       --midiTickOrigin=" + getOptions(TickOrigin.class) + " (defaults to " + options.getMidiTickOrigin() + ")");
    System.err.println("       --objectFuzziness=min pixel delta between items in successive frames (defaults to " + options.getObjectFuzziness() + ")");
    System.err.println("       --objectMinimizeSize=max-speckle-size (defaults to " + options.getObjectMinimumSize() + ")");
    System.err.println("       --rowSpacing=pixels between rows (defaults to " + options.getRowSpacing() + ", zero to disable multiple rows)");
    System.err.println("       --threads=image-processing-threads (defaults to " + options.getThreads() + ", zero to default to number of processors)");
    System.err.println("       --trace=" + getOptions(TraceOption.class) + " (defaults to " + options.getTrace() + ", may be specified more than once)");
    System.err.println("Supported color maps:");
    for (String name : ColorMaps.getSingleton().getNames()) {
      System.err.println("       " + name);
    }
    System.exit(1);
  }

  private String getOptions(Class<? extends Enum<?>> enumClass) {
    StringBuilder s = new StringBuilder();
    for (Enum<?> option : enumClass.getEnumConstants()) {
      if (s.length() > 0) {
        s.append("|");
      }
      s.append(option.name());
    }
    return s.toString();
  }

  private void setVerbose(Options options, Boolean isVerbose) {
    options.setVerbose(isVerbose);
    if (options.isVerbose()) {
      options.getTrace().setAll();
    } else {
      options.getTrace().clearAll();
    }
  }
}
