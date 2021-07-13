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

    /**
     * Загружает и устанавливает текстуру
     * @param shaderProgram шейдерная программа
     * @param uniformTexture название uniform переменной текстурного юнита
     * @param textureUnitValue значение текстурного юнита
     */
    public void setTexture(int shaderProgram, String uniformTexture, int textureUnitValue){
        this.texture = glGenTextures(); //Генерация текстуры
        glBindTexture(GL_TEXTURE_2D, this.texture); //Привязка текстуры как 2D типа

        //Параметры текстуры
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);   //Повторение текстуры вышедшей за границу по оси X(S)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);   //Повторение текстуры вышедшей за границу по оси Y(T)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);   //Фильтрация при уменьшении размера учитывая мипмапу
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);  //Фильтрация текстуры при увеличении размера

        //Выделение буферов под размеры и каналы
        IntBuffer w, h, c;
        w = MemoryUtil.memAllocInt(1);
        h = MemoryUtil.memAllocInt(1);
        c = MemoryUtil.memAllocInt(1);

        stbi_set_flip_vertically_on_load(true); //Переворот оси Y

        ByteBuffer imageData = stbi_load(this.texturePath, w, h, c, 4); //Загрузка изображения

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, w.get(), h.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, imageData);
        glGenerateMipmap(GL_TEXTURE_2D);    //Генерация мипмап текстур

        if(imageData != null){
            stbi_image_free(imageData); //Освобождение памяти под изображение
        }else System.out.println("Ошибка при загрузке изображения");

        //Освобождение памяти буферов
        MemoryUtil.memFree(w);
        MemoryUtil.memFree(h);
        MemoryUtil.memFree(c);

        glUniform1i(glGetUniformLocation(shaderProgram, uniformTexture), textureUnitValue);

    }

    public void bindTexture(int textureUnit){
        glActiveTexture(textureUnit);
        glBindTexture(GL_TEXTURE_2D, this.texture);
    }

    public void unbindTexture(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
