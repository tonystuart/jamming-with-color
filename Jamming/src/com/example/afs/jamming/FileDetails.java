// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

import java.io.File;

public class FileDetails {
  private File file;
  private long lastModified;

  public FileDetails(File file) {
    this.file = file;
    lastModified = file.lastModified();
  }

  public File getFile() {
    return file;
  }

  public boolean isModified() {
    boolean isModified;
    long thisModified = file.lastModified();
    if (thisModified == lastModified) {
      isModified = false;
    } else {
      isModified = true;
      lastModified = thisModified;
    }
    return isModified;
  }

}
