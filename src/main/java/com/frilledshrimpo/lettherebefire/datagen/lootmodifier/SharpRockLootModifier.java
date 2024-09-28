package com.frilledshrimpo.lettherebefire.datagen.lootmodifier;

import com.frilledshrimpo.lettherebefire.item.ModItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class SharpRockLootModifier extends LootModifier {

    // Custom Codec (for data generation and serialization)
    public static final Codec<SharpRockLootModifier> CODEC = RecordCodecBuilder.create(instance ->
            codecStart(instance).apply(instance, SharpRockLootModifier::new)
    );

    // Constructor
    public SharpRockLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);

        // Ensure blockState is not null before checking if it is grass or tall grass
        if (blockState != null && (blockState.is(Blocks.GRASS) || blockState.is(Blocks.TALL_GRASS))) {
            ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);

            // Check if the tool is not null and if it's the sharp rock
            if (tool != null && tool.is(ModItems.SHARP_ROCK.get())) {
                // Add plant fiber to the existing loot
                generatedLoot.add(new ItemStack(ModItems.PLANT_FIBER.get(), 1));
            }
        }

        return generatedLoot;
    }

    // Codec for the SharpRockLootModifier
    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
