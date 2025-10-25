package me.illia.trollmod.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

public interface PhasingComponent extends Component, AutoSyncedComponent {
	boolean isPhasing();
	void setPhasing(boolean phasing);

	int getTicksLeft();
	void setTicksLeft(int ticksLeft);
}
