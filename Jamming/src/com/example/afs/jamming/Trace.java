package com.example.afs.jamming;

import java.util.EnumSet;

public class Trace {

  public enum TraceOption {
    COMPARISON, CONVERSION, DESPECKLING, INPUT, OUTPUT, PERFORMANCE
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
