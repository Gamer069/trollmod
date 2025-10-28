package me.illia.trollmod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.illia.trollmod.Trollmod;
import net.minecraft.client.gl.JsonEffectShaderProgram;
import net.minecraft.client.gl.ShaderStage;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(JsonEffectShaderProgram.class)
@Debug(export = true)
public class JsonEffectGlShaderMixin {
	/**
	 * Fix identifier creation to allow different namespaces in constructor
	 */
	@WrapOperation(
		at = @At(
			value = "NEW",
			target = "(Ljava/lang/String;)Lnet/minecraft/util/Identifier;",
			ordinal = 0
		),
		method = "<init>"
	)
	private Identifier initConstructProgramIdentifier(String id, Operation<Identifier> original) {
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

		return new Identifier(parts[0], "shaders/program/" + parts[1]);
	}

	/**
	 * Fix identifier creation to allow different namespaces in loadEffect
	 */
	@WrapOperation(
		at = @At(
			value = "NEW",
			target = "net/minecraft/util/Identifier",
			ordinal = 0
		),
		method = "loadEffect"
	)
	private static Identifier init(String arg, Operation<Identifier> original, ResourceManager resourceManager, ShaderStage.Type type, String id) {
		Trollmod.LOGGER.info("string s, arg {} id {}", arg, id);
		if (!arg.contains(":")) {
			return original.call(arg);
		}
		Identifier split = new Identifier(id);
		Trollmod.LOGGER.info("id: {}",new Identifier(split.getNamespace(), "shaders/program/" + split.getPath() + type.getFileExtension()));
		return new Identifier(split.getNamespace(), "shaders/program/" + split.getPath() + type.getFileExtension());
	}
}
