// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.command;

public class MidiProgramCommand extends Command {

  private int midiProgram;

  public MidiProgramCommand(int midiProgram) {
    super(Event.PROGRAM);
    this.midiProgram = midiProgram;
  }

  public int getMidiProgram() {
    return midiProgram;
  }

  @Override
  public String toString() {
    return "MidiProgramCommand [midiProgram=" + midiProgram + "]";
  }

}
