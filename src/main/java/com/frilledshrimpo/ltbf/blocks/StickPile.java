package com.frilledshrimpo.ltbf.blocks;


import net.minecraft.core.BlockPos;

import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;


public class StickPile extends Block {

    private static final VoxelShape SHAPE_1 = Block.box(7, 0, 7, 9, 4, 9); // 1 stick
    private static final VoxelShape SHAPE_2 = Block.box(6, 0, 6, 10, 4, 10); // 2 sticks
    private static final VoxelShape SHAPE_3 = Block.box(5, 0, 5, 11, 5, 11); // 3 sticks
    private static final VoxelShape SHAPE_4 = Block.box(4, 0, 4, 12, 5, 12); // 4 sticks


    public static final IntegerProperty STICKCOUNT = IntegerProperty.create("stickcount",1,4);

    public StickPile(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STICKCOUNT);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        int sticks = state.getValue(STICKCOUNT);
        switch (sticks) {
            case 2:
                return SHAPE_2;
            case 3:
                return SHAPE_3;
            case 4:
                return SHAPE_4;
            default:
                return SHAPE_1;
        }

    }
}
