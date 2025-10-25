package me.illia.trollmod.screen;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.networking.ModNetworking;
import me.illia.trollmod.recipe.TeapotRecipe;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

public class TeapotScreenHandler extends AbstractRecipeScreenHandler<Inventory> {
	public PlayerInventory playerInv;
	public SimpleInventory inv;
	public World world;
	public boolean updating;

	private void updateResult() {
		if (updating || world == null) return;
		updating = true;

		ItemStack input = inv.getStack(0);
		if (input.isEmpty()) {
			if (!inv.getStack(1).isEmpty()) {
				inv.setStack(1, ItemStack.EMPTY);
			}
			updating = false;
			return;
		}

		world.getRecipeManager().getFirstMatch(TeapotRecipe.Type.INSTANCE, inv, world)
			.ifPresentOrElse(recipe -> {
				ItemStack result = recipe.craft(inv, world.getRegistryManager());
				ItemStack current = inv.getStack(1);
				if (!ItemStack.areEqual(current, result)) {
					inv.setStack(1, result.copy());
				}
			}, () -> {
				if (!inv.getStack(1).isEmpty()) {
					inv.setStack(1, ItemStack.EMPTY);
				}
			});

		updating = false;
	}

	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);

		for (int i = 0; i < this.inv.size(); i++) {
			ItemStack stack = this.inv.getStack(i);
			if (!stack.isEmpty()) {
				if (player.getInventory().getEmptySlot() == -1)
					player.dropItem(stack, false);
				else player.giveItemStack(stack);

				this.inv.setStack(i, ItemStack.EMPTY);
			}
		}
	}

	public TeapotScreenHandler(int syncId, PlayerInventory playerInv) {
		super(ModScreenHandlers.TEAPOT_SCREEN_HANDLER, syncId);

		this.playerInv = playerInv;
		this.world = playerInv.player.getWorld();

		this.inv = new SimpleInventory(2) {
			@Override
			public void markDirty() {
				super.markDirty();
				if (world.isClient) {
					PacketByteBuf buf = PacketByteBufs.create();
					buf.writeByte(syncId);
					buf.writeNbt(Inventories.writeNbt(new NbtCompound(), this.stacks));
					buf.writeInt(this.size());
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
	public ItemStack quickMove(PlayerEntity player, int slotI) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.slots.get(slotI);

		if (slot != null && slot.hasStack()) {
			ItemStack orig = slot.getStack();
			stack = orig.copy();
			if (slotI < this.inv.size()) {
				if (!this.insertItem(orig, this.inv.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(orig, 0, this.inv.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (orig.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}
		return stack;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}

	@Override
	public void populateRecipeFinder(RecipeMatcher finder) {
	}

	@Override
	public void clearCraftingSlots() {
		inv.setStack(0, ItemStack.EMPTY);
	}

	@Override
	public boolean matches(Recipe<? super Inventory> recipe) {
		return recipe.matches(inv, world);
	}

	@Override
	public int getCraftingResultSlotIndex() {
		return 1;
	}

	@Override
	public int getCraftingWidth() {
		return 1;
	}

	@Override
	public int getCraftingHeight() {
		return 1;
	}

	@Override
	public int getCraftingSlotCount() {
		return 1;
	}

	@Override
	public RecipeBookCategory getCategory() {
		return null;
	}

	@Override
	public boolean canInsertIntoSlot(int index) {
		return false;
	}
}
