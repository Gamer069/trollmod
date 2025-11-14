package me.illia.trollmod.attachment;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Uuids;

import java.util.UUID;

public class ModAttachmentTypes {
	@SuppressWarnings("UnstableApiUsage")
	public static final AttachmentType<UUID> IS_CONTROLLED = AttachmentRegistry.createPersistent(
		Util.id("is_controlled"),
		Uuids.CODEC
	);

	public static void init() {
		Trollmod.LOGGER.info("Initializing data attachments for " + Trollmod.MODID);
	}
}
