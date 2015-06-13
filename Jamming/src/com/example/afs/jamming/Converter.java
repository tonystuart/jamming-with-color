// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import com.sun.media.sound.MidiUtils;

public class Converter {

  public enum TickOrigin {
    LEFT, MIDPOINT
  }

  public static final int TICKS_PER_BEAT = 250;

  private TickOrigin tickOrigin;
  private int velocity;

  public Converter(TickOrigin tickOrigin, int velocity) {
    this.tickOrigin = tickOrigin;
    this.velocity = velocity;
  }

  public Sequence convert(List<Block> blocks, int tickCount, int ticksPerBeat, int channel, int program) {
    try {
      Sequence sequence = new Sequence(Sequence.PPQ, ticksPerBeat);
      Track track = sequence.createTrack();
      addShortMessage(0, track, channel, ShortMessage.PROGRAM_CHANGE, program, 0);
      for (Block block : blocks) {
        int left = block.getItem().getLeft();
        int right = block.getItem().getRight();
        int duration;
        long tick;
        switch (tickOrigin) {
          case LEFT:
            duration = right - left;
            tick = left;
            break;
          case MIDPOINT:
            duration = (right - left) / 2;
            tick = left + duration;
            break;
          default:
            throw new UnsupportedOperationException(tickOrigin.toString());
        }
        int key = block.getKey().getValue();
        addNote(tick, track, channel, key, velocity, duration);
      }
      addMetaMessage(tickCount, track, channel, MidiUtils.META_END_OF_TRACK_TYPE, null, 0);
      return sequence;
    } catch (InvalidMidiDataException e) {
      throw new RuntimeException(e);
    }
  }

  private void addMessage(long tick, Track track, MidiMessage message) {
    MidiEvent midiEvent = new MidiEvent(message, tick);
    track.add(midiEvent);
  }

  private void addMetaMessage(long tick, Track track, int channel, int type, byte[] data, int length) {
    try {
      MetaMessage message = new MetaMessage(type, data, length);
      addMessage(tick, track, message);
    } catch (InvalidMidiDataException e) {
      throw new RuntimeException(e);
    }
  }

  private long addNote(long tick, Track track, int channel, int note, int velocity, long duration) {
    try {
      addShortMessage(tick, track, channel, ShortMessage.NOTE_ON, note, velocity);
      addShortMessage(tick + duration, track, channel, ShortMessage.NOTE_OFF, note, 0);
      return tick;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void addShortMessage(long tick, Track track, int channel, int command, int data1, int data2) {
    try {
      ShortMessage message = new ShortMessage(command, channel, data1, data2);
      addMessage(tick, track, message);
    } catch (InvalidMidiDataException e) {
      throw new RuntimeException(e);
    }
  }

}
