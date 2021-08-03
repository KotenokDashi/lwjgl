package ru.cool.lwjgl3.buffers;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL40.*;

public class BufferObject {

    private BufferType type;
    private int bufferId;

    public BufferObject(BufferType type) {
        this.type = type;
    }

    public void allocGLBufferFloat(FloatBuffer data){
        this.bufferId = glGenBuffers();
        glBindBuffer(this.getBufferType(), this.bufferId);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
    }

    private int getBufferType(){
        return this.type == BufferType.VBO ? GL_ARRAY_BUFFER :
                this.type == BufferType.EBO ? GL_ELEMENT_ARRAY_BUFFER : 0;
    }

    public enum BufferType{
        VBO, EBO;
    }
}
