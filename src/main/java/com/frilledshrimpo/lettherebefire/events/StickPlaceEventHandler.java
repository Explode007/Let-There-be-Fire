package com.frilledshrimpo.lettherebefire.events;

import com.frilledshrimpo.lettherebefire.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "lettherebefire", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StickPlaceEventHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();

        // Check if the player is sneaking (shift key) and holding a stick
        if (player.isShiftKeyDown() && player.getMainHandItem().getItem() == Items.STICK) {
            BlockPos placePos = pos.relative(event.getFace());

            // Ensure the position is air before placing the block
            if (level.getBlockState(placePos).isAir()) {
                if (ModBlocks.STICK_GND.get().defaultBlockState().canSurvive(level, placePos)) {
                    if (ModBlocks.STICK_GND.get().defaultBlockState().canSurvive(level, placePos)) {
                        level.setBlock(placePos, ModBlocks.STICK_GND.get().defaultBlockState(), 3);

                        // Shrink the stack if the player is not in creative mode
                        if (!player.isCreative()) {
                            player.getMainHandItem().shrink(1);
                        }

                        event.setCanceled(true); // Cancel the event to prevent default behavior
                        event.setCancellationResult(InteractionResult.SUCCESS); // Mark as successful
                    }
                } else {
                    event.setCancellationResult(InteractionResult.FAIL);
                }

            }
        }
    }
}
