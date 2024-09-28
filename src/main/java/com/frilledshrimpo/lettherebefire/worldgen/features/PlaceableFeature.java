package com.frilledshrimpo.lettherebefire.worldgen.features;

import com.frilledshrimpo.lettherebefire.blocks.abstracts.GroundStuffBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.material.Fluids;

public class PlaceableFeature extends Feature<SimpleBlockConfiguration> {

    public PlaceableFeature() {
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
        state = state.setValue(GroundStuffBlock.ITEM_COUNT, stickCount);

        if (state.canSurvive(context.level(), position)) {
            return context.level().setBlock(position, state, 2);
        }

        return false;
    }
}
