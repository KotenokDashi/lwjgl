package ru.cool;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40C.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.stb.STBImage.*;

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
        return (FloatBuffer) buffer.flip();
    }

    private IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data);
        return (IntBuffer) buffer.flip();
    }

    private void update(){

        float[] rectangle = {
                //вершины               цвет                текстура
                -0.5f, -0.5f, 0.0f,     1.0f, 0.0f, 0.0f,   0.0f, 0.0f,
                -0.5f, 0.5f, 0.0f,      0.0f, 1.0f, 0.0f,   0.0f, 1.0f,
                0.5f, 0.5f, 0.0f,       0.0f, 0.0f, 1.0f,   1.0f, 1.0f,
                0.5f, -0.5f, 0.0f,      1.0f, 1.0f, 1.0f,   1.0f, 0.0f
        };

        int[] indices = {
                0, 1, 2, 0, 3, 2
        };

        Shader shader = new Shader("src/ru/cool/shaders/vertex.vtx", "src/ru/cool/shaders/fragment.frg");
        shader.setShader();

        FloatBuffer verticesBuffer = this.storeDataInFloatBuffer(rectangle);
        IntBuffer indicesBuffer = this.storeDataInIntBuffer(indices);

        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        MemoryUtil.memFree(verticesBuffer);

        glVertexAttribPointer(0,3, GL_FLOAT, false, 8 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * Float.BYTES, 6 * Float.BYTES);
        glEnableVertexAttribArray(2);

        Texture sz = new Texture("src/ru/cool/textures/logo.jpg");
        sz.setTexture();

        while(!this.closeWindow() && glfwGetKey(this.windowId, GLFW_KEY_ESCAPE) != GLFW_PRESS){
            glClearColor(0.0f,0.0f,0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            sz.bindTexture();
            glBindVertexArray(vao);

            shader.enableShader();
            glDrawElements(GL_TRIANGLES, indicesBuffer);
            shader.disableShader();

            glfwPollEvents();
            glfwSwapBuffers(this.windowId);
        }

        sz.unbindTexture();

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        glfwDestroyWindow(this.windowId);
        glfwTerminate();

    }

    private boolean closeWindow() {
        return glfwWindowShouldClose(this.windowId);
    }

}
