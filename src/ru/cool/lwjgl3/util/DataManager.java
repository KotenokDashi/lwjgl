package ru.cool.lwjgl3.util;

import org.lwjgl.system.MemoryUtil;

import java.nio.*;

public class DataManager {

    public static FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(4 * data.length);
        buffer.put(data);
        return (FloatBuffer) buffer.flip();
    }

    public static IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = MemoryUtil.memAllocInt(4 * data.length);
        buffer.put(data);
        return (IntBuffer) buffer.flip();
    }

    public static DoubleBuffer storeDataInDoubleBuffer(double[] data){
        DoubleBuffer buffer = MemoryUtil.memAllocDouble(8 * data.length);
        buffer.put(data);
        return (DoubleBuffer) buffer.flip();
    }

    public static LongBuffer storeDataInLongBuffer(long[] data){
        LongBuffer buffer = MemoryUtil.memAllocLong(8 * data.length);
        buffer.put(data);
        return (LongBuffer) buffer.flip();
    }

    public static void memoryFree(Buffer ...buffers){
        for(Buffer buf : buffers){
            MemoryUtil.memFree(buf);
        }
    }
}
