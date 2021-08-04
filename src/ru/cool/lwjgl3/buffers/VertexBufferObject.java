package ru.cool.lwjgl3.buffers;

import org.lwjgl.opengl.ARBVertexAttribBinding;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;
import ru.cool.lwjgl3.types.EnumBufferDataType;

import java.nio.*;

import static org.lwjgl.opengl.GL40.*;

public class VertexBufferObject {

    private final int USAGE = GL_STATIC_DRAW;
    private int bufferId;
    private int indicesAmount;

    public VertexBufferObject(){
        IntBuffer vbo = MemoryUtil.memAllocInt(4);
        glGenBuffers(vbo);
        this.bufferId = vbo.get();
        glBindBuffer(GL_ARRAY_BUFFER, this.bufferId);
    }

    public VertexBufferObject setAttributePointer(int size, EnumBufferDataType dataType, int stride, int offset){
        glVertexAttribPointer(this.indicesAmount, size, dataType.getType(), false, stride, offset);
        glEnableVertexAttribArray(this.indicesAmount);
        this.indicesAmount++;
        return this;
    }

    public void disableAttributePointer(){
        for(int i = 0; i < this.indicesAmount; i++) {
            glDisableVertexAttribArray(i);
        }
    }

    public void putDataToBuffer(FloatBuffer data){
        glBufferData(GL_ARRAY_BUFFER, data, USAGE);
    }

    public void putDataToBuffer(IntBuffer data){
        glBufferData(GL_ARRAY_BUFFER, data, USAGE);
    }

    public void putDataToBuffer(ByteBuffer data){
        glBufferData(GL_ARRAY_BUFFER, data, USAGE);
    }

    public void putDataToBuffer(DoubleBuffer data){
        glBufferData(GL_ARRAY_BUFFER, data, USAGE);
    }

    public final void unbindBuffer(){
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

}
