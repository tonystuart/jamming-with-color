// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.sound;

import java.util.concurrent.LinkedBlockingQueue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import com.example.afs.jamming.image.Scene;

// Note that sequencer.addMetaEventListener MidiUtils.isMetaEndOfTrack only detects end of track if not looping
// See: https://docs.oracle.com/javase/tutorial/sound/MIDI-seq-adv.html
// See: javax.sound.midi.Track.ImmutableEndOfTrack
// See: javax.sound.midi.Track.add(MidiEvent)

public class Player {

  private LinkedBlockingQueue<MetaMessage> queue = new LinkedBlockingQueue<MetaMessage>();
  private Sequencer sequencer;

  public Player(float tempoFactor) {
    try {
      sequencer = MidiSystem.getSequencer();
      MetaEventListener listener = new MetaEventListener() {
        public void meta(MetaMessage event) {
          if (event.getType() == 47) {
            queue.add(event);
          }
        }
      };
      sequencer.addMetaEventListener(listener);
      sequencer.open();
      sequencer.setTempoFactor(tempoFactor);
    } catch (MidiUnavailableException e) {
      throw new RuntimeException(e);
    }
  }

  public void play(Scene scene) {
    try {
      Sequence sequence = scene.getSequence();
      sequencer.setSequence(sequence);
      sequencer.setTickPosition(0);
      sequencer.start();
      queue.take();
    } catch (InvalidMidiDataException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
