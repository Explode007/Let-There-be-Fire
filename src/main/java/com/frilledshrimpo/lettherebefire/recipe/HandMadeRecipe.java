package com.frilledshrimpo.lettherebefire.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

public class HandMadeRecipe implements Recipe<Inventory> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final List<ItemStack> materials;  // List of required materials
    private final List<CraftingStage> stages;  // Stages for mini-game

    public HandMadeRecipe(ResourceLocation id, ItemStack output, List<ItemStack> materials, List<CraftingStage> stages) {
        this.id = id;
        this.output = output;
        this.materials = materials;
        this.stages = stages;
    }


    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        // Return the result of the crafting
        return output.copy();
    }

    @Override
    public boolean matches(Inventory inv, Level level) {
        // Check if the inventory contains the required materials
        for (ItemStack required : materials) {
            int requiredCount = required.getCount();
            int foundCount = 0;

            // Loop through the inventory
            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack stackInSlot = inv.getItem(i);
                if (ItemStack.isSameItem(stackInSlot, required)) {
                    foundCount += stackInSlot.getCount();
                    if (foundCount >= requiredCount) {
                        break;  // Found enough of this material
                    }
                }
            }

            if (foundCount < requiredCount) {
                return false;  // Not enough materials
            }
        }
        return true;  // All materials are present
    }

    public List<ItemStack> getMaterials() {
        return materials;
    }

    @Override
    public ItemStack assemble(Inventory inv, RegistryAccess registryAccess) {
        // Return the crafted result, this could be where the crafting logic happens
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        // Assume crafting can happen regardless of the dimensions
        return true;
    }


    public List<CraftingStage> getStages() {
        return stages;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.HAND_MADE_RECIPE.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.HAND_MADE_RECIPE_SERIALIZER.get();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public boolean isSpecial() {
        return true;  // Special recipes don't appear in the recipe book
    }
}