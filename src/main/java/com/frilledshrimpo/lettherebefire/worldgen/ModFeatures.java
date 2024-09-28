package com.frilledshrimpo.lettherebefire.worldgen;

import com.frilledshrimpo.lettherebefire.LetThereBeFire;
import com.frilledshrimpo.lettherebefire.worldgen.features.PlaceableFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, LetThereBeFire.MODID);

    public static final RegistryObject<Feature<SimpleBlockConfiguration>> STICK_GND_FEATURE = FEATURES.register("stick_gnd_feature", PlaceableFeature::new);
    public static final RegistryObject<Feature<SimpleBlockConfiguration>> ROCK_GND_FEATURE = FEATURES.register("rock_gnd_feature", PlaceableFeature::new);

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
