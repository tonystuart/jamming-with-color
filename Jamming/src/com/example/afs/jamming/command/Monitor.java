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
        String[] tokens = line.split("\\s");
        Command command = new Command(tokens);
        queue.put(command);
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
