package ru.cool.lwjgl3.types;

import org.lwjgl.opengl.GL11;

public enum EnumBufferDataType {

    GL_UNSIGNED_INT(GL11.GL_UNSIGNED_INT), GL_FLOAT(GL11.GL_FLOAT);

    private int dataType;


    EnumBufferDataType(int type){
        this.dataType = type;
    }

    public int getType(){
        return this.dataType;
    }
}
