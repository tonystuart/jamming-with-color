// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.command;

import java.util.Arrays;

public class Command {

  public enum Type {
    END_OF_TRACK, MARKER, MONITOR
  }

  private String command;
  private String[] tokens;
  private Type type;

  public Command(String[] tokens) {
    this(Type.MONITOR);
    this.tokens = tokens;
  }

  public Command(Type type) {
    this.type = type;
  }

  public Command(Type type, String command) {
    this.type = type;
    this.command = command;
  }

  public String getCommand() {
    return command;
  }

  public String[] getOperands() {
    String[] operands;
    if (tokens == null) {
      operands = null;
    } else {
      operands = new String[tokens.length - 1];
      System.arraycopy(tokens, 1, operands, 0, tokens.length - 1);
    }
    return operands;
  }

  public String getToken(int index) {
    return tokens[index];
  }

  public String[] getTokens() {
    return tokens;
  }

  public boolean isEmpty() {
    return type == Type.MONITOR && tokens[0].isEmpty();
  }

  public boolean isType(Type type) {
    return this.type == type;
  }

  public boolean matches(String name) {
    if (type == Type.MONITOR) {
      int minimumLength = getMinimumLength(name);
      int commandLength = tokens[0].length();
      if (commandLength >= minimumLength && commandLength <= name.length()) {
        int compareLength = Math.min(name.length(), commandLength);
        if (tokens[0].equalsIgnoreCase(name.substring(0, compareLength))) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "Command [type=" + type + ", tokens=" + Arrays.toString(tokens) + "]";
  }

  private int getMinimumLength(String name) {
    int index = 0;
    int length = name.length();
    while (index < length && Character.isUpperCase(name.charAt(index))) {
      index++;
    }
    return index;
  }

}
