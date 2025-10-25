package me.illia.trollmod.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import me.illia.trollmod.entity.BoomerangEntity;
import net.minecraft.nbt.NbtCompound;

public class BoomerangCatchComponentImpl implements BoomerangCatchComponent {
	private int boomerang;
	private boolean withinRadius = false;

	@Override
	public void readFromNbt(NbtCompound nbtCompound) {
		withinRadius = nbtCompound.getBoolean("withinRadius");
	}

	@Override
	public void writeToNbt(NbtCompound nbtCompound) {
		nbtCompound.putBoolean("withinRadius", withinRadius);
	}

	@Override
	public boolean isWithin() {
		return withinRadius;
	}

	@Override
	public void toggle() {
		withinRadius = !withinRadius;
	}

	@Override
	public void set(boolean val) {
		withinRadius = val;
	}

	@Override
	public int getEntity() {
		return boomerang;
	}

	@Override
	public void setEntity(int entity) {
		this.boomerang = entity;
	}
}
