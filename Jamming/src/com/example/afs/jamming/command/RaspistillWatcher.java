package com.example.afs.jamming.command;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import com.example.afs.jamming.command.Raspistill.RaspistillBuilder;

public class RaspistillWatcher {

  private File imageFile;
  private Raspistill raspistill;
  private WatchService watchService;

  public RaspistillWatcher(Options options) {
    try {
      imageFile = new File(options.getImageLatestFilename());
      RaspistillBuilder raspistillBuilder = new RaspistillBuilder(options.getImageCaptureProgram());
      raspistillBuilder.setBrightness(options.getImageBrightness());
      raspistillBuilder.setHeight(options.getImageHeight());
      raspistillBuilder.setImageWhiteBalance(options.getImageWhiteBalance());
      raspistillBuilder.setImageWhiteBalanceGain(options.getImageWhiteBalanceGain());
      raspistillBuilder.setLatestFilename(imageFile.getName());
      raspistillBuilder.setOutputFilename(options.getImageOutputFilename());
      raspistillBuilder.setRotation(options.getImageRotation());
      raspistillBuilder.setWidth(options.getImageWidth());
      raspistill = raspistillBuilder.build();
      watchService = FileSystems.getDefault().newWatchService();
      String parentPathname = imageFile.getCanonicalFile().getParentFile().getCanonicalPath();
      Path parentpath = Paths.get(parentPathname);
      parentpath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
          onShutdown();
        }
      });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String takePhoto() {
    try {
      imageFile.delete();
      raspistill.takePhoto();
      String imageFilename = null;
      while (imageFilename == null) {
        WatchKey watchKey = watchService.take();
        for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
          Path path = (Path) watchEvent.context();
          String pathString = path.toString();
          if (imageFile.getName().equals(pathString)) {
            imageFilename = pathString;
          }
        }
        watchKey.reset();
      }
      return imageFilename;
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void onShutdown() {
    raspistill.terminate();
  }
}
