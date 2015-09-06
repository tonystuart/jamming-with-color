// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.sound;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;

import com.example.afs.jamming.color.hsb.HsbColor;
import com.example.afs.jamming.color.rgb.Color;
import com.example.afs.jamming.command.Trace;
import com.example.afs.jamming.command.Trace.TraceOption;
import com.example.afs.jamming.image.Block;
import com.example.afs.jamming.rowmapper.Limits;
import com.example.afs.jamming.rowmapper.RowMapper;
import com.sun.media.sound.MidiUtils;

public class Converter {

  public enum TickOrigin {
    LEFT, MIDPOINT
  }

  private class Tracer {

    private class TraceComparator implements Comparator<TraceItem> {
      @Override
      public int compare(TraceItem o1, TraceItem o2) {
        int deltaRow = o1.getLimits().getRow() - o2.getLimits().getRow();
        if (deltaRow != 0) {
          return deltaRow;
        }
        int deltaLeft = o1.getLimits().getLeft() - o2.getLimits().getRight();
        if (deltaLeft != 0) {
          return deltaLeft;
        }
        return 0;
      }
    }

    private class TraceItem {
      private Block block;
      private Limits limits;

      public TraceItem(Limits limits, Block block) {
        this.limits = limits;
        this.block = block;
      }

      public Block getBlock() {
        return block;
      }

      public Limits getLimits() {
        return limits;
      }
    }

    private List<TraceItem> traceItems = new ArrayList<>();

    private void clear() {
      traceItems.clear();
    }

    private void display() {
      traceItems.sort(new TraceComparator());
      System.out.println(traceItems.size() + " block(s) present");
      for (TraceItem traceItem : traceItems) {
        Block block = traceItem.getBlock();
        Limits limits = traceItem.getLimits();
        int averageRgb = block.getAverageRgb();
        Color matchingColor = block.getColor();
        if (matchingColor instanceof HsbColor) {
          Color averageHsb = new HsbColor(averageRgb);
          System.out.printf("row=%04d, left=%04d, right=%04d, averageHsb=%s, matchingHsb=%s %s\n", limits.getRow(), limits.getLeft(), limits.getRight(), averageHsb, matchingColor, block.getComposable());
        } else {
          int distance = Color.getDistance(block.getAverageRgb(), matchingColor);
          System.out.printf("row=%04d, left=%04d, right=%04d, averageRgb=%06x, matchingRgb=%s, distance=%d %s\n", limits.getRow(), limits.getLeft(), limits.getRight(), averageRgb, matchingColor, distance, block.getComposable());
        }
      }
    }

    private void trace(Limits limits, Block block) {
      traceItems.add(new TraceItem(limits, block));
    }
  }

  public static final int TICKS_PER_BEAT = 250;
  private RowMapper rowMapper;
  private TickOrigin tickOrigin;
  private Tracer tracer;
  private int velocity;

  public Converter(TickOrigin tickOrigin, int velocity, RowMapper rowMapper, Trace trace) {
    this.tickOrigin = tickOrigin;
    this.velocity = velocity;
    this.rowMapper = rowMapper;
    this.tracer = trace.isSet(TraceOption.CONVERSION) ? new Tracer() : null;
  }

  public Sequence convert(List<Block> blocks, int ticksPerBeat, int channel, int program) {
    try {
      Sequence sequence = new Sequence(Sequence.PPQ, ticksPerBeat);
      TrackBuilder trackBuilder = new TrackBuilder(sequence.createTrack());
      trackBuilder.addShortMessage(0, channel, ShortMessage.PROGRAM_CHANGE, program, 0);
      if (tracer != null) {
        tracer.clear();
      }
      for (Block block : blocks) {
        Limits limits = rowMapper.getLimits(block);
        int left = limits.getLeft();
        int right = limits.getRight();
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
        if (tracer != null) {
          tracer.trace(limits, block);
        }
      }
      int lastTick = rowMapper.getTotalWidth();
      trackBuilder.addMetaMessage(lastTick, channel, MidiUtils.META_END_OF_TRACK_TYPE, null, 0);
      if (tracer != null) {
        tracer.display();
      }
      return sequence;
    } catch (InvalidMidiDataException e) {
      throw new RuntimeException(e);
    }
  }

}
