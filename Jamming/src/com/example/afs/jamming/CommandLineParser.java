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

public class CommandLineParser {

  public void exitWithUsage(String option) {
    System.err.println("Unrecognized option: " + option);
    Options options = new Options();
    System.err.println("Usage: java " + Jamming.class.getName() + " options file");
    System.err.println("Where: options is one or more of the following:");
    System.err.println("       --backgroundCondition=" + getOptions(Background.class) + " (defaults to " + options.getBackgroundCondition() + ")");
    System.err.println("       --backgroundThreshold=hex-rgb-value (defaults to " + Integer.toHexString(options.getBackgroundThreshold()) + ")");
    System.err.println("       --colorMap=color map (defaults to " + options.getColorMap() + ")");
    System.err.println("       --imageBaseFilename=name (defaults to " + options.getImageBaseFilename() + ")");
    System.err.println("       --imageBrightness=0-100 (defaults to " + options.getImageBrightness() + ")");
    System.err.println("       --imageCaptureProgram=path (defaults to " + options.getImageCaptureProgram() + ")");
    System.err.println("       --imageHeight=0-1944 pixels (defaults to " + options.getImageHeight() + ")");
    System.err.println("       --imageRotation=0-359 degrees (defaults to " + options.getImageRotation() + ")");
    System.err.println("       --imageHeight=0-2592 pixels (defaults to " + options.getImageWidth() + ")");
    System.err.println("       --isDisplayImage=false|true (defaults to " + options.isDisplayImage() + ")");
    System.err.println("       --isPlayAudio=false|true (defaults to " + options.isPlayAudio() + ")");
    System.err.println("       --isVerbose=false|true (defaults to " + options.isVerbose() + ")");
    System.err.println("       --midiChannel=channel (defaults to " + options.getMidiChannel() + ")");
    System.err.println("       --midiProgram=midi program number (defaults to " + options.getMidiProgram() + ")");
    System.err.println("       --midiTempoFactor=0.0-1.0 (defaults to " + options.getMidiTempoFactor() + ")");
    System.err.println("       --midiTickOrigin=" + getOptions(TickOrigin.class) + " (defaults to " + options.getMidiTickOrigin() + ")");
    System.err.println("       --midiVelocity=1-127 (defaults to " + options.getMidiVelocity() + ")");
    System.err.println("       --objectFuzziness=min pixel delta between items in successive frames (defaults to " + options.getObjectFuzziness() + ")");
    System.err.println("       --objectMinimizeSize=max-speckle-size (defaults to " + options.getObjectMinimumSize() + ")");
    System.err.println("Supported color maps:");
    for (String name : ColorMaps.getSingleton().getNames()) {
      System.err.println("       " + name);
    }
    System.exit(1);
  }

  public Options parseOptions(String[] args) {
    Options options = new Options();
    for (String arg : args) {
      String[] tokens = arg.split("=");
      if (tokens.length > 0 && tokens[0].startsWith("--")) {
        if (tokens.length == 2 && tokens[0].equals("--backgroundCondition")) {
          options.setBackgroundCondition(Background.valueOf(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--backgroundThreshold")) {
          options.setBackgroundThreshold(Integer.parseInt(tokens[1], 16));
        } else if (tokens.length == 2 && tokens[0].equals("--colorMap")) {
          options.setColorMap(ColorMaps.getSingleton().get(tokens[1]));
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
        } else if (tokens.length == 2 && tokens[0].equals("--imageWidth")) {
          options.setImageWidth(Integer.parseInt(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--isDisplayImage")) {
          options.setDisplayImage(Boolean.valueOf(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--isPlayAudio")) {
          options.setPlayAudio(Boolean.valueOf(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--isVerbose")) {
          options.setVerbose(Boolean.valueOf(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--midiChannel")) {
          options.setMidiChannel(Integer.parseInt(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--midiProgram")) {
          options.setMidiProgram(Integer.parseInt(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--midiTempoFactor")) {
          options.setMidiTempoFactor(Float.parseFloat(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--midiTickOrigin")) {
          options.setMidiTickOrigin(TickOrigin.valueOf(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--midiVelocity")) {
          options.setMidiVelocity(Integer.parseInt(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--objectFuzziness")) {
          options.setObjectFuzziness(Integer.parseInt(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--objectMinimumSize")) {
          options.setObjectMinimumSize(Integer.parseInt(tokens[1]));
        } else {
          exitWithUsage(arg);
        }
      } else {
        exitWithUsage(arg);
      }
    }
    return options;
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
}
