package com.frilledshrimpo.lettherebefire.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.frilledshrimpo.lettherebefire.LetThereBeFire;

public class ModRecipes {

    // Create DeferredRegister for Recipe Types and Serializers
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, LetThereBeFire.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LetThereBeFire.MODID);

    // Register HandMadeRecipe type and serializer
    public static final RegistryObject<RecipeType<HandMadeRecipe>> HAND_MADE_RECIPE = RECIPE_TYPES.register("hand_made",
            () -> RecipeType.simple(new ResourceLocation(LetThereBeFire.MODID, "hand_made")));

    public static final RegistryObject<RecipeSerializer<HandMadeRecipe>> HAND_MADE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("hand_made",
            HandMadeRecipeSerializer::new);
}
