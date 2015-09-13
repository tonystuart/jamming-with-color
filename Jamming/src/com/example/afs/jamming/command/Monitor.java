// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;

import com.example.afs.jamming.command.Command.Event;

public class Monitor extends Thread {

  private BlockingQueue<Command> queue;

  public Monitor(BlockingQueue<Command> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    try {
      String line;
      BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
      while ((line = console.readLine()) != null) {
        Command command = null;
        line = line.toLowerCase();
        String[] tokens = line.split("\\s");
        if (tokens.length == 1 && tokens[0].startsWith("ca")) {
          command = new Command(Event.CALIBRATE);
        } else if (tokens.length == 2 && tokens[0].startsWith("ch")) {
          command = new MidiChannelCommand(Integer.parseInt(tokens[1]));
        } else if (tokens.length == 1 && tokens[0].startsWith("l")) {
          command = new Command(Event.LOOP);
        } else if (tokens.length == 1 && tokens[0].startsWith("m")) {
          command = new Command(Event.MAP);
        } else if (tokens.length == 1 && tokens[0].startsWith("n")) {
          command = new Command(Event.NEXT);
        } else if (tokens.length == 1 && tokens[0].startsWith("pa")) {
          command = new Command(Event.PAUSE);
        } else if (tokens.length == 2 && tokens[0].startsWith("pr")) {
          command = new MidiProgramCommand(Integer.parseInt(tokens[1]));
        } else if (tokens.length == 1 && tokens[0].startsWith("q")) {
          command = new Command(Event.QUIT);
        } else if (tokens.length == 1 && tokens[0].startsWith("r")) {
          command = new Command(Event.RESUME);
        } else if (tokens.length == 2 && tokens[0].startsWith("t")) {
          command = new MidiTempoFactorCommand(Float.parseFloat(tokens[1]));
        } else {
          System.out.println("Calibrate   - calibrate color map");
          System.out.println("Channel <i> - select midi channel <i> (zero based)");
          System.out.println("Loop        - toggle loop through midi programs");
          System.out.println("Map         - display current color map");
          System.out.println("Next        - stop current frame and play next");
          System.out.println("Pause       - pause play until resume");
          System.out.println("Program <i> - select midi program <i>");
          System.out.println("Quit        - terminate program");
          System.out.println("Resume      - reset and/or resume play");
          System.out.println("Tempo <f>   - set midi tempo to <f>");
        }
        if (command != null) {
          queue.put(command);
        }
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
