// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.command;

public class Command {
  public enum Event {
    CALIBRATE, CHANNEL, END_OF_TRACK, LOOP, NEXT, PAUSE, PROGRAM, QUIT, RESUME, TEMPO
  }

  private Event event;

  public Command(Event event) {
    this.event = event;
  }

  public Event getEvent() {
    return event;
  }

  @Override
  public String toString() {
    return "Command [event=" + event + "]";
  }

}
