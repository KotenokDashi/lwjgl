package ru.cool.lwjgl3;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import ru.cool.lwjgl3.buffers.BufferManager;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
//import static org.lwjgl.opengl.GL40C.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    private final int WIDTH;
    private final int HEIGHT;
    private long windowId;
    private GLFWVidMode vidMode;
    private Shader shader;

    public Window(int WIDTH, int HEIGHT, int windowId) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.windowId = windowId;
    }

    public void createWindow() {
        glfwInit();

//        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
//        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
//        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        vidMode = glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        this.windowId = glfwCreateWindow(this.WIDTH, this.HEIGHT, "Test Window", 0, 0);

        glfwSetWindowPos(this.windowId, (vidMode.width() - this.WIDTH) / 2, (vidMode.height() - this.HEIGHT) / 2);

        if (this.windowId == NULL) {
            System.out.println("Не удалось инициализировать окно!");
            System.exit(-1);
        }

        glfwMakeContextCurrent(this.windowId);
        GL.createCapabilities();

        this.update();

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

        //Объект шейдера
        shader = new Shader("src/ru/cool/shaders/vertex.vtx", "src/ru/cool/shaders/fragment.frg");
        shader.setShader(); //Установка шейдерной программы

        FloatBuffer verticesBuffer = BufferManager.storeDataInFloatBuffer(rectangle);
        IntBuffer indicesBuffer = BufferManager.storeDataInIntBuffer(indices);

        //Создание и присваивание буфера индексов
        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        //Создание и присваивание массива вершинных объектов
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        //Создание вершинного объекта
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        //Освобождение памяти вершинного буфера (есть ли смысл?)
        BufferManager.memoryFree(verticesBuffer);

        //Вершинный аттрибут под вершины фигуры
        glVertexAttribPointer(0,3, GL_FLOAT, false, 8 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        //Вершинный аттрибут под цвет фигуры
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        //Вершинный аттрибут под координаты текстуры
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * Float.BYTES, 6 * Float.BYTES);
        glEnableVertexAttribArray(2);

        //Объекты текстуры
        Texture szLogo_tex = new Texture("src/ru/cool/textures/logo.jpg");
        Texture cool_tex = new Texture("src/ru/cool/textures/cool.jpg");

        shader.enableShader();  //Включение шейдерной программы для установки текстур и uniform-переменных

        //Установка текстур
        szLogo_tex.setTexture(shader.getShaderProgram(), "texture0", 0);
        cool_tex.setTexture(shader.getShaderProgram(), "texture1", 1);

        while(!this.closeWindow() && glfwGetKey(this.windowId, GLFW_KEY_ESCAPE) != GLFW_PRESS){
            glClearColor(0.0f,0.0f,0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            szLogo_tex.bindTexture(GL_TEXTURE0);
            cool_tex.bindTexture(GL_TEXTURE1);

            glBindVertexArray(vao);

            shader.enableShader();
            glDrawElements(GL_TRIANGLES, indicesBuffer);
            shader.disableShader();

            glfwPollEvents();
            glfwSwapBuffers(this.windowId);
        }

        //Отвязка текстуры
        cool_tex.unbindTexture();
        szLogo_tex.unbindTexture();

        //Отключение вершинных аттрибутов
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);

        //Отвязка буферов
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
