package com.frilledshrimpo.lettherebefire.item;

import com.frilledshrimpo.lettherebefire.LetThereBeFire;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LetThereBeFire.MODID);
    public static final RegistryObject<Item> PLANT_FIBER = ITEMS.register("plant_fiber", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLANT_STRING = ITEMS.register("plant_string", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SHARP_ROCK = ITEMS.register("sharp_rock", () -> new SharpRockItem(new Item.Properties()));
    public static final RegistryObject<Item> LOOSE_ROCK = ITEMS.register("loose_rock", () -> new LooseRockItem(new Item.Properties()));
    public static final RegistryObject<Item> CARVED_STICK = ITEMS.register("carved_stick", () -> new CarvedStickItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
