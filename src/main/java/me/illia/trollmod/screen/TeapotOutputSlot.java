package me.illia.trollmod.screen;

import me.illia.trollmod.recipe.TeapotRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class TeapotOutputSlot extends Slot {
	public TeapotOutputSlot(Container inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return false;
	}
}
