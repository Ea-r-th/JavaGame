#version 330

layout(location = 0) in vec3 in_position;
layout(location = 1) in vec4 in_normal;

out vec3 pass_color;

uniform vec3 lightPosition;
uniform vec3 lightColor;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

vec3 calculateLighting(){
    vec3 normal = in_normal.xyz * 2.0 - 1.0;//just because of the way normals were stored in VAO (0 to 1 instead of -1 to 1)
    float brightness = max(dot(-lightPosition, normal), 0.0);
    return (lightColor) + (brightness * lightColor);
}

void main(void){

    gl_Position = projectionMatrix * viewMatrix * vec4(in_position, 1.0);

    vec3 lighting = calculateLighting();
    pass_color = vec3(1.0,0,0.01) * lighting;

}