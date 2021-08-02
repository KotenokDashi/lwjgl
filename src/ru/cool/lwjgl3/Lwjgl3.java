package ru.cool.lwjgl3;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Lwjgl3 {

    public static void main(String[] args) {
        Window window = new Window(800, 640, 1);
        window.createWindow();

    }
}
