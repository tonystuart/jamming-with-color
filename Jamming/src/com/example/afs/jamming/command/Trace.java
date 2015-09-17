// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.command;

import java.util.EnumSet;

public class Trace {

  public enum TraceOption {
    COMPARISON, CONVERSION, DESPECKLING, INPUT, MAPPING, MARKER, OUTPUT, PERFORMANCE, SCENE
  }

  private EnumSet<TraceOption> traceOptions = EnumSet.noneOf(TraceOption.class);

  public Trace(TraceOption... traceOptions) {
    for (TraceOption traceOption : traceOptions) {
      set(traceOption);
    }
  }

  public void clear(TraceOption traceOption) {
    traceOptions.remove(traceOption);
  }

  public void clearAll() {
    traceOptions.clear();
  }

  public boolean isSet(TraceOption traceOption) {
    return traceOptions.contains(traceOption);
  }

  public void set(TraceOption traceOption) {
    traceOptions.add(traceOption);
  }

  public void setAll() {
    traceOptions.addAll(EnumSet.allOf(TraceOption.class));
  }

  @Override
  public String toString() {
    return traceOptions.toString();
  }

}
