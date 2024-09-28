package com.frilledshrimpo.lettherebefire.blocks;

import com.frilledshrimpo.lettherebefire.blocks.abstracts.GroundStuffBlock;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;

public class StickGroundBlock extends GroundStuffBlock {

    public StickGroundBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isValidItem(Item item) {
        // Only allow sticks
        return item == Items.STICK;
    }
}
