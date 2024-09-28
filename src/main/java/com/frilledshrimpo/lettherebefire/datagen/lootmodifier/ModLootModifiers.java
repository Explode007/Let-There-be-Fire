package com.frilledshrimpo.lettherebefire.datagen.lootmodifier;

import com.frilledshrimpo.lettherebefire.LetThereBeFire;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, LetThereBeFire.MODID);

    public static final RegistryObject<Codec<SharpRockLootModifier>> SHARP_ROCK = LOOT_MODIFIERS.register(
            "sharp_rock", () -> SharpRockLootModifier.CODEC
    );

    public static final RegistryObject<Codec<NoToolLogLootModifier>> NO_LOGS = LOOT_MODIFIERS.register(
            "no_logs", () -> NoToolLogLootModifier.CODEC
    );
}