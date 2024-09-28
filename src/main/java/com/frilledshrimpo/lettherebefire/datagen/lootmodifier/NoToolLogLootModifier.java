package com.frilledshrimpo.lettherebefire.datagen.lootmodifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class NoToolLogLootModifier extends LootModifier {

    // Constructor
    public NoToolLogLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);

        // Ensure blockState is not null and is a log
        if (blockState != null && blockState.is(BlockTags.LOGS)) {
            ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);

            // If the tool is null or it's not an axe, prevent loot from dropping
            if (tool == null || !tool.is(ItemTags.AXES)) {
                return new ObjectArrayList<>(); // Return an empty loot list
            }
        }

        return generatedLoot; // Return the original loot if conditions aren't met
    }

    // Codec for NoToolLogLootModifier
    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    // Custom Codec for data generation and serialization
    public static final Codec<NoToolLogLootModifier> CODEC = RecordCodecBuilder.create(instance ->
            codecStart(instance).apply(instance, NoToolLogLootModifier::new)
    );
}
