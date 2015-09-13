// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;

import com.example.afs.jamming.command.Options;
import com.example.afs.jamming.command.Trace.TraceOption;
import com.example.afs.jamming.image.Scene;
import com.example.afs.jamming.rowmapper.MappedBlock;
import com.sun.media.sound.MidiUtils;

public class Converter {

  public enum TickOrigin {
    LEFT, MIDPOINT
  }

  public static final int DRUM_CHANNEL = 9;
  public static final int MAXIMUM_VELOCITY = 127;
  public static final int STANDARD_DRUM_KIT = 1;
  public static final int TICKS_PER_BEAT = 250;

  private int midiChannel;
  private int midiProgram;
  private Options options;

  public Converter(Options options, int midiChannel, int midiProgram) {
    this.options = options;
    this.midiChannel = midiChannel;
    this.midiProgram = midiProgram;
    if (options.getTrace().isSet(TraceOption.CONVERSION)) {
      System.out.println("midiChannel " + midiChannel);
      System.out.println("midiProgram " + midiProgram);
    }
  }

  public Sequence convert(Scene scene) {
    try {
      Sequence sequence = new Sequence(Sequence.PPQ, TICKS_PER_BEAT);
      TrackBuilder trackBuilder = new TrackBuilder(sequence.createTrack());
      trackBuilder.addShortMessage(0, midiChannel, ShortMessage.PROGRAM_CHANGE, midiProgram, 0);
      for (MappedBlock mappedBlock : scene.getMappedBlocks()) {
        int left = mappedBlock.getLeft();
        int right = mappedBlock.getRight();
        int duration;
        long tick;
        switch (options.getMidiTickOrigin()) {
          case LEFT:
            duration = right - left;
            tick = left;
            break;
          case MIDPOINT:
            duration = (right - left) / 2;
            tick = left + duration;
            break;
          default:
            throw new UnsupportedOperationException();
        }
        int velocity = scaleVelocity(scene.getMaximumItemHeight(), mappedBlock.getBlock().getItem().getHeight());
        mappedBlock.getBlock().getComposable().addToTrack(trackBuilder, tick, midiChannel, velocity, duration);
      }
      int lastTick = scene.getMappedWidth();
      trackBuilder.addMetaMessage(lastTick, midiChannel, MidiUtils.META_END_OF_TRACK_TYPE, null, 0);
      return sequence;
    } catch (InvalidMidiDataException e) {
      throw new RuntimeException(e);
    }
  }

  public int getMidiProgram() {
    return midiProgram;
  }

  private int scaleVelocity(int maximumItemHeight, int itemHeight) {
    int baseMidiVelocity = options.getMidiBaseVelocity();
    int dynamicVelocityRange = MAXIMUM_VELOCITY - baseMidiVelocity;
    int scaledVelocity = baseMidiVelocity + ((dynamicVelocityRange * itemHeight) / maximumItemHeight);
    return scaledVelocity;
  }

}
