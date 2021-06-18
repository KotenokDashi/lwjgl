package ru.cool;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40C.*;
import static org.lwjgl.system.MemoryUtil.*;

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

        if(this.windowId == NULL){
            System.out.println("Не удалось инициализировать окно!");
            System.exit(-1);
        }

        glfwMakeContextCurrent(this.windowId);

        GL.createCapabilities();

        this.update();

    }

    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data);
        return buffer;
    }

    private IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data);
        return buffer;
    }

    private void update(){

        float[] vertices = {
                -0.5f, -0.5f, 0,0f,
                0.0f, 0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };

        FloatBuffer buff = this.storeDataInFloatBuffer(vertices);

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, buff, GL_STATIC_DRAW);
        glVertexAttribPointer(0,3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        MemoryUtil.memFree(buff);

        while(!this.closeWindow() && glfwGetKey(this.windowId, GLFW_KEY_ESCAPE) != GLFW_PRESS){
            glClearColor(0f,0f,0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glDrawArrays(GL_TRIANGLES,0, vertices.length / 3);

            glfwPollEvents();
            glfwSwapBuffers(this.windowId);
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        glfwDestroyWindow(this.windowId);

    }

    private boolean closeWindow() {
        return glfwWindowShouldClose(this.windowId);
    }

}
