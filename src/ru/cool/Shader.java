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

    /**
     * That method compiling and attaching shaders in one shader program.
     * For use shader program, use enableShader(), disableShader().
     */

    public final void setShader(){
        int vertexShader = this.compileShader(this.vtxFile, true);
        int fragmentShader = this.compileShader(this.frgFile, false);
        int shaderProgram = glCreateProgram();

        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);

        int programStatus = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        int logLength = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
        if(programStatus == 0){
            String programLog = glGetProgramInfoLog(shaderProgram, logLength);
            System.out.println("Error in shader program:" + "\n" + programLog);
        }

        this.programId = shaderProgram;

    }

    private void enableShader(){
        glUseProgram(this.programId);
    }

    private void disableShader(){
        glUseProgram(0);
    }




    /**
     *
     * @param shaderFile - path to shader file;
     * @param type - vertex shader if type == true, fragment shader if type == false;
     * @return compiled shader
     */

    private int compileShader(String shaderFile, boolean type){
        StringBuffer shaderSrc = new StringBuffer();
        Path p = Paths.get(shaderFile);
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
        int shader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(shader, shaderSrc);
        glCompileShader(shader);
        int status = glGetShaderi(shader, GL_COMPILE_STATUS);
        int logLength = glGetShaderi(shader, GL_INFO_LOG_LENGTH);
        if(status == 0){
            String shaderLog = glGetShaderInfoLog(shader, logLength);
            System.out.println("Error in" + (type ? "vertex" : "fragment") + "shader: " + "\n" + shaderLog);
            System.exit(-1);
        }
        return shader;
    }



}
