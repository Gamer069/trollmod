#version 330

in vec3 Position;

uniform mat4 ProjMat;
uniform vec2 OutSize;

out vec2 texCoord;

void main() {
    vec4 outPos = ProjMat * vec4(Position, 1.0);
    gl_Position = vec4(outPos.xy, 0.0, 1.0);

    // More reliable UV calculation
    texCoord = Position.xy / OutSize;
}
