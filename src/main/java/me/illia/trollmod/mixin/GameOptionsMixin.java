package me.illia.trollmod.mixin;

import me.illia.trollmod.Trollmod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Perspective;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
	@Inject(method = "setPerspective", at = @At("HEAD"), cancellable = true)
	private void setPerspective(Perspective perspective, CallbackInfo ci) {
		if (Trollmod.LOCK) {
			MinecraftClient.getInstance().player.sendMessage(Text.translatable("msg.trollmod.locked").styled(style -> style.withColor(Formatting.DARK_RED)));
			ci.cancel();
		}
	}
}
