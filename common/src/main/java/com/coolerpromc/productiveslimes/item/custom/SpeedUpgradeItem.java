package com.coolerpromc.productiveslimes.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class SpeedUpgradeItem extends Item {
    private final float timeMultiplier;

    public SpeedUpgradeItem(float timeMultiplier, Properties properties) {
        super(properties);
        this.timeMultiplier = timeMultiplier;
    }

    public float getTimeMultiplier() {
        return this.timeMultiplier;
    }

    public static float timeMultiplier(Container upgrades) {
        float multiplier = 1.0F;
        for (int i = 0; i < upgrades.getContainerSize(); i++) {
            if (upgrades.getItem(i).getItem() instanceof SpeedUpgradeItem upgrade) {
                multiplier *= upgrade.getTimeMultiplier();
            }
        }
        return multiplier;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        int percent = Math.round((1.0F / this.timeMultiplier - 1.0F) * 100.0F);
        builder.accept(Component.translatable("tooltip.productiveslimes.speed_upgrade", percent).withStyle(ChatFormatting.GRAY));
    }
}
