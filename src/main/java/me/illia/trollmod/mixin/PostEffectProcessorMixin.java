package me.illia.trollmod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.illia.trollmod.Trollmod;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PostChain.class)
public class PostEffectProcessorMixin {
	@WrapOperation(
		at = @At(
			value = "NEW",
			target = "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;",
			ordinal = 0
		),
		method = "parsePassNode"
	)
	private ResourceLocation initConstructTextureIdentifier(String id, Operation<ResourceLocation> original) {
		// id is textures/effect/trollmod:vignette.png
		String fname = id.replaceFirst("^textures/effect/", "");

		// fname is trollmod:vignette.png

		String[] parts = fname.split(":");

		// parts is ["trollmod", "vignette.png"]

		if (parts.length != 2) {
			Trollmod.LOGGER.error("invalid path, ensure you have only one : (texture)");
			throw new RuntimeException();
		}

		return new ResourceLocation(parts[0], "textures/effect/" + parts[1]);
	}
}
