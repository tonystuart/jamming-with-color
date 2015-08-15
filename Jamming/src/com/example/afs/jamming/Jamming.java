// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.sound.midi.Sequence;

import com.example.afs.jamming.ItemFinder.Background;

public class Jamming {

  public static void main(String[] args) {
    CommandLineParser parser = new CommandLineParser();
    Options options = parser.parseOptions(args);
    Jamming jamming = new Jamming(options);
    jamming.jam();
  }

  private ImageViewer afterView;
  private ImageViewer beforeView;
  private Options options;
  private Player player;
  private int program;
  private RaspistillWatcher raspistillWatcher;

  public Jamming(Options options) {
    this.options = options;
    if (options.isDisplayImage()) {
      beforeView = new ImageViewer();
      afterView = new ImageViewer();
    }
    program = options.getMidiProgram();
    raspistillWatcher = new RaspistillWatcher(options);
  }

  public void jam() {
    int loopCount = 0;
    List<Block> oldBlocks = Collections.emptyList();
    for (;;) {
      String filename = raspistillWatcher.takePhoto();
      BufferedImage image = getImage(filename, loopCount);
      List<Block> newBlocks = getBlocks(image, loopCount);
      if (options.getObjectFuzziness() == 0 || isDifferent(oldBlocks, newBlocks, options.getObjectFuzziness())) {
        playBlocks(image, newBlocks);
        oldBlocks = newBlocks;
      } else {
        playBlocks(image, oldBlocks);
      }
      loopCount++;
    }
  }

  private Block convertItemToBlock(ImageProcessor imageProcessor, Item item) {
    int averageRgb = imageProcessor.getAverageRgb(item);
    if (options.isDisplayImage()) {
      imageProcessor.setAverageRgb(item, averageRgb);
    }
    Entry<? extends Color, Key> entry = options.getColorMap().findClosestEntry(averageRgb);
    Block block = new Block(item, entry.getKey(), entry.getValue(), averageRgb);
    return block;
  }

  private void despeckleItems(ImageProcessor imageProcessor, List<Item> items) {
    if (options.getObjectMinimumSize() > 0) {
      int despeckleCount = imageProcessor.despeckle(options.getObjectMinimumSize());
      if (options.isVerbose()) {
        System.out.println("Despeckling removed " + despeckleCount + " speckles");
        displayInfo("After despeckling", items);
      }
    }
  }

  private void displayBlockInfo(List<Block> blocks) {
    if (options.isVerbose()) {
      ArrayList<Block> sortedBlocks = new ArrayList<>(blocks);
      sortedBlocks.sort(new Comparator<Block>() {
        @Override
        public int compare(Block o1, Block o2) {
          return o1.getItem().getLeft() - o2.getItem().getLeft();
        }
      });
      displayInfo("After mapping and sorting:", sortedBlocks);
    }
  }

  private void displayInfo(String message, List<?> items) {
    System.out.println(message);
    System.out.println("There are " + items.size() + " item(s) present");
    for (Object item : items) {
      System.out.println(item);
    }
  }

  private List<Block> getBlocks(BufferedImage image, int loopCount) {
    ImageProcessor imageProcessor = new ImageProcessor(image, Background.LESS_THAN, options.getBackgroundThreshold(), options.getObjectMinimumSize());
    List<Item> items = imageProcessor.getItems();
    if (options.isVerbose()) {
      displayInfo("Original image", items);
    }
    despeckleItems(imageProcessor, items);
    List<Block> blocks = new LinkedList<>();
    for (Item item : items) {
      Block block = convertItemToBlock(imageProcessor, item);
      blocks.add(block);
    }
    if (options.isDisplayImage()) {
      afterView.display(image, "After " + loopCount);
    }
    displayBlockInfo(blocks);
    return blocks;
  }

  private BufferedImage getImage(String fileName, int loopCount) {
    if (options.isVerbose()) {
      System.out.println("Reading updated image");
    }
    File file = new File(fileName);
    BufferedImage image;
    try {
      image = ImageIO.read(file);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    if (options.isDisplayImage()) {
      beforeView.display(image, "Before " + loopCount);
    }
    return image;
  }

  private Player getPlayer() {
    if (player == null) {
      player = new Player(options.getMidiTempoFactor());
    }
    return player;
  }

  private boolean isDifferent(List<Block> oldBlocks, List<Block> newBlocks, int fuzziness) {
    boolean isDifferent = true;
    if (oldBlocks.size() == newBlocks.size()) {
      Iterator<Block> oldIterator = oldBlocks.iterator();
      Iterator<Block> newIterator = newBlocks.iterator();
      isDifferent = false;
      while (!isDifferent && oldIterator.hasNext()) {
        Block oldBlock = oldIterator.next();
        Block newBlock = newIterator.next();
        if (!oldBlock.fuzzyEquals(newBlock, fuzziness)) {
          if (options.isVerbose()) {
            System.out.println("The new image is different from the previous image within " + fuzziness + " pixels");
            System.out.println("The previous image contains " + oldBlock);
            System.out.println("The new image contains " + newBlock);
          }
          isDifferent = true;
        }
      }
    }
    return isDifferent;
  }

  private void playBlocks(BufferedImage image, List<Block> blocks) {
    if (options.isPlayAudio()) {
      if (blocks.size() > 0) {
        Converter converter = new Converter(options.getMidiTickOrigin(), options.getMidiVelocity());
        Sequence sequence = converter.convert(blocks, image.getWidth(), 250, options.getMidiChannel(), program);
        if (options.isMidiProgramLoop()) {
          program = (program + 1) % 127;
          if (options.isVerbose()) {
            System.out.println("Looping to program " + program);
          }
        }
        getPlayer().play(sequence);
      }
    }
  }

}
