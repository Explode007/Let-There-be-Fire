package com.frilledshrimpo.lettherebefire.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class HandCraftingMenu extends Screen {
    private ScrollableRecipeList scrollableRecipeList;

    public HandCraftingMenu() {
        super(Component.literal("Hand Crafting Menu"));
    }

    @Override
    protected void init() {
        super.init();
        int panelWidth = (int) (this.width * 0.5);  // 50% of the screen width
        int panelHeight = (int) (this.height * 0.5);  // 50% of the screen height

        int panelLeft = (this.width - panelWidth) / 2;
        int panelTop = (this.height - panelHeight) / 2;

// Initialize the scrollable recipe list
        scrollableRecipeList = new ScrollableRecipeList(
                Minecraft.getInstance(),
                panelWidth,   // Use calculated width
                panelHeight,  // Use calculated height
                panelTop,     // Centered top position
                panelLeft   // Centered left position
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
