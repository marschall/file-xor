package com.github.marschall.filexor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;


public class Main {

  
  private static final int BUFFER_SIZE = 0x2000;
  
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.out.println("usage: file-xor.javar file");
      System.exit(1);
    }
    Path path = Paths.get(args[0]);
    xor(path);
  }
  
  static void xor(Path path) throws IOException {
    String fileName = path.getFileName().toString();
    
    Path target;
    if (fileName.endsWith(".xor")) {
      target = path.resolveSibling(fileName.substring(0, fileName.length() - ".xor".length()));
    } else {
      target = path.resolveSibling(fileName + ".xor");
    } 
    
    try (
        InputStream input = Files.newInputStream(path, READ);
        OutputStream output = Files.newOutputStream(target, WRITE, CREATE_NEW)) {
      xor(input, output);
    }
  }
  
  static void xor(InputStream input, OutputStream output) throws IOException {
    byte[] buffer = new byte[BUFFER_SIZE];
    int read;
    while ((read = input.read(buffer)) != -1) {
      xor(buffer, read);
      output.write(buffer, 0, read);
    }
  }
  
  static void xor(byte[] buffer, int len) {
    for (int i = 0; i < len; ++i) {
      byte value = buffer[i];
      buffer[i] = (byte) (value ^  0);
    }
  }

}
