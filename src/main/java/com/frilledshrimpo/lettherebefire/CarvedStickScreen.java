package com.frilledshrimpo.lettherebefire;

import com.frilledshrimpo.lettherebefire.item.ModItems;
import com.frilledshrimpo.lettherebefire.network.ActionPacket;
import com.frilledshrimpo.lettherebefire.network.ModPacketHandler;
import com.frilledshrimpo.lettherebefire.network.PlayerAction;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;

import java.util.Comparator;

public class CarvedStickScreen extends Screen {
    private static final ResourceLocation TEXTURE = new ResourceLocation(LetThereBeFire.MODID, "textures/gui/grass_gui.png");
    private static final ResourceLocation CARVED_STICK_FRONT = new ResourceLocation(LetThereBeFire.MODID, "textures/gui/carved_stick_front.png");
    private static final ResourceLocation SHARP_ROCK_TEXTURE = new ResourceLocation("lettherebefire", "textures/item/sharp_rock.png");
    private static final ResourceLocation CARVED_STICK_TEXTURE = new ResourceLocation("lettherebefire", "textures/item/carved_stick.png");

    private final int imageWidth = 64;
    private final int imageHeight = 64;
    private int leftPos, topPos, rockPosX, rockPosY, stickPosX, stickPosY, stringPosX, stringPosY;
    private boolean draggingRock = false, rockSettled = false, draggingString = false;
    private int windLoops = 0;
    private int lastMouseX = -1, lastMouseY = -1;

    public CarvedStickScreen() {
        super(Component.translatable("gui.carved_stick.title"));
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        this.stickPosX = this.leftPos + 20;
        this.stickPosY = this.topPos + 30;
        this.rockPosX = this.leftPos + 40;
        this.rockPosY = this.topPos + 20;
        this.stringPosX = this.leftPos + 40;
        this.stringPosY = this.topPos + 20;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.disableScissor();
        this.renderBackground(pGuiGraphics);
        Minecraft.getInstance().getTextureManager().bindForSetup(TEXTURE);
        Minecraft.getInstance().getTextureManager().bindForSetup(CARVED_STICK_FRONT);
        Minecraft.getInstance().getTextureManager().bindForSetup(SHARP_ROCK_TEXTURE);
        Minecraft.getInstance().getTextureManager().bindForSetup(CARVED_STICK_TEXTURE);

        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 64, 64);

        renderItems(pGuiGraphics);

        if (rockSettled && draggingString) {
            renderWinding(pGuiGraphics, pMouseX, pMouseY);
        }

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    private void renderItems(GuiGraphics pGuiGraphics) {
        // Render Carved Stick as 2D texture (back)
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(this.stickPosX, this.stickPosY, 0);  // Translate to the stick position
        pGuiGraphics.pose().rotateAround(new Quaternionf().rotateZ((float) Math.toRadians(-45)), 0, 0, 0);  // Apply rotation

        // Render the carved stick texture
        pGuiGraphics.blit(CARVED_STICK_TEXTURE, 0, 0, 0, 0, 16, 16, 16, 16);  // Stick

        pGuiGraphics.pose().popPose();

        // Render Sharp Rock (if not settled)
        if (!rockSettled) {
            pGuiGraphics.blit(SHARP_ROCK_TEXTURE, this.rockPosX, this.rockPosY, 0, 0, 16, 16, 16, 16);  // Sharp rock
        } else {
            // Adjust the rock position when it is settled
            this.rockPosX = this.stickPosX + 3;
            this.rockPosY = this.stickPosY - 12;

            pGuiGraphics.pose().pushPose();
            pGuiGraphics.blit(SHARP_ROCK_TEXTURE, this.rockPosX, this.rockPosY, 0, 0, 16, 16, 16, 16);
            pGuiGraphics.pose().popPose();


            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate(this.stickPosX, this.stickPosY, 0);  // Translate to the stick position
            pGuiGraphics.pose().rotateAround(new Quaternionf().rotateZ((float) Math.toRadians(-45)), 0, 0, 0);  // Apply rotation

            pGuiGraphics.blit(CARVED_STICK_FRONT, 0, 0, 0, 0, 16, 16, 16, 16);
            pGuiGraphics.pose().popPose();

            // Render Plant String after everything else
            pGuiGraphics.renderItem(new ItemStack(ModItems.PLANT_STRING.get()), this.stringPosX, this.stringPosY);
        }
    }



    private void renderWinding(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        if (draggingString) {
            trackMouseForWinding(pMouseX, pMouseY);
        }
    }

    private void trackMouseForWinding(int pMouseX, int pMouseY) {
        double angle = Math.atan2(pMouseY - stickPosY, pMouseX - stickPosX);
        double prevAngle = Math.atan2(lastMouseY - stickPosY, lastMouseX - stickPosX);

        // Check if the mouse has moved enough to count as a "loop"
        if (Math.abs(angle - prevAngle) > Math.PI / 2) {
            windLoops++;
        }

        lastMouseX = pMouseX;
        lastMouseY = pMouseY;

        // Complete winding when loops are finished
        if (windLoops >= 3) {
            completeWinding();
        }
    }

    private void startWinding(int mouseX, int mouseY) {
        this.draggingString = true;
        this.windLoops = 0;
        this.lastMouseX = mouseX;
        this.lastMouseY = mouseY;
    }

    private void completeWinding() {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            // Send the ActionPacket to the server
            player.playSound(SoundEvents.PLAYER_LEVELUP, 1.0F, 1.0F);
            ModPacketHandler.sendToServer(new ActionPacket(PlayerAction.PROCESS_COMPLETE));
        }
        Minecraft.getInstance().setScreen(null); // Close the screen after completion
    }

    // Input handling for dragging and clicking
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (rockSettled && draggingString) {
            this.stringPosX = (int) mouseX - 8;
            this.stringPosY = (int) mouseY - 8;
            trackMouseForWinding((int) mouseX, (int) mouseY); // Track the dragging to simulate winding
            return true;
        }

        if (draggingRock && !rockSettled) {
            updateRockPosition(mouseX, mouseY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOverRock(mouseX, mouseY) && !rockSettled) {
            draggingRock = true;
            return true;
        }
        if (isMouseOverString(mouseX, mouseY) && rockSettled) {
            startWinding((int) mouseX, (int) mouseY);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (draggingRock) {
            draggingRock = false;
            return true;
        }
        if (draggingString) {
            draggingString = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private void updateRockPosition(double mouseX, double mouseY) {
        this.rockPosX = (int) mouseX - 8;
        this.rockPosY = (int) mouseY - 8;

        if (isNearCarvedStick()) {
            rockSettled = true;
            playDingSound();
        }
    }

    private boolean isMouseOverRock(double mouseX, double mouseY) {
        return mouseX >= this.rockPosX && mouseX <= this.rockPosX + 16 && mouseY >= this.rockPosY && mouseY <= this.rockPosY + 16;
    }

    private boolean isMouseOverString(double mouseX, double mouseY) {
        return mouseX >= this.stringPosX && mouseX <= this.stringPosX + 16 && mouseY >= this.stringPosY && mouseY <= this.stringPosY + 16;
    }

    private boolean isNearCarvedStick() {
        return Math.abs(rockPosX - stickPosX) < 10 && Math.abs(rockPosY - stickPosY) < 10;
    }

    private void playDingSound() {
        Minecraft.getInstance().player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
