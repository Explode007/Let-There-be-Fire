package com.frilledshrimpo.lettherebefire.events;

import com.frilledshrimpo.lettherebefire.blocks.ModBlocks;
import com.frilledshrimpo.lettherebefire.item.ModItems;
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
public class LooseRockPlaceEventHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState targetBlockState = level.getBlockState(pos);


        if (player.getMainHandItem().getItem() == ModItems.LOOSE_ROCK.get()) {

            if (targetBlockState.is(ModBlocks.ROCK_GND.get())) {
                InteractionResult result = targetBlockState.use(level, player, event.getHand(), new BlockHitResult(event.getHitVec().getLocation(), event.getFace(), pos, false));

                if (result == InteractionResult.SUCCESS) {
                    event.setCanceled(true);
                    event.setCancellationResult(InteractionResult.SUCCESS);
                }
            } else {

                BlockPos placePos = pos.relative(event.getFace());
                if (level.getBlockState(placePos).isAir() && ModBlocks.ROCK_GND.get().defaultBlockState().canSurvive(level, placePos)) {
                    level.setBlock(placePos, ModBlocks.ROCK_GND.get().defaultBlockState(), 3);


                    if (!player.isCreative()) {
                        player.getMainHandItem().shrink(1);
                    }

                    event.setCanceled(true);
                    event.setCancellationResult(InteractionResult.SUCCESS);
                }
            }
        }
    }
}
