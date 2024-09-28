package com.frilledshrimpo.lettherebefire.datagen.providers;

import com.frilledshrimpo.lettherebefire.LetThereBeFire;

import com.frilledshrimpo.lettherebefire.datagen.lootmodifier.NoToolLogLootModifier;
import com.frilledshrimpo.lettherebefire.datagen.lootmodifier.SharpRockLootModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;


public class ModLootModifierProvider extends GlobalLootModifierProvider {

    public ModLootModifierProvider(PackOutput packOutput) {
        super(packOutput, LetThereBeFire.MODID);
    }

    @Override
    protected void start() {
        // Register the SharpRockLootModifier here
        add("sharp_rock_loot_modifier", new SharpRockLootModifier(new LootItemCondition[0]));
        add("no_tool_log_loot_modifier", new NoToolLogLootModifier(new LootItemCondition[0]));
    }

}
