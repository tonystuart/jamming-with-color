package com.example.afs.jamming.rowmapper;

import java.awt.image.BufferedImage;

import com.example.afs.jamming.Options;

public class RowMapperFactory {
  public static RowMapper getRowMapper(Options options, BufferedImage image) {
    RowMapper rowMapper;
    if (options.getRowSpacing() > 0) {
      rowMapper = new MultipleRowMapper(options.getRowSpacing(), image.getWidth());
    } else {
      rowMapper = new SingleRowMapper(image.getWidth());
    }
    return rowMapper;
  }
}