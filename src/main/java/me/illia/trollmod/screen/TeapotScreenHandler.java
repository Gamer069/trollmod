package me.illia.trollmod.screen;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.networking.ModNetworking;
import me.illia.trollmod.recipe.TeapotRecipe;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

public class TeapotScreenHandler extends RecipeBookMenu<Container> {
	public Inventory playerInv;
	public SimpleContainer inv;
	public Level world;
	public boolean updating;

	private void updateResult() {
		if (updating || world == null) return;
		updating = true;

		ItemStack input = inv.getItem(0);
		if (input.isEmpty()) {
			if (!inv.getItem(1).isEmpty()) {
				inv.setItem(1, ItemStack.EMPTY);
			}
			updating = false;
			return;
		}

		world.getRecipeManager().getRecipeFor(TeapotRecipe.Type.INSTANCE, inv, world)
			.ifPresentOrElse(recipe -> {
				ItemStack result = recipe.craft(inv, world.registryAccess());
				ItemStack current = inv.getItem(1);
				if (!ItemStack.matches(current, result)) {
					inv.setItem(1, result.copy());
				}
			}, () -> {
				if (!inv.getItem(1).isEmpty()) {
					inv.setItem(1, ItemStack.EMPTY);
				}
			});

		updating = false;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);

		for (int i = 0; i < this.inv.getContainerSize(); i++) {
			ItemStack stack = this.inv.getItem(i);
			if (!stack.isEmpty()) {
				if (player.getInventory().getFreeSlot() == -1)
					player.drop(stack, false);
				else player.addItem(stack);

				this.inv.setItem(i, ItemStack.EMPTY);
			}
		}
	}

	public TeapotScreenHandler(int syncId, Inventory playerInv) {
		super(ModScreenHandlers.TEAPOT_SCREEN_HANDLER, syncId);

		this.playerInv = playerInv;
		this.world = playerInv.player.level();

		this.inv = new SimpleContainer(2) {
			@Override
			public void setChanged() {
				super.setChanged();
				if (world.isClientSide) {
					FriendlyByteBuf buf = PacketByteBufs.create();
					buf.writeByte(syncId);
					buf.writeNbt(ContainerHelper.saveAllItems(new CompoundTag(), this.items));
					buf.writeInt(this.getContainerSize());
					ClientPlayNetworking.send(ModNetworking.TEAPOT_SYNC, buf);

					Trollmod.LOGGER.info("Client: " + inv.toString());
				} else {
					Trollmod.LOGGER.info("Server: " + inv.toString());
				}
				if (!updating)
					updateResult();
			}
		};

		Trollmod.LOGGER.info(inv.toString());

		this.addSlot(new Slot(inv, 0, 44, 35));
		this.addSlot(new TeapotOutputSlot(inv, 1, 116, 35));

		// render player hotbar
		int i = 0;
		for (;i < 9; i++) {
			this.addSlot(new Slot(playerInv, i, 8 + i * 18, 142));
		}

		// render player inv
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlot(new Slot(playerInv, i, 8 + x * 18, 84 + y * 18));
				i++;
			}
		}
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotI) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.slots.get(slotI);

		if (slot != null && slot.hasItem()) {
			ItemStack orig = slot.getItem();
			stack = orig.copy();
			if (slotI < this.inv.getContainerSize()) {
				if (!this.moveItemStackTo(orig, this.inv.getContainerSize(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(orig, 0, this.inv.getContainerSize(), false)) {
				return ItemStack.EMPTY;
			}

			if (orig.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}
		return stack;
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public void fillCraftSlotsStackedContents(StackedContents finder) {
	}

	@Override
	public void clearCraftingContent() {
		inv.setItem(0, ItemStack.EMPTY);
	}

	@Override
	public boolean recipeMatches(Recipe<? super Container> recipe) {
		return recipe.matches(inv, world);
	}

	@Override
	public int getResultSlotIndex() {
		return 1;
	}

	@Override
	public int getGridWidth() {
		return 1;
	}

	@Override
	public int getGridHeight() {
		return 1;
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public RecipeBookType getRecipeBookType() {
		return null;
	}

	@Override
	public boolean shouldMoveToInventory(int index) {
		return false;
	}
}
