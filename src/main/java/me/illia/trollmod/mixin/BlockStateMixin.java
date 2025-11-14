package me.illia.trollmod.mixin;

import me.illia.trollmod.block.GhostBlock;
import me.illia.trollmod.block.GhostBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class BlockStateMixin {
	@Shadow
	public abstract Block getBlock();

	@Inject(
		method = "isSideSolidFullSquare",
		at = @At("HEAD"),
		cancellable = true
	)
	private void isSideSolidFullSquare(BlockView world, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		if (getBlock() instanceof GhostBlock block) {
			GhostBlockEntity gbe = (GhostBlockEntity)world.getBlockEntity(pos);
			cir.setReturnValue(gbe.getBlock().getDefaultState().isSideSolidFullSquare(world, pos, direction));
		}
	}
}
