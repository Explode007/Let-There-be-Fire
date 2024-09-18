package com.frilledshrimpo.lettherebefire.item;

import com.frilledshrimpo.lettherebefire.LetThereBeFire;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LetThereBeFire.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static final RegistryObject<Item> PLANTFIBER = ITEMS.register("plant_fiber", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLANTSTRING = ITEMS.register("plant_string", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SHARPROCK = ITEMS.register("sharp_rock", () -> new Item(new Item.Properties()));
}
