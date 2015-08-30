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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.sound.midi.Sequence;

import com.example.afs.jamming.Trace.TraceOption;
import com.example.afs.jamming.rowmapper.RowMapper;
import com.example.afs.jamming.rowmapper.RowMapperFactory;

public class Scene {

  private List<Block> blocks;
  private Options options;
  private int program;
  private RowMapper rowMapper;
  private Sequence sequence;

  public Scene(Options options, BufferedImage image, int loopCount) {
    this.options = options;
    this.blocks = getBlocks(image, loopCount);
    this.program = options.getMidiProgram();
    this.rowMapper = RowMapperFactory.getRowMapper(options, image);
  }

  public boolean containsBlocks() {
    return blocks.size() > 0;
  }

  public Sequence getSequence() {
    if (sequence == null || options.isMidiProgramLoop()) {
      Converter converter = new Converter(options.getMidiTickOrigin(), options.getMidiVelocity(), rowMapper, options.getTrace());
      sequence = converter.convert(blocks, 250, options.getMidiChannel(), program);
      if (options.isMidiProgramLoop()) {
        program = (program + 1) % 127;
        if (options.getTrace().isSet(TraceOption.CONVERSION)) {
          System.out.println("Looping to program " + program);
        }
      }
    }
    return sequence;
  }

  public boolean isDifferentFrom(Scene that) {
    boolean isDifferent = true;
    if (that != null && !options.isMidiProgramLoop()) {
      if (that.blocks.size() == this.blocks.size()) {
        Iterator<Block> oldIterator = that.blocks.iterator();
        Iterator<Block> newIterator = this.blocks.iterator();
        isDifferent = false;
        while (!isDifferent && oldIterator.hasNext()) {
          Block oldBlock = oldIterator.next();
          Block newBlock = newIterator.next();
          if (!oldBlock.fuzzyEquals(newBlock, options.getObjectFuzziness())) {
            if (options.getTrace().isSet(TraceOption.COMPARISON)) {
              System.out.println("The new image is different from the previous image within " + options.getObjectFuzziness() + " pixels");
              System.out.println("The previous image contains " + oldBlock);
              System.out.println("The new image contains " + newBlock);
            }
            isDifferent = true;
          }
        }
      }
    }
    return isDifferent;
  }

  private Block convertItemToBlock(ImageProcessor imageProcessor, Item item) {
    int averageRgb = imageProcessor.getAverageRgb(item);
    if (options.isDisplayImage()) {
      imageProcessor.setAverageRgb(item, averageRgb);
    }
    Entry<? extends Color, ? extends Composable> entry = options.getColorMap().findClosestEntry(averageRgb);
    Block block = new Block(item, entry.getKey(), entry.getValue(), averageRgb);
    return block;
  }

  private void despeckleItems(ImageProcessor imageProcessor, List<Item> items) {
    if (options.getObjectMinimumSize() > 0) {
      int despeckleCount = imageProcessor.despeckle(items, options.getObjectMinimumSize());
      if (options.getTrace().isSet(TraceOption.DESPECKLING)) {
        System.out.println("Despeckling removed " + despeckleCount + " speckles");
        displayInfo("After despeckling", items);
      }
    }
  }

  private void displayBlockInfo(List<Block> blocks) {
    if (options.getTrace().isSet(TraceOption.OUTPUT)) {
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
    ImageProcessor imageProcessor = new ImageProcessor(image, options);
    List<Item> items = imageProcessor.extractItems();
    if (options.getTrace().isSet(TraceOption.INPUT)) {
      displayInfo("Original image", items);
    }
    despeckleItems(imageProcessor, items);
    List<Block> blocks = new LinkedList<>();
    for (Item item : items) {
      Block block = convertItemToBlock(imageProcessor, item);
      blocks.add(block);
    }
    displayBlockInfo(blocks);
    return blocks;
  }

}