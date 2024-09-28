package com.frilledshrimpo.lettherebefire.blocks.abstracts;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import net.minecraft.world.level.block.state.properties.IntegerProperty;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;


public abstract class GroundStuffBlock extends Block {
    public static final IntegerProperty ITEM_COUNT = IntegerProperty.create("count",1, 4);
    private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D);


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public GroundStuffBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(ITEM_COUNT, 1));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ITEM_COUNT);
    }


    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (pLevel.getBlockState(pPos.below()).getBlock() instanceof GroundStuffBlock) {
            return false;
        }

        return pLevel.getBlockState(pPos.below()).isFaceSturdy(pLevel, pPos.below(), Direction.UP);
    }

    protected abstract boolean isValidItem(Item item);
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {


        if (pState.getValue(ITEM_COUNT) < 4 && isValidItem(pPlayer.getItemInHand(pHand).getItem())) {
            pLevel.setBlock(pPos, pState.setValue(ITEM_COUNT, pState.getValue(ITEM_COUNT) + 1), 3);
            if(!pPlayer.isCreative()) {
                pPlayer.getItemInHand(pHand).shrink(1);
            } 
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
