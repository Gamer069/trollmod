package me.illia.trollmod.mixin;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFireBlock.class)
public class AbstractFireBlockMixin {
	@Inject(method = "onEntityCollision", at = @At("HEAD"), cancellable = true)
	private void collide(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
		if (entity instanceof SnowballEntity snowball) {
			snowball.discard();

			world.setBlockState(pos, Blocks.AIR.getDefaultState());

			ci.cancel();
		}
	}
}
