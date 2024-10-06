package com.frilledshrimpo.lettherebefire.item;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.InteractionHand;

import net.minecraft.world.level.Level;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;

public class LooseRockItem extends Item {

    private int clickCounter = 0;

    public LooseRockItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (isHoldingLooseRock(player)) {
            clickCounter++;
            player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 0.3f, 1f);
            for (int i = 0; i < 5; i++) {
                world.addParticle(
                        new BlockParticleOption(ParticleTypes.BLOCK, Blocks.STONE.defaultBlockState()),
                        player.getX() + (world.getRandom().nextDouble() - 0.5),
                        player.getY() + 1.0D,
                        player.getZ() + (world.getRandom().nextDouble() - 0.5),
                        0.0D, 0.0D, 0.0D
                );
            }

            if (clickCounter >= 2 && clickCounter <= 4) {
                RandomSource random = world.getRandom();
                boolean success = random.nextFloat() < 0.40f; // 40% chance to succeed

                consumeLooseRock(player);


                if (success) {
                    // Play success sound
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.STONE_BREAK, SoundSource.PLAYERS, 10.0f, 1.0f);
                    player.addItem(new ItemStack(ModItems.SHARP_ROCK.get()));
                    clickCounter = 0;
                    return InteractionResultHolder.success(itemStack);
                }

                clickCounter = 0;
                return InteractionResultHolder.success(itemStack);
            }

            if (clickCounter > 4) {
                clickCounter = 0;
            }

            return InteractionResultHolder.success(itemStack);
        }

        return InteractionResultHolder.fail(itemStack);
    }

    private boolean isHoldingLooseRock(Player player) {
        // Check if both main hand and offhand are holding Loose Rock
        ItemStack mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHandItem = player.getItemInHand(InteractionHand.OFF_HAND);
        return mainHandItem.getItem() instanceof LooseRockItem && offHandItem.getItem() instanceof LooseRockItem;
    }

    private void consumeLooseRock(Player player) {
        ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHandStack = player.getItemInHand(InteractionHand.OFF_HAND);

        // Check if the offhand has more than 1 rock, consume from offhand first
        if (offHandStack.getItem() instanceof LooseRockItem && offHandStack.getCount() > 1) {
            offHandStack.shrink(1);
        }
        // If only 1 rock in offhand and main hand has more than 1, consume from main hand
        else if (offHandStack.getItem() instanceof LooseRockItem && offHandStack.getCount() == 1 && mainHandStack.getCount() > 1) {
            mainHandStack.shrink(1);
        }
        // If 1 rock in each hand, consume from the main hand
        else if (mainHandStack.getItem() instanceof LooseRockItem && mainHandStack.getCount() > 0) {
            mainHandStack.shrink(1);
        }
    }
}

