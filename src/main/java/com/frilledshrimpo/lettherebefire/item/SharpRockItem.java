package com.frilledshrimpo.lettherebefire.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;


public class SharpRockItem extends Item {

    private int clickCounter = 0;

    public SharpRockItem(Properties properties) {
        super(properties.durability(48));
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pMiningEntity) {
        if (pState.is(Blocks.GRASS) || pState.is(Blocks.TALL_GRASS)) {
            pStack.hurtAndBreak(1, pMiningEntity, (e) -> e.broadcastBreakEvent(pMiningEntity.getUsedItemHand()));
        }
        return super.mineBlock(pStack, pLevel, pState, pPos, pMiningEntity);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState blockState) {
        return blockState.is(Blocks.GRASS) || blockState.is(Blocks.TALL_GRASS);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand usedHand) {
        // Get items in both hands
        ItemStack mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHandItem = player.getItemInHand(InteractionHand.OFF_HAND);

        // Check if the player has a sharp rock and a stick in either hand
        if ((mainHandItem.getItem() instanceof SharpRockItem && offHandItem.getItem() == Items.STICK) ||
                (mainHandItem.getItem() == Items.STICK && offHandItem.getItem() instanceof SharpRockItem)) {

            if (!world.isClientSide) {
                clickCounter++;

                // Play sound on each click
                player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);

                if (clickCounter >= 3) {
                    // Reset the counter
                    clickCounter = 0;

                    // Consume a stick
                    if (mainHandItem.getItem() == Items.STICK) {
                        mainHandItem.shrink(1);
                    } else if (offHandItem.getItem() == Items.STICK) {
                        offHandItem.shrink(1);
                    }

                    // Give the player a carved stick
                    player.addItem(new ItemStack(ModItems.CARVED_STICK.get()));

                    // Play crafting sound
                    player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.0F);
                }
            }

            // Return success and the itemstack
            return InteractionResultHolder.success(mainHandItem);
        }

        // If the conditions are not met, return pass to allow other interactions
        return InteractionResultHolder.pass(mainHandItem);
    }

}
