package ru.cool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL40.*;

public class Shader {

    private int programId;
    private String frgFile;
    private String vtxFile;

    public Shader(String frgFile, String vtxFile) {
        this.frgFile = frgFile;
        this.vtxFile = vtxFile;
    }

    public void setShader(){

    }

    private int compileShader(int shader, String shaderFile){
        StringBuffer shaderSrc = new StringBuffer();
        Path p = Paths.get("src/ru/cool/shaders/" + shaderFile);
        try(FileChannel channel = (FileChannel) Files.newByteChannel(p)){
            ByteBuffer buff = ByteBuffer.allocate((int)channel.size());
            channel.read(buff);
            buff.flip();
            byte b = 0;
            for(int i = 0; i < buff.capacity(); i++){
                shaderSrc.append(shaderSrc);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
        shader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(shader, shaderSrc);
        glCompileShader(shader);
        int status = glGetShaderi(shader, GL_COMPILE_STATUS);
        if(status == 0){

        }
        return shader;
    }

}
