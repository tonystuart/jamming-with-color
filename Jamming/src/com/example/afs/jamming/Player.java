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
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

// Note that sequencer.addMetaEventListener MidiUtils.isMetaEndOfTrack only detects end of track if not looping
// See: https://docs.oracle.com/javase/tutorial/sound/MIDI-seq-adv.html
// See: javax.sound.midi.Track.ImmutableEndOfTrack
// See: javax.sound.midi.Track.add(MidiEvent)

public class Player {

  private final class SequenceQueueReceiver implements Receiver {
    private long lastTick;
    private Sequence nextSequence;

    @Override
    public void close() {
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
      long tick = sequencer.getTickPosition();
      long previousTick = lastTick;
      lastTick = tick; // must save before setSequence calls us right back
      if (tick < previousTick) {
        if (isTerminate) {
          // The Java Sound Event Dispatcher thread is very hard terminate
          //sequencer.setLoopCount(0);
          //sequencer.stop(); // this hangs
          //sequencer.close();
          System.exit(0);
        } else {
          if (nextSequence != null) {
            System.err.println("Selecting new sequence, tick=" + tick + ", previousTick=" + previousTick);
            try {
              sequencer.setSequence(nextSequence);
            } catch (InvalidMidiDataException e) {
              throw new RuntimeException(e);
            }
            nextSequence = null;
          }
        }
      }
    }

    public void setNextSequence(Sequence nextSequence) {
      this.nextSequence = nextSequence;
    }
  }

  private boolean isTerminate;
  private SequenceQueueReceiver receiver;
  private Sequencer sequencer;

  public Player(float tempoFactor) {
    try {
      sequencer = MidiSystem.getSequencer();
      receiver = new SequenceQueueReceiver();
      sequencer.getTransmitter().setReceiver(receiver);
      sequencer.open();
      sequencer.setTempoFactor(tempoFactor);
    } catch (MidiUnavailableException e) {
      throw new RuntimeException(e);
    }
  }

  public void loop(Sequence sequence) {
    try {
      if (sequencer.isRunning()) {
        receiver.setNextSequence(sequence);
      } else {
        sequencer.setSequence(sequence);
        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
        sequencer.start();
      }
    } catch (InvalidMidiDataException e) {
      throw new RuntimeException(e);
    }
  }

  public void stop() {
    sequencer.setLoopCount(0);
  }

  public void terminate() {
    isTerminate = true;
  }

}
