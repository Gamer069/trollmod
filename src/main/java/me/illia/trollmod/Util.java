package me.illia.trollmod;

import me.illia.trollmod.effect.EffectDescriptor;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.level.block.Block;
import java.util.Arrays;

public class Util {
	public static ResourceLocation id(String path) {
		return new ResourceLocation(Trollmod.MODID, path);
	}

	public static Potion pot(ResourceLocation id, EffectDescriptor... descs) {
		return Registry.register(
			BuiltInRegistries.POTION,
			id,
			new Potion(
				Arrays.stream(descs).map(desc -> {
					return new MobEffectInstance(desc.entry().value(), desc.duration(), desc.amplifier());
				}).toArray(MobEffectInstance[]::new)
			)
		);
	}

	public static void potRecipe(Item item, Potion output, Potion... input) {
		Arrays.stream(input).forEach(pot -> {
			PotionBrewing.addMix(pot, item, output);
		});
	}

	public static EffectDescriptor desc(Holder<MobEffect> entry, int duration, int amplifier) {
		return new EffectDescriptor(entry, duration, amplifier);
	}

	public static void t(FabricLanguageProvider.TranslationBuilder builder, Block block, String translation) {
		builder.add(block, translation);
	}

	public static void t(FabricLanguageProvider.TranslationBuilder builder, EntityType<?> entityType, String translation) {
		builder.add(entityType, translation);
	}

	public static void t(FabricLanguageProvider.TranslationBuilder builder, String key, String translation) {
		builder.add(key, translation);
	}

	public static void t(FabricLanguageProvider.TranslationBuilder builder, MobEffect effect, String translation) {
		builder.add(effect, translation);
	}

	public static void t(FabricLanguageProvider.TranslationBuilder builder, ResourceKey<CreativeModeTab> group, String translation) {
		builder.add(group, translation);
	}

	public static void t(FabricLanguageProvider.TranslationBuilder builder, Item item, String translation) {
		builder.add(item, translation);
	}

	public static ResourceLocation idFrom(String idStr) {
		return new ResourceLocation(idStr);
	}
}
