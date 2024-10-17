package com.frilledshrimpo.lettherebefire.gui;

import com.frilledshrimpo.lettherebefire.LetThereBeFire;
import com.frilledshrimpo.lettherebefire.recipe.HandMadeRecipe;
import com.frilledshrimpo.lettherebefire.recipe.HandMadeRecipeSerializer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.widget.ScrollPanel;

import java.awt.*;
import java.util.List;


public class ScrollableRecipeList extends ScrollPanel {
    private final List<HandMadeRecipe> recipes;
    private final int buttonSize = 32;

    public ScrollableRecipeList(Minecraft mc, int width, int height, int top, int left, List<HandMadeRecipe> recipes) {
        super(mc, width, height, top, left);
        this.recipes = recipes;

    }

    @Override
    protected int getContentHeight() {
        return buttons.size() * buttonSize;
    }

    @Override
    protected void drawPanel(GuiGraphics guiGraphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
        int xStart = this.left + 10; // Start drawing from the left edge of the panel
        int yStart = relativeY; // Start drawing from the current scroll offset
        int columnWidth = buttonSize + 5; // Add padding between buttons
        int rowHeight = buttonSize + 10; // Add padding between rows

        // Calculate how many buttons can fit in one row
        int buttonsPerRow = (entryRight - this.left) / columnWidth; // Number of buttons per row based on panel width

        int column = 0; // Track which column we're in
        int row = 0; // Track which row we're in

        // Loop through each button
        for (HandMadeRecipe recipe : recipes) {
            ItemStack outputStack = recipe.getResultItem(null);
            ImageButton button = new ImageButton(
                    this.left + (column * columnWidth), yStart + (row* rowHeight),
                    buttonSize, buttonSize,
                    0,0,19,
                    new ResourceLocation(LetThereBeFire.MODID, "textures/gui/handcraftinginvbutton.png"),
                    17,37,
                    btn -> {}
            );
            // Render the button
            button.render(guiGraphics, mouseX, mouseY, 0); // Render the button

            // Move to the next column
            column++;

            // If we've filled the current row, move to the next row and reset the column
            if (column >= buttonsPerRow) {
                column = 0;
                row++;
            }
        }
    }

    private ResourceLocation getTextureForRecipe(HandMadeRecipe recipe) {
        // Get the item's name and generate the path dynamically
        String itemName = recipe.getResultItem(null).getItem().toString();

        // Create the resource path dynamically
        return new ResourceLocation(LetThereBeFire.MODID, "textures/gui/" + itemName + "_recipe.png");
    }

    private void openCraftingScreen(HandMadeRecipe recipe) {
        if (recipe.getResultItem(null).getItem().toString().equals("minecraft:stone_axe")) {
            Minecraft.getInstance().setScreen(new CraftStoneAxeScreen());
        }
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
        for (ImageButton button : buttons) {
            if (button.isHovered()) {
                pNarrationElementOutput.add(NarratedElementType.TITLE, Component.literal("Hovering over Hand Crafting Recipe: " + button.getMessage().getString()));
                return;
            }
        }

        // Provide a default narration when no buttons are hovered
        pNarrationElementOutput.add(NarratedElementType.TITLE, Component.literal("Scrollable hand crafting menu"));
    }
}