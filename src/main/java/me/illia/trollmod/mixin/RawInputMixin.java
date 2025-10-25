package me.illia.trollmod.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(InputUtil.class)
@Environment(EnvType.CLIENT)
public class RawInputMixin {
}
