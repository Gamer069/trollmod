package me.illia.trollmod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.shaders.Program;
import me.illia.trollmod.Trollmod;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EffectInstance.class)
@Debug(export = true)
public class JsonEffectGlShaderMixin {
	/**
	 * Fix identifier creation to allow different namespaces in constructor
	 */
	@WrapOperation(
		at = @At(
			value = "NEW",
			target = "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;",
			ordinal = 0
		),
		method = "<init>"
	)
	private ResourceLocation initConstructProgramIdentifier(String id, Operation<ResourceLocation> original) {
		Trollmod.LOGGER.info("object o id {}", id);
		// id is already "shaders/program/" + name + ".json"
		if (!id.contains(":")) {
			return original.call(id);
		}

		// id is shaders/program/trollmod:vignette.json...
		String fname = id.replaceFirst("^shaders/program/", "");

		// fname is trollmod:vignette.json
		String[] parts = fname.split(":");

		if (parts.length != 2) {
			Trollmod.LOGGER.error("invalid path, ensure you have only one :");
			throw new RuntimeException();
		}

		// parts[0] is trollmod
		// parts[1] is vignette.json

		return new ResourceLocation(parts[0], "shaders/program/" + parts[1]);
	}

	/**
	 * Fix identifier creation to allow different namespaces in loadEffect
	 */
	@WrapOperation(
		at = @At(
			value = "NEW",
			target = "net/minecraft/resources/ResourceLocation",
			ordinal = 0
		),
		method = "getOrCreate"
	)
	private static ResourceLocation init(String arg, Operation<ResourceLocation> original, ResourceManager resourceManager, Program.Type type, String id) {
		Trollmod.LOGGER.info("string s, arg {} id {}", arg, id);
		if (!arg.contains(":")) {
			return original.call(arg);
		}
		ResourceLocation split = new ResourceLocation(id);
		Trollmod.LOGGER.info("id: {}",new ResourceLocation(split.getNamespace(), "shaders/program/" + split.getPath() + type.getExtension()));
		return new ResourceLocation(split.getNamespace(), "shaders/program/" + split.getPath() + type.getExtension());
	}
}
