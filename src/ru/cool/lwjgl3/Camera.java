package ru.cool.lwjgl3;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.cool.lwjgl3.util.DataManager;
import ru.cool.lwjgl3.util.Keyboard;
import ru.cool.lwjgl3.util.MatrixManager;

import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.glfw.GLFW.*;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class Camera {

    private FloatBuffer prspMatrixData;
    private Shader shader;
    private float posX;
    private float posY;
    private float posZ;
    private Vector3f cameraPos;
    private Vector3f cameraFront;
    private Vector3f cameraUp;

    public Camera(Shader shader, float posX, float posY, float posZ)
    {
        this.shader = shader;
        cameraPos = new Vector3f(posX, posY, posZ);
        cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
    }

    public void input(long windowId){

        Keyboard.handleKeyboardInput(windowId);
    }

    public void setPerspective(float fov, float aspect, float zNear, float zFar)
    {
        Matrix4f prspMatrix = new Matrix4f().perspective((float)Math.toRadians(fov), aspect, zNear, zFar);
        prspMatrixData = MatrixManager.matrixStorage(prspMatrix);
        glUniformMatrix4fv(glGetUniformLocation(this.shader.getShaderProgram(), "projection"), false, prspMatrixData);
    }

    public void clearMatrixData(){
        DataManager.memoryFree(prspMatrixData);
    }

    public float getPosX()
    {
        return this.posX;
    }

    public float getPosY()
    {
        return this.posY;
    }

    public float getPosZ()
    {
        return this.posZ;
    }

    public void setPosX(float posX)
    {
        this.cameraPos.add(posX, 0.0f, 0.0f);
    }

    public void setPosY(float posY)
    {
        this.cameraPos.add(0.0f, posY, 0.0f);
    }

    public void setPosZ(float posZ)
    {
        this.cameraPos.add(0.0f, 0.0f, posZ);
    }
}
