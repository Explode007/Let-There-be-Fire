package com.frilledshrimpo.lettherebefire.network;

import com.frilledshrimpo.lettherebefire.item.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Comparator;

public enum PlayerAction {
    PROCESS_COMPLETE(1) {
        @Override
        public void execute(Player player) {
            // Process the completion action
            player.getInventory().items.stream()
                    .filter(itemStack -> itemStack.getItem() == ModItems.SHARP_ROCK.get())
                    .max(Comparator.comparingInt(ItemStack::getDamageValue))
                    .ifPresent(sharpRockToConsume -> {
                        int maxDurability = sharpRockToConsume.getMaxDamage();
                        int currentDurability = sharpRockToConsume.getDamageValue();
                        float durabilityPercentage = 1.0f - ((float) currentDurability / (float) maxDurability);

                        sharpRockToConsume.shrink(1);

                        ItemStack stoneAxe = new ItemStack(Items.STONE_AXE);
                        int axeDurability = Math.round(stoneAxe.getMaxDamage() * durabilityPercentage);
                        stoneAxe.setDamageValue(stoneAxe.getMaxDamage() - axeDurability);

                        player.getInventory().add(stoneAxe);
                    });

            player.getInventory().items.stream()
                    .filter(itemStack -> itemStack.getItem() == ModItems.CARVED_STICK.get() ||
                            itemStack.getItem() == ModItems.PLANT_STRING.get())
                    .forEach(itemStack -> itemStack.shrink(1));
        }
    };

    private final int id;

    PlayerAction(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PlayerAction fromId(int id) {
        for (PlayerAction type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null; // Return null if action ID not found
    }

    public abstract void execute(Player player);
}
