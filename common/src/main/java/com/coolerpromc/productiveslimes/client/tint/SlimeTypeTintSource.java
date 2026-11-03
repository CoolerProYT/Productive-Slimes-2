package com.coolerpromc.productiveslimes.client.tint;

import com.coolerpromc.productiveslimes.data.component.ModDataComponents;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record SlimeTypeTintSource(int defaultColor) implements ItemTintSource {
    public static final MapCodec<SlimeTypeTintSource> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.INT.optionalFieldOf("default", 0xFFFFFFFF).forGetter(SlimeTypeTintSource::defaultColor)
    ).apply(i, SlimeTypeTintSource::new));

    @Override
    public int calculate(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        Holder<SlimeType> type = itemStack.get(ModDataComponents.SLIME_TYPE.get());
        if (type != null) {
            return ARGB.opaque(type.value().color());
        }
        return this.defaultColor;
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return MAP_CODEC;
    }
}
