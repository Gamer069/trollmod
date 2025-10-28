package me.illia.trollmod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.illia.trollmod.Trollmod;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PostEffectProcessor.class)
public class PostEffectProcessorMixin {
	@WrapOperation(
		at = @At(
			value = "NEW",
			target = "(Ljava/lang/String;)Lnet/minecraft/util/Identifier;",
			ordinal = 0
		),
		method = "parsePass"
	)
	private Identifier initConstructTextureIdentifier(String id, Operation<Identifier> original) {
		// id is textures/effect/trollmod:vignette.png
		String fname = id.replaceFirst("^textures/effect/", "");

		// fname is trollmod:vignette.png

		String[] parts = fname.split(":");

		// parts is ["trollmod", "vignette.png"]

		if (parts.length != 2) {
			Trollmod.LOGGER.error("invalid path, ensure you have only one : (texture)");
			throw new RuntimeException();
		}

		return new Identifier(parts[0], "textures/effect/" + parts[1]);
	}
}
