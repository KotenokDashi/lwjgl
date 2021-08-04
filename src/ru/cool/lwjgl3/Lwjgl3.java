package ru.cool.lwjgl3;

import org.joml.Vector3f;

public class Lwjgl3 {

    public static void main(String[] args) {
        Window window = new Window(800, 640, 1);
        window.createWindow();
    }

    public static void printVector(Vector3f vec){
        System.out.println("x: " + vec.x() + " y: " + vec.y() + " z: " + vec.z());
    }
}
