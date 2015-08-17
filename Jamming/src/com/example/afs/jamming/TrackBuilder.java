// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class TrackBuilder {

  private Track track;

  public TrackBuilder(Track track) {
    this.track = track;
  }

  public void addMessage(long tick, MidiMessage message) {
    MidiEvent midiEvent = new MidiEvent(message, tick);
    track.add(midiEvent);
  }

  public void addMetaMessage(long tick, int channel, int type, byte[] data, int length) {
    try {
      MetaMessage message = new MetaMessage(type, data, length);
      addMessage(tick, message);
    } catch (InvalidMidiDataException e) {
      throw new RuntimeException(e);
    }
  }

  public long addNote(long tick, int channel, int note, int velocity, long duration) {
    try {
      addShortMessage(tick, channel, ShortMessage.NOTE_ON, note, velocity);
      addShortMessage(tick + duration, channel, ShortMessage.NOTE_OFF, note, 0);
      return tick;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void addShortMessage(long tick, int channel, int command, int data1, int data2) {
    try {
      ShortMessage message = new ShortMessage(command, channel, data1, data2);
      addMessage(tick, message);
    } catch (InvalidMidiDataException e) {
      throw new RuntimeException(e);
    }
  }
}
