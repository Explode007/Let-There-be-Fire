package com.frilledshrimpo.lettherebefire.worldgen;

import com.frilledshrimpo.lettherebefire.LetThereBeFire;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, LetThereBeFire.MODID);

    public static final RegistryObject<Feature<SimpleBlockConfiguration>> CUSTOM_STICK_BLOCK = FEATURES.register("custom_stick_block", StickGndFeature::new);

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
