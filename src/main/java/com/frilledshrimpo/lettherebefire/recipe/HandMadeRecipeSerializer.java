package com.frilledshrimpo.lettherebefire.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.ArrayList;
import java.util.List;

public class HandMadeRecipeSerializer implements RecipeSerializer<HandMadeRecipe> {

    @Override
    public HandMadeRecipe fromJson(ResourceLocation id, JsonObject json) {
        // Parse the output item
        ItemStack output = parseOutput(json);

        // Parse materials from the "materials" array in JSON
        List<ItemStack> materials = parseMaterials(json.getAsJsonArray("materials"));

        // Parse crafting stages from the "stages" array in JSON
        List<CraftingStage> stages = parseStages(json.getAsJsonArray("stages"));

        // Return a new HandMadeRecipe with the parsed data
        return new HandMadeRecipe(id, output, materials, stages);
    }

    @Override
    public HandMadeRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        // Deserialize from network packet
        ItemStack output = buffer.readItem();
        int materialCount = buffer.readInt();
        List<ItemStack> materials = new ArrayList<>();
        for (int i = 0; i < materialCount; i++) {
            materials.add(buffer.readItem());
        }

        int stageCount = buffer.readInt();
        List<CraftingStage> stages = new ArrayList<>();
        for (int i = 0; i < stageCount; i++) {
            String action = buffer.readUtf();
            int loops = buffer.readInt();
            stages.add(new CraftingStage(action, loops));
        }

        return new HandMadeRecipe(id, output, materials, stages);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, HandMadeRecipe recipe) {
        // Serialize to network packet
        buffer.writeItem(recipe.getResultItem(null));  // Write output

        // Write materials
        buffer.writeInt(recipe.getMaterials().size());
        for (ItemStack material : recipe.getMaterials()) {
            buffer.writeItem(material);
        }

        // Write crafting stages
        buffer.writeInt(recipe.getStages().size());
        for (CraftingStage stage : recipe.getStages()) {
            buffer.writeUtf(stage.getAction());
            buffer.writeInt(stage.getRequiredLoops());
        }
    }

    // Helper method to parse materials from the JSON
    private List<ItemStack> parseMaterials(JsonArray materialsArray) {
        List<ItemStack> materials = new ArrayList<>();
        for (JsonElement element : materialsArray) {
            JsonObject materialJson = element.getAsJsonObject();
            String itemId = GsonHelper.getAsString(materialJson, "item");
            int count = GsonHelper.getAsInt(materialJson, "count", 1); // Default count is 1 if not provided

            // Get the ItemStack from the registry using the item ID
            ItemStack material = new ItemStack(BuiltInRegistries.ITEM.get(new ResourceLocation(itemId)), count);
            materials.add(material);
        }
        return materials;
    }

    // Helper method to parse crafting stages from the JSON
    private List<CraftingStage> parseStages(JsonArray stagesArray) {
        List<CraftingStage> stages = new ArrayList<>();
        for (JsonElement element : stagesArray) {
            JsonObject stageJson = element.getAsJsonObject();
            String action = GsonHelper.getAsString(stageJson, "action");
            int loops = GsonHelper.getAsInt(stageJson, "loops_required", 1); // Default loops required to 1

            stages.add(new CraftingStage(action, loops));
        }
        return stages;
    }

    // Helper method to parse the output item from the JSON
    private ItemStack parseOutput(JsonObject json) {
        JsonObject result = GsonHelper.getAsJsonObject(json, "output");
        String itemId = GsonHelper.getAsString(json, "item");
        int count = GsonHelper.getAsInt(result, "count", 1);

        return new ItemStack(BuiltInRegistries.ITEM.get(new ResourceLocation(itemId)), count);
    }
}
