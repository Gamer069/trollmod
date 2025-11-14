package me.illia.trollmod.block;

import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class GhostBlock extends BaseEntityBlock {
	public static final BooleanProperty HAS_BLOCK = BooleanProperty.create("has_block");

	protected GhostBlock(Properties settings) {
		super(settings);

		registerDefaultState(defaultBlockState().setValue(HAS_BLOCK, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HAS_BLOCK);
		super.createBlockStateDefinition(builder);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new GhostBlockEntity(pos, state);
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		GhostBlockEntity gbe = (GhostBlockEntity)world.getBlockEntity(pos);
		if (placer instanceof Player user && gbe != null) {
			gbe.setOwner(user);
		}

		super.setPlacedBy(world, pos, state, placer, itemStack);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return !state.getValue(HAS_BLOCK) ? RenderShape.MODEL : RenderShape.INVISIBLE;
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (player.isShiftKeyDown()) {
			if (hand == InteractionHand.MAIN_HAND && player.getItemInHand(hand).isEmpty()) {
				world.setBlockAndUpdate(pos, state.setValue(HAS_BLOCK, false));
				((GhostBlockEntity) world.getBlockEntity(pos)).setBlock(null);
			}
		} else {
			ItemStack stack = player.getItemInHand(hand);
			if (stack.getItem() instanceof BlockItem item) {
				Block block = item.getBlock();
				((GhostBlockEntity) world.getBlockEntity(pos)).setBlock(block);
				world.setBlockAndUpdate(pos, state.setValue(HAS_BLOCK, true));
			}
			return InteractionResult.CONSUME;
		}
		return InteractionResult.PASS;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		BlockEntity be = world.getBlockEntity(pos);
		if (!(be instanceof GhostBlockEntity gbe)) {
			return super.getShape(state, world, pos, context);
		}

		Block inner = gbe.getBlock();
		if (inner == null || inner == this) {  // avoid recursion
			return super.getShape(state, world, pos, context);
		}

		return inner.getShape(inner.defaultBlockState(), world, pos, context);
	}
}
