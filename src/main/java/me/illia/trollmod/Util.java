package me.illia.trollmod;

import me.illia.trollmod.effect.EffectDescriptor;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class Util {
	public static Identifier id(String path) {
		return new Identifier(Trollmod.MODID, path);
	}

	public static Potion pot(Identifier id, EffectDescriptor... descs) {
		return Registry.register(
			Registries.POTION,
			id,
			new Potion(
				Arrays.stream(descs).map(desc -> {
					return new StatusEffectInstance(desc.entry().value(), desc.duration(), desc.amplifier());
				}).toArray(StatusEffectInstance[]::new)
			)
		);
	}

	public static void potRecipe(Item item, Potion output, Potion... input) {
		Arrays.stream(input).forEach(pot -> {
			BrewingRecipeRegistry.registerPotionRecipe(pot, item, output);
		});
	}

	public static EffectDescriptor desc(RegistryEntry<StatusEffect> entry, int duration, int amplifier) {
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

	public static void t(FabricLanguageProvider.TranslationBuilder builder, StatusEffect effect, String translation) {
		builder.add(effect, translation);
	}

	public static void t(FabricLanguageProvider.TranslationBuilder builder, RegistryKey<ItemGroup> group, String translation) {
		builder.add(group, translation);
	}

	public static void t(FabricLanguageProvider.TranslationBuilder builder, Item item, String translation) {
		builder.add(item, translation);
	}

	public static Identifier idFrom(String idStr) {
		return new Identifier(idStr);
	}
}
