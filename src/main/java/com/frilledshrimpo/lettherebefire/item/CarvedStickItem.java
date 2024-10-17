package com.frilledshrimpo.lettherebefire.item;

import com.frilledshrimpo.lettherebefire.gui.CraftStoneAxeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CarvedStickItem extends Item {

    public CarvedStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.lettherebefire.carved_stick.tooltip"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        Optional<ItemStack> leastDurabilitySharpRock = player.getInventory().items.stream()
                .filter(stack -> stack.getItem() == ModItems.SHARP_ROCK.get())
                .min(Comparator.comparingInt(ItemStack::getDamageValue));

        boolean hasSharpRock = leastDurabilitySharpRock.isPresent();
        boolean hasPlantString = player.getInventory().contains(new ItemStack(ModItems.PLANT_STRING.get()));

        if (world.isClientSide && hasSharpRock && hasPlantString) {
            Minecraft.getInstance().setScreen(new CraftStoneAxeScreen());
        }else {
            if (!hasSharpRock && !hasPlantString) {
                player.displayClientMessage(Component.translatable("message.missing_both_items"), true);
            } else if (!hasSharpRock) {
                player.displayClientMessage(Component.translatable("message.missing_sharp_rock"), true);
            } else if (!hasPlantString) {
                player.displayClientMessage(Component.translatable("message.missing_plant_string"), true);
            }
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide());
    }
}
