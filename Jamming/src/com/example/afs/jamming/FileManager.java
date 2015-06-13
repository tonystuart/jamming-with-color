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
import java.util.HashMap;
import java.util.Map;

public class FileManager {
  private Map<String, FileDetails> fileDetailsMap = new HashMap<>();
  private int loopDelay;

  public FileManager(int loopDelay) {
    this.loopDelay = loopDelay;
  }

  public File getFile(String fileName) {
    return getFileDetails(fileName).getFile();
  }

  public FileDetails getFileDetails(String fileName) {
    FileDetails fileDetails = fileDetailsMap.get(fileName);
    if (fileDetails == null) {
      fileDetails = new FileDetails(new File(fileName));
      fileDetailsMap.put(fileName, fileDetails);
    } else {
      while (!fileDetails.isModified()) {
        try {
          Thread.sleep(loopDelay);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return fileDetails;
  }
}
