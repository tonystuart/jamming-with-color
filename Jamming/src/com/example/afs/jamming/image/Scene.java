// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.image;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.sound.midi.Sequence;

import com.example.afs.jamming.color.rgb.Color;
import com.example.afs.jamming.command.Options;
import com.example.afs.jamming.command.Trace.TraceOption;
import com.example.afs.jamming.rowmapper.MappedBlock;
import com.example.afs.jamming.rowmapper.RowMapper;
import com.example.afs.jamming.rowmapper.RowMapperFactory;
import com.example.afs.jamming.sound.Composable;
import com.example.afs.jamming.utility.IterableArray;

public class Scene {

  private List<Block> blocks;
  private BufferedImage image;
  private MappedBlock[] mappedBlocks;
  private int mappedWidth;
  private int maximumItemHeight;
  private Options options;
  private Sequence sequence;

  public Scene(Options options, BufferedImage image) {
    this.options = options;
    this.image = image;
    extractBlocks();
    extractMappedBlocks();
  }

  public boolean containsBlocks() {
    return blocks.size() > 0;
  }

  public List<Block> getBlocks() {
    return blocks;
  }

  public Iterable<MappedBlock> getMappedBlocks() {
    return new IterableArray<MappedBlock>(mappedBlocks);
  }

  public int getMappedWidth() {
    return mappedWidth;
  }

  public int getMaximumItemHeight() {
    return maximumItemHeight;
  }

  public Sequence getSequence() {
    return sequence;
  }

  public boolean isDifferentFrom(Scene that) {
    boolean isDifferent = true;
    if (that != null) {
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

  @Override
  public String toString() {
    return "Scene [mappedWidth=" + mappedWidth + ", maximumItemHeight=" + maximumItemHeight + "]";
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
        displayInfo("After despeckling there are " + items.size() + " item(s) present (" + despeckleCount + " speckle(s) removed)", items);
      }
    }
  }

  private void displayInfo(String message, Iterable<?> items) {
    System.out.println(message);
    for (Object item : items) {
      System.out.println(item);
    }
  }

  private void extractBlocks() {
    ImageProcessor imageProcessor = new ImageProcessor(image, options);
    List<Item> items = imageProcessor.extractItems();
    if (options.getTrace().isSet(TraceOption.INPUT)) {
      displayInfo("In the original image, there are " + items.size() + " item(s) present", items);
    }
    despeckleItems(imageProcessor, items);
    blocks = new LinkedList<>();
    for (Item item : items) {
      maximumItemHeight = Math.max(maximumItemHeight, item.getHeight());
      Block block = convertItemToBlock(imageProcessor, item);
      blocks.add(block);
    }
    if (options.getTrace().isSet(TraceOption.OUTPUT)) {
      displayInfo("After color conversion, there are " + blocks.size() + " block(s) present", blocks);
    }
  }

  private void extractMappedBlocks() {
    int positionIndex = 0;
    mappedBlocks = new MappedBlock[blocks.size()];
    RowMapper rowMapper = RowMapperFactory.getRowMapper(options, image);
    for (Block block : blocks) {
      mappedBlocks[positionIndex++] = rowMapper.getPosition(block);
    }
    Arrays.sort(mappedBlocks, new Comparator<MappedBlock>() {
      @Override
      public int compare(MappedBlock o1, MappedBlock o2) {
        int deltaRow = o1.getRow() - o2.getRow();
        if (deltaRow != 0) {
          return deltaRow;
        }
        int deltaLeft = o1.getLeft() - o2.getLeft();
        if (deltaLeft != 0) {
          return deltaLeft;
        }
        return 0;
      }
    });
    mappedWidth = rowMapper.getMappedWidth();
    if (options.getTrace().isSet(TraceOption.SCENE)) {
      displayInfo("The scene contains " + blocks.size() + " block(s)", getMappedBlocks());
    }
    if (options.getTrace().isSet(TraceOption.MAPPING)) {
      System.out.println("The scene contains the following color mapping(s)");
      for (MappedBlock mappedBlock : getMappedBlocks()) {
        int row = mappedBlock.getRow();
        Color averageColor = mappedBlock.getBlock().getAverageColor();
        Color matchingColor = mappedBlock.getBlock().getColor();
        Composable composable = mappedBlock.getBlock().getComposable();
        System.out.println(row + ": " + averageColor + " " + matchingColor + " " + composable);
      }
    }
  }
}