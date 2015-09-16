// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.sound;

import java.util.concurrent.BlockingQueue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import com.example.afs.jamming.command.Command;
import com.example.afs.jamming.command.Command.Type;
import com.sun.media.sound.MidiUtils;

// Note that sequencer.addMetaEventListener MidiUtils.isMetaEndOfTrack only detects end of track if not looping
// See: https://docs.oracle.com/javase/tutorial/sound/MIDI-seq-adv.html
// See: javax.sound.midi.Track.ImmutableEndOfTrack
// See: javax.sound.midi.Track.add(MidiEvent)

public class Player {

  private class PlayerMetaEventListener implements MetaEventListener {
    public void meta(MetaMessage event) {
      onMetaMessage(event);
    }
  }

  private BlockingQueue<Command> queue;
  private Sequencer sequencer;

  public Player(BlockingQueue<Command> queue, float tempoFactor) {
    try {
      this.queue = queue;
      sequencer = MidiSystem.getSequencer();
      sequencer.addMetaEventListener(new PlayerMetaEventListener());
      sequencer.open();
      sequencer.setTempoFactor(tempoFactor);
    } catch (MidiUnavailableException e) {
      throw new RuntimeException(e);
    }
  }

  public void close() {
    sequencer.close();
  }

  public void play(Sequence sequence) {
    try {
      sequencer.setSequence(sequence);
      sequencer.setTickPosition(0);
      sequencer.start();
    } catch (InvalidMidiDataException e) {
      throw new RuntimeException(e);
    }
  }

  public void setTempoFactor(float tempoFactor) {
    sequencer.setTempoFactor(tempoFactor);
  }

  public void stop() {
    sequencer.stop();
  }

  private void onMetaMessage(MetaMessage event) {
    if (event.getType() == MidiUtils.META_END_OF_TRACK_TYPE) {
      pushEndOfTrackCommand();
    }
  }

  private void pushEndOfTrackCommand() {
    queue.add(new Command(Type.END_OF_TRACK));
  }

}
