#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
//in vec3 color;// out from vertex

out vec4 out_color; //RGBA color per pixel/fragment

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;

void main() {

    float ambientLight = 0.2; //Minimum amount of light there can be in the scene

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot1 = dot(unitNormal, unitLightVector);
    float brightness = max(nDot1, ambientLight);
    vec3 diffuse = brightness * lightColor;
    //out_color = vec4(color,1.0); //Just passes the linearly interpolated color values from vertex shader

    vec3 unitVectorToCamera = normalize(toCameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

    float specularCompare = dot(reflectedLightDirection, unitLightVector);
    specularCompare = max(specularCompare, 0.0);
    float dampedFactor = pow(specularCompare, shineDamper);

    vec3 specularLighting = dampedFactor * lightColor;

    out_color = vec4(specularLighting, 1.0) + vec4(diffuse,1.0) * texture(textureSampler, pass_textureCoords);
}
