package me.illia.trollmod.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import me.illia.trollmod.entity.BoomerangEntity;

import java.util.UUID;

public interface BoomerangCatchComponent extends Component, AutoSyncedComponent {
	boolean isWithin();
	void toggle();

	void set(boolean val);
	void setEntity(int entity);
	int getEntity();
}
