package ru.cool.lwjgl3.buffers;

import org.lwjgl.system.MemoryUtil;
import static org.lwjgl.opengl.GL40.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;


public class VertexArrayObject {

    private int arrayId;

    public VertexArrayObject() {
        IntBuffer vao = MemoryUtil.memAllocInt(4);
        glGenVertexArrays(vao);
        this.arrayId = vao.get();
        glBindVertexArray(this.arrayId);
    }

    public void unbindVertexArray() {
        glBindVertexArray(0);
    }
}

