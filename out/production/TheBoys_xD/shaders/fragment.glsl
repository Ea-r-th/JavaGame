#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
//in vec3 color;// out from vertex

out vec4 out_color; //RGBA color per pixel/fragment

uniform sampler2D textureSampler;
uniform vec3 lightColor;

void main() {

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot1 = dot(unitNormal, unitLightVector);
    float brightness = max(nDot1, 0.0);
    vec3 diffuse = brightness * lightColor;
    //out_color = vec4(color,1.0); //Just passes the linearly interpolated color values from vertex shader
    out_color = vec4(diffuse,1.0) * texture(textureSampler, pass_textureCoords);
}
