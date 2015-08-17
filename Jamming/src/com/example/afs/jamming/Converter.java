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
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;

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
      TrackBuilder trackBuilder = new TrackBuilder(sequence.createTrack());
      trackBuilder.addShortMessage(0, channel, ShortMessage.PROGRAM_CHANGE, program, 0);
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
        block.getComposable().addToTrack(trackBuilder, tick, channel, velocity, duration);
      }
      trackBuilder.addMetaMessage(tickCount, channel, MidiUtils.META_END_OF_TRACK_TYPE, null, 0);
      return sequence;
    } catch (InvalidMidiDataException e) {
      throw new RuntimeException(e);
    }
  }

}
