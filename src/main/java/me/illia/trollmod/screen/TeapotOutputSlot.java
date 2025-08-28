package me.illia.trollmod.screen;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class TeapotOutputSlot extends Slot {
	public TeapotOutputSlot(Inventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	@Override
	public boolean canInsert(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack takeStack(int amount) {
		if (this.hasStack()) {
			ItemStack stack = getStack();
			stack.setCount(stack.getCount() + Math.min(amount, this.getStack().getCount()));

			this.setStack(stack);
		}

		return super.takeStack(amount);
	}
}
