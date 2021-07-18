package ru.cool.lwjgl3.buffers;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.Buffer;

public class BufferManager {

    /**
     * Метод хранения вещественных числовых данных в буфере
     * @param data массив данных которыми будет обёрнут массив
     * @return новый перевёрнутый FloatBuffer из которого нужно будет освободить память.
     */
    public static FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data);
        return (FloatBuffer) buffer.flip();
    }

    /**
     * Метод хранения целых числовых данных в буфере
     * @param data массив данных которыми будет обёрнут массив
     * @return новый перевёрнутый IntBuffer из которого нужно будет освободить память.
     */
    public static IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data);
        return (IntBuffer) buffer.flip();
    }

    public static void memoryFree(Buffer buffer){
         MemoryUtil.memFree(buffer);
    }
}
