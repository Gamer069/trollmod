#version 330

uniform sampler2D DiffuseSampler;   // main framebuffer
uniform sampler2D VignetteTexture;  // your PNG vignette texture
uniform vec2 InSize;                // framebuffer size

out vec4 fragColor;

void main() {
    // convert fragment coordinate to normalized UV
    vec2 uv = gl_FragCoord.xy / InSize;

    // sample the scene and vignette textures
    vec4 sceneColor = texture(DiffuseSampler, uv);
    vec4 vignetteMask = texture(VignetteTexture, uv);

    // assume vignette texture is black where dark, white where visible
    // multiply RGB by vignette's brightness
    sceneColor.rgb *= vignetteMask.rgb;

    fragColor = sceneColor;
}