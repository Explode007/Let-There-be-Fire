package com.frilledshrimpo.lettherebefire.blocks;

import com.frilledshrimpo.lettherebefire.blocks.abstracts.GroundStuffBlock;
import com.frilledshrimpo.lettherebefire.item.ModItems;
import net.minecraft.world.item.Item;

public class RockGroundBlock extends GroundStuffBlock {

    public RockGroundBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isValidItem(Item item) {
        // Only allow loose rocks
        return item == ModItems.LOOSE_ROCK.get();
    }
}
