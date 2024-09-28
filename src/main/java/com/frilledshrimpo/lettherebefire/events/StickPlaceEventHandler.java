package com.frilledshrimpo.lettherebefire.events;

import com.frilledshrimpo.lettherebefire.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
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
        BlockState targetBlockState = level.getBlockState(pos);

        // Check if the player is holding a stick
        if (player.getMainHandItem().getItem() == Items.STICK) {
            // If the player is right-clicking on a STICK_GND block, handle the interaction by forwarding to the block's use method
            if (targetBlockState.is(ModBlocks.STICK_GND.get())) {
                InteractionResult result = targetBlockState.use(level, player, event.getHand(), new BlockHitResult(event.getHitVec().getLocation(), event.getFace(), pos, false));

                if (result == InteractionResult.SUCCESS) {
                    event.setCanceled(true); // Cancel the event if successfully handled by the block's use method
                    event.setCancellationResult(InteractionResult.SUCCESS);
                }
            } else {
                // If clicking on an empty space, try to place the block
                BlockPos placePos = pos.relative(event.getFace());
                if (level.getBlockState(placePos).isAir() && ModBlocks.STICK_GND.get().defaultBlockState().canSurvive(level, placePos)) {
                    level.setBlock(placePos, ModBlocks.STICK_GND.get().defaultBlockState(), 3);

                    // Shrink the stack if the player is not in creative mode
                    if (!player.isCreative()) {
                        player.getMainHandItem().shrink(1);
                    }

                    event.setCanceled(true); // Cancel the event to prevent default behavior
                    event.setCancellationResult(InteractionResult.SUCCESS); // Mark as successful
                }
            }
        }
    }
}
