package com.coolerpromc.productiveslimes.item.custom;

import com.coolerpromc.productiveslimes.data.component.ModDataComponents;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlimeballItem extends Item {
    public SlimeballItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack itemStack) {
        Holder<SlimeType> type = itemStack.get(ModDataComponents.SLIME_TYPE.get());
        if (type != null) {
            return Component.translatable("item.productiveslimes.slimeball.typed", SlimeType.name(type));
        }
        return super.getName(itemStack);
    }
}
