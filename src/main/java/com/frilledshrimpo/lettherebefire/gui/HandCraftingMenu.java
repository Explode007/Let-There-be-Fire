package com.frilledshrimpo.lettherebefire.gui;

import com.frilledshrimpo.lettherebefire.LetThereBeFire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class HandCraftingMenu extends Screen {
    private ScrollableRecipeList scrollableRecipeList;
    private final List<ImageButton> buttons = new ArrayList<>(); // Button list for recipes

    public HandCraftingMenu() {
        super(Component.literal("Hand Crafting Menu"));
    }

    @Override
    protected void init() {
        super.init();

        buttons.clear();


        // Example buttons
        for (int i = 0; i < 5; i++) {
            ImageButton button = new ImageButton(
                    0, 0, 18, 18,
                    0, 0, 19,
                    new ResourceLocation(LetThereBeFire.MODID, "textures/gui/handcraftinginvbutton.png"),
                    17, 37,
                    btn -> {
                        // Define what happens when a button is clicked
                    });
            buttons.add(button);
        }

        // Initialize the scrollable recipe list and add it to the screen
// Set width and height as percentages of the screen dimensions
        int panelWidth = (int) (this.width * 0.5);  // 50% of the screen width
        int panelHeight = (int) (this.height * 0.5);  // 50% of the screen height

// Calculate the top and left positions to center the panel
        int panelLeft = (this.width - panelWidth) / 2;
        int panelTop = (this.height - panelHeight) / 2;

// Initialize the scrollable recipe list
        scrollableRecipeList = new ScrollableRecipeList(
                Minecraft.getInstance(),
                panelWidth,   // Use calculated width
                panelHeight,  // Use calculated height
                panelTop,     // Centered top position
                panelLeft,    // Centered left position
                buttons
        );


        // Add the scrollable panel as a renderable widget
        this.addRenderableWidget(scrollableRecipeList);
    }


    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics); // Render the background

        // Render the scrollable list and other components
        scrollableRecipeList.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick); // Render any additional widgets
    }
}
