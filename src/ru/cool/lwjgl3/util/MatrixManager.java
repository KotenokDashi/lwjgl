package ru.cool.lwjgl3.util;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import java.nio.*;

public class MatrixManager {

    public static FloatBuffer matrixStorage(Matrix4f matrix){
        FloatBuffer matrixData = MemoryUtil.memAllocFloat(16);
        return matrix.get(matrixData);
    }

}
