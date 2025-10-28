package me.illia.trollmod.mixin;

import me.illia.trollmod.effect.ModEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {
	@Shadow
	@Final
	private GameOptions settings;

	@Shadow
	private static float getMovementMultiplier(boolean positive, boolean negative) {
		return 0;
	}

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void tick(boolean slowDown, float slowDownFactor, CallbackInfo ci) {
		MinecraftClient client = MinecraftClient.getInstance();
		ClientPlayerEntity player = client.player;
		if (player.hasStatusEffect(ModEffects.INVERT_CONTROLS.value())) {
			this.pressingForward = this.settings.backKey.isPressed();
			this.pressingBack = this.settings.forwardKey.isPressed();
			this.pressingLeft = this.settings.rightKey.isPressed();
			this.pressingRight = this.settings.leftKey.isPressed();
			this.movementForward = getMovementMultiplier(this.pressingForward, this.pressingBack);
			this.movementSideways = getMovementMultiplier(this.pressingLeft, this.pressingRight);
			this.jumping = this.settings.jumpKey.isPressed();
			this.sneaking = this.settings.sneakKey.isPressed();
			if (slowDown) {
				this.movementSideways *= slowDownFactor;
				this.movementForward *= slowDownFactor;
			}
			ci.cancel();
		}
	}
}
