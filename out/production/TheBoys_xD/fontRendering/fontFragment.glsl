#version 400 core

in vec2 pass_textureCoords;

out vec4 out_color;

uniform vec3 color;
uniform sampler2D fontAtlas;

const float width = 0.3;
const float edge = 0.2;

const float borderWidth = 0.4;
const float borderEdge = 0.25;

const vec3 outlineColor = vec3(0,0,0);

void main(void) {

    float distance = 1.0 - texture(fontAtlas, pass_textureCoords).a; //Samples the fontAtlas and uses the textureCoords alpha to determine the distance from the center line.
    float alpha = 1.0 - smoothstep(width, width + edge, distance); //Creates a gradient between the two edges of the start and end of the text character gradient, returning the alpha there.

    float borderDistance = 1.0 - texture(fontAtlas, pass_textureCoords).a;
    float outlineAlpha = 1.0 - smoothstep(borderWidth, borderWidth + borderEdge, borderDistance);

    float totalAlpha = alpha + (1.0 - alpha) * outlineAlpha;
    vec3 totalColor = mix(outlineColor, color, alpha / totalAlpha);

    out_color = vec4(totalColor, totalAlpha);
}

