// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.command;

public class MidiTempoFactorCommand extends Command {

  private float midiTempoFactor;

  public MidiTempoFactorCommand(float midiTempoFactor) {
    super(Event.TEMPO);
    this.midiTempoFactor = midiTempoFactor;
  }

  public float getMidiTempoFactor() {
    return midiTempoFactor;
  }

  @Override
  public String toString() {
    return "MidiTempoFactorCommand [midiTempoFactor=" + midiTempoFactor + "]";
  }

}
