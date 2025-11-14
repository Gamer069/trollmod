package me.illia.trollmod.mixin;

import me.illia.trollmod.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.LocalPlayer;
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
	private Options options;

	@Shadow
	private static float calculateImpulse(boolean positive, boolean negative) {
		return 0;
	}

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void tick(boolean slowDown, float slowDownFactor, CallbackInfo ci) {
		Minecraft client = Minecraft.getInstance();
		LocalPlayer player = client.player;
		if (player.hasEffect(ModEffects.INVERT_CONTROLS.value())) {
			this.up = this.options.keyDown.isDown();
			this.down = this.options.keyUp.isDown();
			this.left = this.options.keyRight.isDown();
			this.right = this.options.keyLeft.isDown();
			this.forwardImpulse = calculateImpulse(this.up, this.down);
			this.leftImpulse = calculateImpulse(this.left, this.right);
			this.jumping = this.options.keyJump.isDown();
			this.shiftKeyDown = this.options.keyShift.isDown();
			if (slowDown) {
				this.leftImpulse *= slowDownFactor;
				this.forwardImpulse *= slowDownFactor;
			}
			ci.cancel();
		}
	}
}
