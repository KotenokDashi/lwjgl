package ru.cool.lwjgl3.buffers;

import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL40.*;

public class ElementBufferObject {

    private int bufferId;
    private final int USAGE = GL_STATIC_DRAW;

    public ElementBufferObject() {
        IntBuffer ebo = MemoryUtil.memAllocInt(4);
        glGenBuffers(ebo);
        this.bufferId = ebo.get();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.bufferId);
    }

    public void drawElements(IntBuffer indicesBuffer){
        glDrawElements(GL_TRIANGLES, indicesBuffer);
    }

    public void putIndices(IntBuffer indices){
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, USAGE);
    }

    public void unbindElementBuffer(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

}
