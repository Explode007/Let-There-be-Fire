package com.frilledshrimpo.lettherebefire.worldgen;

import com.frilledshrimpo.lettherebefire.LetThereBeFire;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import org.slf4j.Logger;

import java.util.List;


public class ModPlacedFeatures {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceKey<PlacedFeature> STICK_GND_PLACED_KEY = registerKey("stick_gnd");
    public static final ResourceKey<PlacedFeature> ROCK_GND_PLACED_KEY = registerKey("rock_gnd");


    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, STICK_GND_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.STICK_GND_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        );

        register(context, ROCK_GND_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ROCK_GND_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        );

    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(LetThereBeFire.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
