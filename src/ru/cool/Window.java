package ru.cool;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40C.*;

public class Window {

    private final int WIDTH;
    private final int HEIGHT;
    private long windowId;

    public Window(int WIDTH, int HEIGHT, int windowId) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.windowId = windowId;
    }

    public void createWindow(){
        glfwInit();

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        this.windowId = glfwCreateWindow(this.WIDTH,this.HEIGHT, "Test Window", 0, 0);

        glfwMakeContextCurrent(this.windowId);

        GL.createCapabilities();

        this.update();

    }

    private void update(){

        while(!this.closeWindow() && glfwGetKey(this.windowId, GLFW_KEY_ESCAPE) != GLFW_PRESS){
            glClearColor(0f,0f,0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwPollEvents();
            glfwSwapBuffers(this.windowId);
        }

        glfwDestroyWindow(this.windowId);

    }

    private boolean closeWindow() {
        return glfwWindowShouldClose(this.windowId);
    }

}
