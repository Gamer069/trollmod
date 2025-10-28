#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D VignetteTexture;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 sceneColor = texture(DiffuseSampler, texCoord);
    vec4 vignetteMask = texture(VignetteTexture, texCoord);

    // Multiply scene RGB by vignette brightness
    sceneColor.rgb *= vignetteMask.rgb;

    fragColor = sceneColor;
}