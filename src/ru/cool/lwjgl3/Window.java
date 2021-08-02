package ru.cool.lwjgl3;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import ru.cool.lwjgl3.buffers.ElementBufferObject;
import ru.cool.lwjgl3.buffers.VertexArrayObject;
import ru.cool.lwjgl3.buffers.VertexBufferObject;
import ru.cool.lwjgl3.types.EnumBufferDataType;
import ru.cool.lwjgl3.util.DataManager;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.system.MemoryUtil.*;

import static  ru.cool.lwjgl3.types.EnumBufferDataType.*;

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

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

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

        FloatBuffer verticesBuffer = DataManager.storeDataInFloatBuffer(rectangle);
        IntBuffer indicesBuffer = DataManager.storeDataInIntBuffer(indices);

        //Объект шейдера
        shader = new Shader("src/ru/cool/lwjgl3/shaders/vertex.vtx", "src/ru/cool/lwjgl3/shaders/fragment.frg");
        shader.setShader(); //Установка шейдерной программы

        //Создание и присваивание буфера индексов
        ElementBufferObject ebo = new ElementBufferObject();
        ebo.putIndices(indicesBuffer);

        VertexArrayObject vao = new VertexArrayObject();

        //Создание вершинного объекта
        VertexBufferObject vbo = new VertexBufferObject();
        vbo.putDataToBuffer(verticesBuffer);
        DataManager.memoryFree(verticesBuffer);
        vbo
                .setAttributePointer(3, EnumBufferDataType.GL_FLOAT, 8 * Float.BYTES, 0)
                .setAttributePointer(3, EnumBufferDataType.GL_FLOAT, 8 * Float.BYTES, 3 * Float.BYTES)
                .setAttributePointer(2, EnumBufferDataType.GL_FLOAT, 8 * Float.BYTES, 6 * Float.BYTES);

        //Объекты текстуры
        Texture szLogo_tex = new Texture("src/ru/cool/lwjgl3/textures/logo.jpg");
        Texture cool_tex = new Texture("src/ru/cool/lwjgl3/textures/cool.jpg");

        shader.enableShader();  //Включение шейдерной программы для установки текстур и uniform-переменных

        //Установка текстур
        szLogo_tex.setTexture(shader.getShaderProgram(), "texture0", 0);
        cool_tex.setTexture(shader.getShaderProgram(), "texture1", 1);

        while(!this.closeWindow() && glfwGetKey(this.windowId, GLFW_KEY_ESCAPE) != GLFW_PRESS){
            glClearColor(0.0f,0.0f,0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            szLogo_tex.bindTexture(GL_TEXTURE0);
            cool_tex.bindTexture(GL_TEXTURE1);

            shader.enableShader();
            ebo.drawElements(indicesBuffer);
            shader.disableShader();

            glfwPollEvents();
            glfwSwapBuffers(this.windowId);
        }

        DataManager.memoryFree(indicesBuffer);

        //Отвязка текстуры
        cool_tex.unbindTexture();
        szLogo_tex.unbindTexture();

        //Отключение вершинных аттрибутов
        vbo.disableAttributePointer();

        //Отвязка буферов
        ebo.unbindElementBuffer();
        vbo.unbindBuffer();
        vao.unbindVertexArray();

        glfwDestroyWindow(this.windowId);
        glfwTerminate();
    }

    private boolean closeWindow() {
        return glfwWindowShouldClose(this.windowId);
    }


}
