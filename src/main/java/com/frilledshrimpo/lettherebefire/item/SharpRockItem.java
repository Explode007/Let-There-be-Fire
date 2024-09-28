package com.frilledshrimpo.lettherebefire.item;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;


public class SharpRockItem extends Item {

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
}
