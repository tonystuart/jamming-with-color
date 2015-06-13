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

  public void exitWithUsage() {
    Options options = new Options();
    System.err.println("Usage: java " + Jamming.class.getName() + " options file");
    System.err.println("Where: options is one or more of the following:");
    System.err.println("       --audio=false|true (defaults to " + options.isAudio() + ")");
    System.err.println("       --backgroundCondition=" + getOptions(Background.class) + " (defaults to " + options.getBackgroundCondition() + ")");
    System.err.println("       --backgroundThreshold=hex-rgb-value (defaults to " + Integer.toHexString(options.getBackgroundThreshold()) + ")");
    System.err.println("       --channel=channel (defaults to " + options.getChannel());
    System.err.println("       --colorMap=color map (defaults to " + options.getColorMap());
    System.err.println("       --fuzziness=min pixel delta between items in successive frames (defaults to " + options.getFuzziness() + ")");
    System.err.println("       --image=false|true (defaults to " + options.isImage() + ")");
    System.err.println("       --loopDelay=delay-milliseconds, zero to disable looping (defaults to " + options.getLoopDelay() + ")");
    System.err.println("       --minimizeSize=max-speckle-size (defaults to " + options.getMinimumSize() + ")");
    System.err.println("       --program=midi program number (defaults to " + options.getProgram() + ")");
    System.err.println("       --programLoop=false|true (defaults to " + options.isProgramLoop() + ")");
    System.err.println("       --tempoFactor=0.0-1.0 (defaults to " + options.getTempoFactor() + ")");
    System.err.println("       --tickOrigin=" + getOptions(TickOrigin.class) + " (defaults to " + options.getTickOrigin() + ")");
    System.err.println("       --velocity=1-127 (defaults to " + options.getVelocity() + ")");
    System.err.println("       --verbose=false|true (defaults to " + options.isVerbose() + ")");
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
        if (tokens.length == 2 && tokens[0].equals("--audio")) {
          options.setAudio(Boolean.valueOf(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--backgroundCondition")) {
          options.setBackgroundCondition(Background.valueOf(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--backgroundThreshold")) {
          options.setBackgroundThreshold(Integer.parseInt(tokens[1], 16));
        } else if (tokens.length == 2 && tokens[0].equals("--channel")) {
          options.setChannel(Integer.parseInt(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--colorMap")) {
          options.setColorMap(ColorMaps.getSingleton().get(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--fuzziness")) {
          options.setFuzziness(Integer.parseInt(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--image")) {
          options.setImage(Boolean.valueOf(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--loopDelay")) {
          options.setLoopDelay(Integer.parseInt(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--minimumSize")) {
          options.setMinimumSize(Integer.parseInt(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--program")) {
          options.setProgram(Integer.parseInt(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--programLoop")) {
          options.setProgramLoop(Boolean.valueOf(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--tempoFactor")) {
          options.setTempoFactor(Float.parseFloat(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--tickOrigin")) {
          options.setTickOrigin(TickOrigin.valueOf(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--velocity")) {
          options.setVelocity(Integer.parseInt(tokens[1]));
        } else if (tokens.length == 2 && tokens[0].equals("--verbose")) {
          options.setVerbose(Boolean.valueOf(tokens[1]));
        } else {
          System.err.println("Unrecognized option: " + arg);
          exitWithUsage();
        }
      } else {
        options.addFile(arg);
      }
    }
    if (options.getFileNames().size() == 0) {
      System.err.println("Missing filename");
      exitWithUsage();
    }
    if (options.getColorMap() == null) {
      System.err.println("Invalid color map");
      exitWithUsage();
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
