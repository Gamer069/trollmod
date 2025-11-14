package me.illia.trollmod.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(InputConstants.class)
@Environment(EnvType.CLIENT)
public class RawInputMixin {
}
