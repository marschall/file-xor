package com.github.marschall.filexor;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.junit.Rule;
import org.junit.Test;

public class MainTest {

  @Rule
  public final FileSystemRule rule = new FileSystemRule();

  @Test
  public void xor() throws IOException {
    // given
    FileSystem fileSystem = rule.getFileSystem();
    Path input = fileSystem.getPath("file.txt");
    Path output = input.resolveSibling("file.txt.xor");
    byte[] data = new byte[]{0b00000000, 0b01010101, (byte) 0b10101010, (byte) 0b11111111};
    byte[] dataXor = new byte[]{(byte) 0b11111111, (byte) 0b10101010, 0b01010101, (byte) 0b00000000};
    try (OutputStream stream = Files.newOutputStream(input, StandardOpenOption.CREATE_NEW)) {
      stream.write(data);
    }
    
    // when
    Main.xor(input);
    
    // then
    assertTrue(Files.exists(input));
    assertTrue(Files.exists(output));
    
    assertArrayEquals(data, Files.readAllBytes(input));
    assertArrayEquals(dataXor, Files.readAllBytes(output));
  }

  @Test
  public void deXor() throws IOException {
    // given
    FileSystem fileSystem = rule.getFileSystem();
    Path input = fileSystem.getPath("file.txt");
    Path output = input.resolveSibling("file.txt.xor");
    byte[] data = new byte[]{0b00000000, 0b01010101, (byte) 0b10101010, (byte) 0b11111111};
    byte[] dataXor = new byte[]{(byte) 0b11111111, (byte) 0b10101010, 0b01010101, (byte) 0b00000000};
    try (OutputStream stream = Files.newOutputStream(output, StandardOpenOption.CREATE_NEW)) {
      stream.write(dataXor);
    }
    
    // when
    Main.xor(output);
    
    // then
    assertTrue(Files.exists(input));
    assertTrue(Files.exists(output));
    
    assertArrayEquals(data, Files.readAllBytes(input));
    assertArrayEquals(dataXor, Files.readAllBytes(output));
  }

}
