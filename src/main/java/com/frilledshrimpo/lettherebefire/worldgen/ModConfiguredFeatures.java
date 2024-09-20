package com.frilledshrimpo.lettherebefire.worldgen;

import com.frilledshrimpo.lettherebefire.blocks.ModBlocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import com.frilledshrimpo.lettherebefire.LetThereBeFire;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;


public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> STICK_GND_KEY = registerKey("stick_gnd");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(STICK_GND_KEY, new ConfiguredFeature<>(ModFeatures.CUSTOM_STICK_BLOCK.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.STICK_GND.get().defaultBlockState()))));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(LetThereBeFire.MODID, name));
    }
}
