package me.illia.trollmod.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class TeapotRecipe implements Recipe<SimpleContainer> {
	private final ItemStack output;
	private final Ingredient input;
	private final ResourceLocation id;

	public TeapotRecipe(ResourceLocation id, Ingredient recipeItems, ItemStack output) {
		this.input = recipeItems;
		this.output = output;
		this.id = id;
	}

	@Override
	public boolean matches(SimpleContainer inventory, Level world) {
		if (!world.isClientSide()) {
			return false;
		}

		return input.test(inventory.getItem(0));
	}

	@Override
	public ItemStack craft(SimpleContainer inventory, RegistryAccess registryManager) {
		return output;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryManager) {
		return output;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = NonNullList.createWithCapacity(1);
		list.add(input);

		return list;
	}

	@Override
	public ResourceLocation getId() {
		return id;
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
		public TeapotRecipe fromJson(ResourceLocation id, JsonObject json) {
			TeapotRecipeJSONFormat teapotRecipeJSONFormat = new Gson().fromJson(json, TeapotRecipeJSONFormat.class);

			if (teapotRecipeJSONFormat.input == null) {
				throw new JsonSyntaxException("input is missing");
			} else if (teapotRecipeJSONFormat.outputItem == null) {
				throw new JsonSyntaxException("outputItem is missing");
			}

			Item outputItem = BuiltInRegistries.ITEM.getOptional(new ResourceLocation(teapotRecipeJSONFormat.outputItem))
				.orElseThrow(() -> new JsonSyntaxException("No such item: " + teapotRecipeJSONFormat.outputItem));

			ItemStack outputItemStack = outputItem.getDefaultInstance();
			outputItemStack.setCount(teapotRecipeJSONFormat.outputAmount);

			return new TeapotRecipe(id, Ingredient.fromJson(teapotRecipeJSONFormat.input), outputItemStack);
		}

		@Override
		public TeapotRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			Ingredient input = Ingredient.fromNetwork(buf);
			ItemStack output = buf.readItem();

			return new TeapotRecipe(id, input, output);
		}

		@Override
		public void write(FriendlyByteBuf buf, TeapotRecipe recipe) {
			recipe.input.toNetwork(buf);
			buf.writeItem(recipe.output);
		}
	}
}
