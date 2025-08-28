package me.illia.trollmod.screen;

import me.illia.trollmod.recipe.TeapotRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

public class TeapotScreenHandler extends AbstractRecipeScreenHandler<Inventory> {
	public PlayerInventory playerInv;
	public World world;

	public TeapotScreenHandler(int syncId, PlayerInventory playerInv) {
		super(ModScreenHandlers.TEAPOT_SCREEN_HANDLER, syncId);
		this.playerInv = playerInv;
		this.world = playerInv.player.getWorld();
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int slot) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot2 = this.slots.get(slot);
		if (slot2 != null && slot2.hasStack()) {
			ItemStack itemStack2 = slot2.getStack();
			itemStack = itemStack2.copy();
			if (slot == 2) {
				if (!this.insertItem(itemStack2, 3, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot2.onQuickTransfer(itemStack2, itemStack);
			} else if (slot != 1 && slot != 0) {
				if (this.isBoilable(itemStack2)) {
					if (!this.insertItem(itemStack2, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (slot >= 2 && slot < 30) {
					if (!this.insertItem(itemStack2, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (slot >= 30 && slot < 39 && !this.insertItem(itemStack2, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(itemStack2, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slot2.setStack(ItemStack.EMPTY);
			} else {
				slot2.markDirty();
			}

			if (itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot2.onTakeItem(player, itemStack2);
		}

		return itemStack;
	}

	public boolean isBoilable(ItemStack stack) {
		return this.world.getRecipeManager().getFirstMatch(TeapotRecipe.Type.INSTANCE, new SimpleInventory(stack), this.world).isPresent();
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
	}

	@Override
	public boolean matches(Recipe<? super Inventory> recipe) {
		return false;
	}

	@Override
	public int getCraftingResultSlotIndex() {
		return 0;
	}

	@Override
	public int getCraftingWidth() {
		return 0;
	}

	@Override
	public int getCraftingHeight() {
		return 0;
	}

	@Override
	public int getCraftingSlotCount() {
		return 0;
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
