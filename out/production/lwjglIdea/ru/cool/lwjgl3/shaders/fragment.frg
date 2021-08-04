#version 330 core

in vec3 frg_color;
in vec2 tex_coord;
out vec4 outColor;

uniform sampler2D texture0;
uniform sampler2D texture1;

void main(){
//    outColor = mix(texture(texture0, tex_coord), texture(texture1, tex_coord), 0.5f);
    outColor = texture(texture0, tex_coord);
}