package me.illia.trollmod.mixin;

import me.illia.trollmod.block.GhostBlock;
import me.illia.trollmod.block.GhostBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateMixin {
	@Shadow
	public abstract Block getBlock();

	@Inject(
		method = "isFaceSturdy(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z",
		at = @At("HEAD"),
		cancellable = true
	)
	private void isSideSolidFullSquare(BlockGetter world, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		if (getBlock() instanceof GhostBlock block) {
			GhostBlockEntity gbe = (GhostBlockEntity)world.getBlockEntity(pos);
			cir.setReturnValue(gbe.getBlock().defaultBlockState().isFaceSturdy(world, pos, direction));
		}
	}
}
