package ru.cool.lwjgl2;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL40.*;

public class OldWindow {

    private final int WIDTH;
    private final int HEIGHT;
    private long windowId;
    private String windowTitle;
    private GLFWVidMode vidMode;

    public OldWindow(int width, int height, String windowTitle) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.windowTitle = windowTitle;
    }

    public void run()
    {
        this.init();
        glfwMakeContextCurrent(this.windowId);
        GL.createCapabilities();
        this.update();
    }

    public void init()
    {
        glfwInit();
        this.windowId = glfwCreateWindow(this.WIDTH, this.HEIGHT, this.windowTitle, 0, 0);
        this.vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if(this.windowId == NULL){
            System.out.println("Не удалось инициализировать окно " + this.windowId);
            System.exit(-1);
        }
        glfwSetWindowPos(this.windowId, (this.vidMode.width() - this.WIDTH) / 2, (this.vidMode.height() - this.HEIGHT) / 2);
        glfwSetWindowTitle(this.windowId, this.windowTitle);

    }

    private void update()
    {
        while(!this.shouldCloseRequest()){
            glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glLoadIdentity();
            glScalef(0.5f, 0.5f, 0.5f);
            this.drawObj();


            glfwPollEvents();
            glfwSwapBuffers(this.windowId);
        }

        this.destroy();
    }

    private void drawObj(){
        glBegin(GL_TRIANGLES);
            glColor3f(1.0f, 0.0f, 0.0f);
            glVertex2f(-0.5f, -0.5f);
            glColor3f(0.0f, 1.0f, 0.0f);
            glVertex2f(0.5f, -0.5f);
            glColor3f(0.0f, 0.0f, 1.0f);
            glVertex2f(0.0f, 0.5f);
        glEnd();
    }

    private void destroy(){
        glfwDestroyWindow(this.windowId);
    }

    private boolean shouldCloseRequest(){
        return glfwWindowShouldClose(this.windowId);
    }
}
