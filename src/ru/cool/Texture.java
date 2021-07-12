package ru.cool;

import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL40C.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private String texturePath;
    private int texture;

    public Texture(String texturePath){
        this.texturePath = texturePath;
    }

    public void setTexture(){
        this.texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, this.texture);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer w, h, c;
        w = MemoryUtil.memAllocInt(1);
        h = MemoryUtil.memAllocInt(1);
        c = MemoryUtil.memAllocInt(1);

        ByteBuffer imageData = stbi_load(this.texturePath, w, h, c, 4);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w.get(), h.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, imageData);
        glGenerateMipmap(GL_TEXTURE_2D);

        if(imageData != null){
            stbi_image_free(imageData);
        }
        MemoryUtil.memFree(w);
        MemoryUtil.memFree(h);
        MemoryUtil.memFree(c);
    }

    public void bindTexture(){
        glBindTexture(GL_TEXTURE_2D, this.texture);
    }

    public void unbindTexture(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
