package me.illia.trollmod.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseFireBlock.class)
public class AbstractFireBlockMixin {
	@Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
	private void collide(BlockState state, Level world, BlockPos pos, Entity entity, CallbackInfo ci) {
		if (entity instanceof Snowball snowball) {
			snowball.discard();

			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

			ci.cancel();
		}
	}
}
