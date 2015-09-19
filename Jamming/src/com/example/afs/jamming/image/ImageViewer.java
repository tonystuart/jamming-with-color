// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming.image;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.example.afs.jamming.rowmapper.MappedBlock;

public class ImageViewer extends JFrame {

  public enum Availability {
    PERSISTENT, TRANSIENT
  }

  private class ImagePanel extends JPanel {
    private BufferedImage image;

    public void display(BufferedImage image) {
      this.image = image;
      setSize(image.getWidth(null), image.getHeight(null));
      repaint();
    }

    public void paint(Graphics g) {
      if (image != null) {
        g.drawImage(image, 0, 0, null);
        for (Entry<String, MappedBlock> entry : highlights.entrySet()) {
          String value = entry.getKey();
          MappedBlock mappedBlock = entry.getValue();
          int left = mappedBlock.getBlock().getItem().getLeft();
          int top = mappedBlock.getBlock().getItem().getTop();
          int width = mappedBlock.getBlock().getItem().getWidth();
          int height = mappedBlock.getBlock().getItem().getHeight();
          FontMetrics metrics = g.getFontMetrics();
          int markerWidth = metrics.stringWidth(value) + 6;
          int markerHeight = metrics.getHeight() + 6;
          int x = left + ((width - markerWidth) / 2);
          int y = top + ((height - markerHeight) / 2);
          g.setColor(Color.WHITE);
          // https://bugs.openjdk.java.net/browse/JDK-4080020
          g.fillRect(x, y, markerWidth, markerHeight);
          g.setColor(Color.BLACK);
          g.drawString(value, x + 3, y + markerHeight - 5);
        }
      }
    }
  }

  private Map<String, MappedBlock> highlights = new ConcurrentHashMap<>();
  private ImagePanel imagePanel;

  public ImageViewer() {
    imagePanel = new ImagePanel();
    getContentPane().add(imagePanel, BorderLayout.CENTER);
  }

  public void addHighlight(int index, MappedBlock mappedBlock) {
    highlights.put(Integer.toString(index + 1), mappedBlock);
    imagePanel.repaint();
  }

  public void clearHighlights() {
    highlights.clear();
    imagePanel.repaint();
  }

  public void display(BufferedImage image, String title, Availability availability) {
    if (availability == Availability.TRANSIENT) {
      image = copyImage(image);
    }
    imagePanel.display(image);
    setSize(imagePanel.getSize());
    setTitle(title + " - " + image.getWidth(null) + "x" + image.getHeight(null));
    setAutoRequestFocus(false);
    setVisible(true);
  }

  public void removeHighlight(int index, MappedBlock mappedBlock) {
  }

  @Override
  public void toFront() {
    // Disable default action to prevent distracting window switch
  }

  private BufferedImage copyImage(BufferedImage source) {
    BufferedImage image = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
    Graphics graphics = image.getGraphics();
    graphics.drawImage(source, 0, 0, null);
    graphics.dispose();
    return image;
  }

}
