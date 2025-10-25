package me.illia.trollmod.datagen;

import com.google.gson.JsonObject;
import me.illia.trollmod.Util;
import me.illia.trollmod.damage.ModDamageTypes;
import me.illia.trollmod.datagen.providers.*;
import me.illia.trollmod.datagen.providers.lang.ModEnUsProvider;
import me.illia.trollmod.datagen.providers.lang.ModUkUaProvider;
import me.illia.trollmod.world.ModConfiguredFeatures;
import me.illia.trollmod.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

import java.util.concurrent.CompletableFuture;

public class TrollmodDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, registerable -> {
			registerable.register(ModDamageTypes.BOOMERANG_DAMAGE_TYPE_KEY, ModDamageTypes.BOOMERANG_DAMAGE_TYPE);
		});
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModBlockLootTableProvider::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModWorldGenProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(BoomerangDamageTypeGenerator::new);

		// LANGS
		pack.addProvider(ModEnUsProvider::new);
		pack.addProvider(ModUkUaProvider::new);
	}

	private static class BoomerangDamageTypeGenerator implements DataProvider {
		private final DataOutput.PathResolver path;

		public BoomerangDamageTypeGenerator(FabricDataOutput fabricDataOutput) {
			path = fabricDataOutput.getResolver(DataOutput.OutputType.DATA_PACK, "damage_type/");
		}

		@Override
		public CompletableFuture<?> run(DataWriter writer) {
			JsonObject damageTypeObject = new JsonObject();

			damageTypeObject.addProperty("exhaustion", ModDamageTypes.BOOMERANG_DAMAGE_TYPE.exhaustion());
			damageTypeObject.addProperty("message_id", ModDamageTypes.BOOMERANG_DAMAGE_TYPE.msgId());
			damageTypeObject.addProperty("scaling", ModDamageTypes.BOOMERANG_DAMAGE_TYPE.scaling().asString());

			return DataProvider.writeToPath(writer, damageTypeObject, path.resolveJson(Util.id("boomerang_damage_type")));
		}

		@Override
		public String getName() {
			return "Damage Type";
		}
	}
}
