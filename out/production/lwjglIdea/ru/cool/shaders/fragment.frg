#version 330 core

in vec3 frg_color;
in vec2 tex_coord;
out vec4 outColor;

uniform sampler2D tex_color;

void main(){
    outColor = texture(tex_color, tex_coord) * vec4(frg_color, 1.0f);

}