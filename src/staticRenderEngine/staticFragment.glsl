#version 400 core

in vec2 pass_textureCoords;

out vec4 out_color; //RGBA color per pixel/fragment

uniform sampler2D textureSampler;

void main() {

    vec4 textureColor = texture(textureSampler, pass_textureCoords);

    if(textureColor.a < 0.5){ //Discards the transparent parts of a texture so it isn't rendered
        discard;
    }

    out_color = textureColor;

}
