package ru.cool.lwjgl3.objects;

import ru.cool.lwjgl3.Texture;
import ru.cool.lwjgl3.buffers.VertexBufferObject;

import java.nio.FloatBuffer;

public class Block {

    private String blockTexture;
    private int posX;
    private int posY;
    private int posZ;

    public Block setTexture(String blockTexture){
        this.blockTexture = blockTexture;
        return this;
    }

    public String getBlockTexture(){
        return this.blockTexture;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosZ() {
        return posZ;
    }
}
