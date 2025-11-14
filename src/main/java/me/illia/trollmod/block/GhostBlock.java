package me.illia.trollmod.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GhostBlock extends BlockWithEntity {
	public static final BooleanProperty HAS_BLOCK = BooleanProperty.of("has_block");

	protected GhostBlock(Settings settings) {
		super(settings);

		setDefaultState(getDefaultState().with(HAS_BLOCK, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(HAS_BLOCK);
		super.appendProperties(builder);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new GhostBlockEntity(pos, state);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		GhostBlockEntity gbe = (GhostBlockEntity)world.getBlockEntity(pos);
		if (placer instanceof PlayerEntity user && gbe != null) {
			gbe.setOwner(user);
		}

		super.onPlaced(world, pos, state, placer, itemStack);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return !state.get(HAS_BLOCK) ? BlockRenderType.MODEL : BlockRenderType.INVISIBLE;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (player.isSneaking()) {
			if (hand == Hand.MAIN_HAND && player.getStackInHand(hand).isEmpty()) {
				world.setBlockState(pos, state.with(HAS_BLOCK, false));
				((GhostBlockEntity) world.getBlockEntity(pos)).setBlock(null);
			}
		} else {
			ItemStack stack = player.getStackInHand(hand);
			if (stack.getItem() instanceof BlockItem item) {
				Block block = item.getBlock();
				((GhostBlockEntity) world.getBlockEntity(pos)).setBlock(block);
				world.setBlockState(pos, state.with(HAS_BLOCK, true));
			}
			return ActionResult.CONSUME;
		}
		return ActionResult.PASS;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		BlockEntity be = world.getBlockEntity(pos);
		if (!(be instanceof GhostBlockEntity gbe)) {
			return super.getOutlineShape(state, world, pos, context);
		}

		Block inner = gbe.getBlock();
		if (inner == null || inner == this) {  // avoid recursion
			return super.getOutlineShape(state, world, pos, context);
		}

		return inner.getOutlineShape(inner.getDefaultState(), world, pos, context);
	}
}
