package ru.cool.lwjgl3.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import ru.cool.lwjgl3.Shader;
import ru.cool.lwjgl3.Texture;
import ru.cool.lwjgl3.buffers.ElementBufferObject;
import ru.cool.lwjgl3.buffers.VertexArrayObject;
import ru.cool.lwjgl3.buffers.VertexBufferObject;
import ru.cool.lwjgl3.objects.Block;
import ru.cool.lwjgl3.types.EnumBufferDataType;
import ru.cool.lwjgl3.util.DataManager;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL40.*;

public class BlockRenderer {

    private ElementBufferObject ebo;
    private VertexBufferObject vbo;
    private VertexArrayObject vao;
    private IntBuffer indices;
    private Texture blockTexture;
    private Block renderedBlock;
    private Matrix4f matrix;
    private Shader shader;
    private FloatBuffer matrixData;
    private int transformLocation;


    public BlockRenderer(Block block, Shader shader)
    {
        this.renderedBlock = block;
        this.shader = shader;
        this.matrixData = BufferUtils.createFloatBuffer(16);
        matrix = new Matrix4f();
    }

    public void preRender()
    {
        float[] blockVertices = {
                //вершины               цвет                текстура
                -0.5f, -0.5f, 0.0f,     1.0f, 0.0f, 0.0f,   0.0f, 0.0f,
                -0.5f, 0.5f, 0.0f,      0.0f, 1.0f, 0.0f,   0.0f, 1.0f,
                0.5f, 0.5f, 0.0f,       0.0f, 0.0f, 1.0f,   1.0f, 1.0f,
                0.5f, -0.5f, 0.0f,      1.0f, 1.0f, 1.0f,   1.0f, 0.0f
        };
        int[] indices = {
                0, 1, 2, 0, 3, 2
        };
        this.indices = DataManager.storeDataInIntBuffer(indices);
        ebo = new ElementBufferObject();
        ebo.putIndices(DataManager.storeDataInIntBuffer(indices));
        vao = new VertexArrayObject();
        vbo = new VertexBufferObject();
        vbo.putDataToBuffer(DataManager.storeDataInFloatBuffer(blockVertices));
        vbo
                .setAttributePointer(3, EnumBufferDataType.GL_FLOAT, 8 * Float.BYTES, 0)
                .setAttributePointer(3, EnumBufferDataType.GL_FLOAT, 8 * Float.BYTES, 3 * Float.BYTES)
                .setAttributePointer(2, EnumBufferDataType.GL_FLOAT, 8 * Float.BYTES, 6 * Float.BYTES);
        this.blockTexture = new Texture(this.renderedBlock.getBlockTexture());
        this.blockTexture.setTexture();
    }

    public Matrix4f getMatrix()
    {
        return matrix;
    }

    public BlockRenderer render()
    {
        transformLocation = glGetUniformLocation(this.shader.getShaderProgram(), "model");
        blockTexture.bindTexture();
        ebo.drawElements(indices);
        this.matrix.identity();
        this.matrix.get(this.matrixData);
        glUniformMatrix4fv(this.transformLocation, false, this.matrixData);
        return this;
    }

    public void postRender()
    {
        blockTexture.unbindTexture();
        vbo.disableAttributePointer();
        ebo.unbindElementBuffer();
        vbo.unbindBuffer();
        vao.unbindVertexArray();
    }

    public BlockRenderer translate(float x, float y, float z)
    {
        this.matrix.translate(x, y, z);
        this.matrix.get(this.matrixData);
        glUniformMatrix4fv(this.transformLocation, false, this.matrixData);
        return this;
    }

    public BlockRenderer scale(float x, float y, float z)
    {
        this.matrix.scale(x, y, z);
        this.matrix.get(this.matrixData);
        glUniformMatrix4fv(this.transformLocation, false, this.matrixData);
        return this;
    }

    public BlockRenderer rotate(float angle, float x, float y, float z)
    {
        this.matrix.rotate((float)Math.toRadians(angle), x, y, z);
        this.matrix.get(this.matrixData);
        glUniformMatrix4fv(this.transformLocation, false, this.matrixData);
        return this;
    }

}
