package com.frilledshrimpo.lettherebefire.item;

import com.frilledshrimpo.lettherebefire.LetThereBeFire;
import com.frilledshrimpo.lettherebefire.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LetThereBeFire.MODID);

    public static final RegistryObject<CreativeModeTab> LTBF_TAB = CREATIVE_MODE_TABS.register("ltbf_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PLANTFIBER.get()))
                    .title(Component.translatable( "creativetab.ltbf_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.PLANTFIBER.get());
                        output.accept(ModItems.PLANTSTRING.get());
                        output.accept(ModItems.SHARPROCK.get());
                        output.accept(ModBlocks.STICK_GND.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
