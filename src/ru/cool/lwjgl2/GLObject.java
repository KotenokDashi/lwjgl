package ru.cool.lwjgl2;

import static org.lwjgl.opengl.GL40.*;

public class GLObject {
    private float x;
    private float y;
    private float z;
    private ObjectType type;

    public GLObject(ObjectType type) {
        this.type = type;
    }

    public void drawObject(float x, float y, float z){
        switch (type){
            case TRIANGLE:

        }
    }

    public enum ObjectType{
        TRIANGLE(GL_TRIANGLES), QUAD(GL_TRIANGLE_FAN);

        ObjectType(int type){}
    }
}
