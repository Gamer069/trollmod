package me.illia.trollmod.item;

import me.illia.trollmod.screen.ModScreenHandlers;
import me.illia.trollmod.screen.TeapotScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TeapotItem extends Item implements NamedScreenHandlerFactory {
	public ToolMaterial material;

	public TeapotItem(ToolMaterial material, Settings settings) {
		super(settings);
		this.material = material;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		user.openHandledScreen(this);
		return super.use(world, user, hand);
	}

	@Override
	public Text getDisplayName() {
		return Text.translatable("screen.trollmod.teapot");
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
		return new TeapotScreenHandler(syncId, playerInventory);
	}
}
