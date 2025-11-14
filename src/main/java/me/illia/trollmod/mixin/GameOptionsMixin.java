package me.illia.trollmod.mixin;

import me.illia.trollmod.Trollmod;
import net.minecraft.ChatFormatting;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public class GameOptionsMixin {
	@Inject(method = "setCameraType", at = @At("HEAD"), cancellable = true)
	private void setPerspective(CameraType perspective, CallbackInfo ci) {
		if (Trollmod.LOCK) {
			Minecraft.getInstance().player.sendSystemMessage(Component.translatable("msg.trollmod.locked").withStyle(style -> style.withColor(ChatFormatting.DARK_RED)));
			ci.cancel();
		}
	}
}
