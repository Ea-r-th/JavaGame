#version 400 core

in vec2 pass_textureCoords;
//in vec3 color;// out from vertex

out vec4 out_color; //RGBA color per pixel/fragment

uniform sampler2D textureSampler;
void main() {

    //out_color = vec4(color,1.0); //Just passes the linearly interpolated color values from vertex shader
    out_color = texture(textureSampler, pass_textureCoords);
}
