package ru.cool.lwjgl3;

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

    public Shader(String vtxFile, String frgFile) {
        this.frgFile = frgFile;
        this.vtxFile = vtxFile;
    }

    public final void setShader()
    {
        int vertexShader = this.compileShader(this.vtxFile, ShaderType.VERTEX);
        int fragmentShader = this.compileShader(this.frgFile, ShaderType.FRAGMENT);
        int shaderProgram = glCreateProgram();

        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);

        int programStatus = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        int logLength = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
        if(programStatus == 0)
        {
            String programLog = glGetProgramInfoLog(shaderProgram, logLength);
            System.out.println("Error in shader program:" + "\n" + programLog);
        }

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);

        this.programId = shaderProgram;
    }

    public final void enableShader(){
        glUseProgram(this.programId);
    }

    public final void disableShader(){
        glUseProgram(0);
    }

    private int compileShader(String shaderFile, ShaderType type)
    {
        StringBuffer shaderSrc = new StringBuffer();
        Path p = Paths.get(shaderFile);
        try(FileChannel channel = (FileChannel) Files.newByteChannel(p))
        {
            ByteBuffer buff = ByteBuffer.allocate((int)channel.size());
            channel.read(buff);
            buff.flip();
            for(int i = 0; i < buff.capacity(); i++){
                shaderSrc.append((char)buff.get());
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        int shader = glCreateShader((type == ShaderType.VERTEX ? GL_VERTEX_SHADER : GL_FRAGMENT_SHADER));
        glShaderSource(shader, shaderSrc);
        glCompileShader(shader);
        int status = glGetShaderi(shader, GL_COMPILE_STATUS);
        int logLength = glGetShaderi(shader, GL_INFO_LOG_LENGTH);
        if(status == 0)
        {
            String shaderLog = glGetShaderInfoLog(shader, logLength);
            System.out.println("Error in " + (type == ShaderType.VERTEX ? "vertex" : "fragment") + " shader: " + "\n" + shaderLog);
            System.exit(-1);
        }
        return shader;
    }

    public int getShaderProgram() {
        return programId;
    }

    enum ShaderType{
        VERTEX, FRAGMENT
    }
}
