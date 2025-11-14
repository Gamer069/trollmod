package me.illia.trollmod.component;

import net.minecraft.nbt.CompoundTag;

public class PhasingComponentImpl implements PhasingComponent {
	public boolean phasing;
	public int ticksLeft;

	@Override
	public boolean isPhasing() {
		return phasing;
	}

	@Override
	public void setPhasing(boolean phasing) {
		this.phasing = phasing;
	}

	@Override
	public int getTicksLeft() {
		return ticksLeft;
	}

	@Override
	public void setTicksLeft(int ticksLeft) {
		this.ticksLeft = ticksLeft;
	}

	@Override
	public void readFromNbt(CompoundTag nbtCompound) {
		this.phasing = nbtCompound.getBoolean("phasing");
	}

	@Override
	public void writeToNbt(CompoundTag nbtCompound) {
		nbtCompound.putBoolean("phasing", this.phasing);
	}
}
