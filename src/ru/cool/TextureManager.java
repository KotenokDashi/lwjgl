package ru.cool;

import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL40C.*;
import static org.lwjgl.stb.STBImage.*;

public class TextureManager {
    private void bindTexture(String texturePath, int width, int height, IntBuffer channels){
        IntBuffer
                bufferedWidth,
                bufferedHeight;
        bufferedWidth = MemoryUtil.memAllocInt(1);
        bufferedHeight = MemoryUtil.memAllocInt(1);
        bufferedWidth.put(width);
        bufferedHeight.put(height);

        ByteBuffer bufferedImage = stbi_load(texturePath, bufferedWidth, bufferedHeight, channels, 4);
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, bufferedImage);
        glGenerateMipmap(texture);
        stbi_image_free(bufferedImage);


    }
}
