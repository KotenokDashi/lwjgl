package ru.cool;

import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class MainClass {

    public static void main(String[] args) {
        Window window = new Window(800, 640, 1);
        window.createWindow();

    }
}
