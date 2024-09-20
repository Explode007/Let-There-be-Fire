package com.frilledshrimpo.lettherebefire.worldgen;

import com.frilledshrimpo.lettherebefire.blocks.StickGndBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.material.Fluids;

public class StickGndFeature extends Feature<SimpleBlockConfiguration> {

    public StickGndFeature() {
        super(SimpleBlockConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        RandomSource random = context.random();
        BlockPos position = context.origin();
        BlockState state = context.config().toPlace().getState(random, position);

        if(context.level().getFluidState(position).getType() != Fluids.EMPTY) {
            return false;
        }

        // Randomize the stick count between 1 and 4
        int stickCount = random.nextInt(4) + 1;
        state = state.setValue(StickGndBlock.STICK_COUNT, stickCount);

        if (state.canSurvive(context.level(), position)) {
            return context.level().setBlock(position, state, 2);
        }

        return false;
    }
}
