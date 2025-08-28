package me.illia.trollmod.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class TeapotRecipe implements Recipe<SimpleInventory> {
	private final ItemStack output;
	private final Ingredient input;

	public TeapotRecipe(Ingredient recipeItems, ItemStack output) {
		this.input = recipeItems;
		this.output = output;
	}

	@Override
	public boolean matches(SimpleInventory inventory, World world) {
		if (!world.isClient()) {
			return false;
		}

		return input.test(inventory.getStack(0));
	}

	@Override
	public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
		return output;
	}

	@Override
	public boolean fits(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getOutput(DynamicRegistryManager registryManager) {
		return output;
	}

	@Override
	public DefaultedList<Ingredient> getIngredients() {
		DefaultedList<Ingredient> list = DefaultedList.ofSize(1);
		list.add(input);

		return list;
	}

	@Override
	public Identifier getId() {
		return null;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return Serializer.INSTANCE;
	}

	@Override
	public RecipeType<?> getType() {
		return Type.INSTANCE;
	}

	public static class Type implements RecipeType<TeapotRecipe> {
		public static final Type INSTANCE = new Type();
		public static final String ID = "teapot";
	}

	public static class Serializer implements RecipeSerializer<TeapotRecipe> {
		public static final Serializer INSTANCE = new Serializer();
		public static final String ID = "teapot";

		@Override
		public TeapotRecipe read(Identifier id, JsonObject json) {
			TeapotRecipeJSONFormat teapotRecipeJSONFormat = new Gson().fromJson(json, TeapotRecipeJSONFormat.class);

			if (teapotRecipeJSONFormat.input == null) {
				throw new JsonSyntaxException("input is missing");
			} else if (teapotRecipeJSONFormat.outputItem == null) {
				throw new JsonSyntaxException("outputItem is missing");
			}

			Item outputItem = Registries.ITEM.getOrEmpty(new Identifier(teapotRecipeJSONFormat.outputItem))
				.orElseThrow(() -> new JsonSyntaxException("No such item: " + teapotRecipeJSONFormat.outputItem));

			ItemStack outputItemStack = outputItem.getDefaultStack();
			outputItemStack.setCount(teapotRecipeJSONFormat.outputAmount);

			return new TeapotRecipe(Ingredient.fromJson(teapotRecipeJSONFormat.input), outputItemStack);
		}

		@Override
		public TeapotRecipe read(Identifier id, PacketByteBuf buf) {
			Ingredient input = Ingredient.fromPacket(buf);
			ItemStack output = buf.readItemStack();

			return new TeapotRecipe(input, output);
		}

		@Override
		public void write(PacketByteBuf buf, TeapotRecipe recipe) {
			recipe.input.write(buf);
			buf.writeItemStack(recipe.output);
		}
	}
}
