// Copyright 2015 Anthony F. Stuart - All rights reserved.
//
// This program and the accompanying materials are made available
// under the terms of the GNU General Public License. For other license
// options please contact the copyright owner.
//
// This program is made available on an "as is" basis, without
// warranties or conditions of any kind, either express or implied.

package com.example.afs.jamming;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageViewer extends JFrame {

  public static class ImagePanel extends JPanel {
    private Image image;

    public void display(Image image) {
      this.image = image;
      setSize(image.getWidth(null), image.getHeight(null));
      repaint();
    }

    public void paint(Graphics g) {
      if (image != null) {
        g.drawImage(image, 0, 0, null);
      }
    }
  }

  private ImagePanel imagePanel;

  public ImageViewer() {
    imagePanel = new ImagePanel();
    getContentPane().add(imagePanel, BorderLayout.CENTER);
  }

  public void display(Image image, String title) {
    imagePanel.display(image);
    setSize(imagePanel.getSize());
    setTitle(title + " - " + image.getWidth(null) + "x" + image.getHeight(null));
    setAutoRequestFocus(false);
    setVisible(true);
  }

  @Override
  public void toFront() {
    // Disable default action to prevent distracting window switch
  }

}
