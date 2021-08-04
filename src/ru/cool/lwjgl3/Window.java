package ru.cool.lwjgl3;

import java.lang.Math;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Vector;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import ru.cool.lwjgl3.objects.Block;
import ru.cool.lwjgl3.renderer.BlockRenderer;
import ru.cool.lwjgl3.util.DataManager;
import ru.cool.lwjgl3.util.Keyboard;
import ru.cool.lwjgl3.util.MatrixManager;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    private final int WIDTH;
    private final int HEIGHT;
    private long windowId;
    private float deltaTime = 0.0f;
    private float lastFrame = 0.0f;
    private GLFWVidMode vidMode;
    private Shader shader;
    private IntBuffer bufferedWidth;
    private IntBuffer bufferedHeight;
    private Vector3f cameraPos;
    private Vector3f cameraFront;
    private Vector3f cameraUp;

    public Window(int WIDTH, int HEIGHT, int windowId)
    {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.windowId = windowId;
    }

    public void createWindow()
    {
        glfwInit();
//        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
//        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
//        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_SAMPLES, 4);    //Устанавливает количество сэмплов во фреймбуфер вроде
        bufferedWidth = BufferUtils.createIntBuffer(1);
        bufferedHeight = BufferUtils.createIntBuffer(1);
        vidMode = glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        this.windowId = glfwCreateWindow(this.WIDTH, this.HEIGHT, "Test Window", 0, 0);
        glfwGetWindowSize(this.windowId, bufferedWidth, bufferedHeight);
        glfwSetWindowPos(this.windowId,
                (vidMode.width() - bufferedWidth.get(0)) / 2,
                (vidMode.height() - bufferedHeight.get(0)) / 2);
        if (this.windowId == NULL)
        {
            System.out.println("Не удалось инициализировать окно!");
            System.exit(-1);
        }
        glfwMakeContextCurrent(this.windowId);
        GL.createCapabilities();
        glfwSetFramebufferSizeCallback(this.windowId, (window, width, height) -> {
            glViewport(0,0, width, height);
        });
        this.update();
    }

    private void update()
    {
        shader = new Shader("src/ru/cool/lwjgl3/shaders/vertex.vtx", "src/ru/cool/lwjgl3/shaders/fragment.frg");
        shader.setShader();
        Block coolBlock = new Block().setTexture("src/ru/cool/lwjgl3/textures/felix.jpg");
        BlockRenderer renderer = new BlockRenderer(coolBlock, shader);
        shader.enableShader();  //Включение шейдерной программы для установки текстур и uniform-переменных
        renderer.preRender();
        //TODO: инкапсулировать объявление матриц
        cameraPos = new Vector3f(0.0f, 0.0f, 3.0f);
        cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        FloatBuffer viewMatrixData;
        Camera camera = new Camera(shader, 0.0f, 0.0f, -3.0f);
        camera.setPerspective(45, this.bufferedWidth.get() / this.bufferedHeight.get(), 0.1f, 100.0f);
        int viewMatrixUniform = glGetUniformLocation(this.shader.getShaderProgram(), "view");
        while(!this.closeWindow() && glfwGetKey(this.windowId, GLFW_KEY_ESCAPE) != GLFW_PRESS)
        {
            float currentFrame = (float) glfwGetTime();
            deltaTime = currentFrame - lastFrame;
            lastFrame = currentFrame;
            this.setDisplayColor(0, 0f, 0f);
            this.enableDepthTest();
            glEnable(GL_MULTISAMPLE);
            camera.input(this.windowId);
            shader.enableShader();
            renderer.render();
            renderer.render().translate(1.0f, 0.0f, -1.0f).rotate(40, 0.5f, 1.0f, 0.0f);
            viewMatrixData = MatrixManager.matrixStorage(new Matrix4f().lookAt(
                    cameraPos,
                    cameraFront,
                    cameraUp
            ));
            glUniformMatrix4fv(viewMatrixUniform, false, viewMatrixData);
            shader.disableShader();
            this.updateFrame();
        }
        renderer.postRender();
        glfwDestroyWindow(this.windowId);
        glfwTerminate();
    }

    private void inputHandling(){
        float cameraSpeed = 2.5f * deltaTime;

        if(glfwGetKey(this.windowId, GLFW_KEY_W) != GLFW_RELEASE)
        {
            cameraPos.add(cameraFront.mul(cameraSpeed));
        }
        if(glfwGetKey(this.windowId, GLFW_KEY_S) == GLFW_PRESS)
        {
            cameraPos.sub(cameraFront.mul(cameraSpeed));
        }
        if(glfwGetKey(this.windowId, GLFW_KEY_A) == GLFW_PRESS)
        {
            cameraPos.sub((cameraFront.cross(cameraUp)).normalize().mul(cameraSpeed));
        }
        if(glfwGetKey(this.windowId, GLFW_KEY_D) == GLFW_PRESS)
        {
            cameraPos.add(cameraFront.cross(cameraUp).normalize().mul(cameraSpeed));
        }
        Lwjgl3.printVector(cameraPos);
        Lwjgl3.printVector(cameraFront);
    }

    private void updateFrame()
    {
        glfwPollEvents();
        glfwSwapBuffers(this.windowId);
    }

    private void setDisplayColor(float r, float g, float b)
    {
        glClearColor(r,g,b, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    private void enableDepthTest(){
        glEnable(GL_DEPTH_TEST);
        glClear(GL_DEPTH_BUFFER_BIT);
    }

    private boolean closeWindow() {
        return glfwWindowShouldClose(this.windowId);
    }

    private float[] random(int i)
    {
        float[] rand = new float[i];
        for(int b = 0; b < rand.length; b++){
            rand[b] = -0.5f + (float)Math.random() * 1.5f;
        }
        return rand;
    }
}
