package me.illia.trollmod.item;

import me.illia.trollmod.screen.ModScreenHandlers;
import me.illia.trollmod.screen.TeapotScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class TeapotItem extends Item implements MenuProvider {
	public Tier material;

	public TeapotItem(Tier material, Properties settings) {
		super(settings);
		this.material = material;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		user.openMenu(this);
		return super.use(world, user, hand);
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("screen.trollmod.teapot");
	}

	@Override
	public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
		return new TeapotScreenHandler(syncId, playerInventory);
	}
}
