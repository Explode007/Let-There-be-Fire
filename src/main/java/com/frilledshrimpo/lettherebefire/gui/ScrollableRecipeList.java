package com.frilledshrimpo.lettherebefire.gui;

import com.frilledshrimpo.lettherebefire.LetThereBeFire;
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


import java.util.ArrayList;
import java.util.List;


public class ScrollableRecipeList extends ScrollPanel {
    private final List<ImageButton> buttons;
    private final int buttonSize = 32;

    public ScrollableRecipeList(Minecraft mc, int width, int height, int top, int left) {
        super(mc, width, height, top, left);
        this.buttons = new ArrayList<>();


    }

    @Override
    protected int getContentHeight() {
        return -1;
    }

    @Override
    protected void drawPanel(GuiGraphics guiGraphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
        for (ImageButton button : buttons) {
            button.render(guiGraphics, mouseX, mouseY, 0);
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