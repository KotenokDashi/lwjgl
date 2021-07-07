#version 330 core

in vec3 frg_color;
out vec4 outColor;

void main(){
    outColor = vec4(frg_color, 1.0f);

}