package com.frilledshrimpo.lettherebefire.events;

import com.frilledshrimpo.lettherebefire.LetThereBeFire;
import com.frilledshrimpo.lettherebefire.gui.HandCraftingMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = "lettherebefire", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModGuiEvents {

    @SubscribeEvent
    public static void onInitGui(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof InventoryScreen) { // Build the Hand Crafting Button in the Inventory Screen
            // Get the screen's width and height
            int InvLeft = ((InventoryScreen) event.getScreen()).getGuiLeft(); // X position of crafting grid
            int InvTop = ((InventoryScreen) event.getScreen()).getGuiTop();  // Y position of crafting grid

            int x = InvLeft + 150;
            int y = InvTop + 58;

            // Create the button using Button.Builder
            ImageButton handCraftButton = new ImageButton(
                    x, y, 18, 18, 0, 0,19,
                    new ResourceLocation(LetThereBeFire.MODID, "textures/gui/handcraftinginvbutton.png"), 17,37,
                    btn -> {
                        Minecraft.getInstance().setScreen(new HandCraftingMenu());
                    }
            );

            // Add the button to the inventory screen
            event.addListener(handCraftButton);

        }
    }
}
