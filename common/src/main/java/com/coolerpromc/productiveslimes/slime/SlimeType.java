package com.coolerpromc.productiveslimes.slime;

import com.coolerpromc.productiveslimes.Constants;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Registry;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.Optional;

public record SlimeType(int color, HolderSet<Item> food, ItemStackTemplate squeezeResult, int productionTime, int tier, Optional<Breeding> breeding, boolean naturalSpawn) {
    public static final ResourceKey<Registry<SlimeType>> REGISTRY_KEY = ResourceKey.createRegistryKey(Constants.id("slime_type"));

    public static final Codec<Integer> COLOR_CODEC = Codec.withAlternative(Codec.STRING.comapFlatMap(SlimeType::parseHex, color -> String.format("#%06X", color & 0xFFFFFF)), Codec.INT);

    public static final Codec<SlimeType> DIRECT_CODEC = RecordCodecBuilder.create(i -> i.group(
            COLOR_CODEC.fieldOf("color").forGetter(SlimeType::color),
            RegistryCodecs.holderSet(Registries.ITEM).fieldOf("food").forGetter(SlimeType::food),
            ItemStackTemplate.CODEC.fieldOf("squeeze_result").forGetter(SlimeType::squeezeResult),
            ExtraCodecs.POSITIVE_INT.fieldOf("production_time").forGetter(SlimeType::productionTime),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("tier", 0).forGetter(SlimeType::tier),
            Breeding.CODEC.optionalFieldOf("breeding").forGetter(SlimeType::breeding),
            Codec.BOOL.optionalFieldOf("natural_spawn", false).forGetter(SlimeType::naturalSpawn)
    ).apply(i, SlimeType::new));

    public static final Codec<Holder<SlimeType>> CODEC = RegistryCodecs.holder(REGISTRY_KEY);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SlimeType>> STREAM_CODEC = ByteBufCodecs.holderRegistry(REGISTRY_KEY);

    public boolean isFood(ItemStack stack) {
        return stack.is(this.food);
    }

    public ItemStack createSqueezeResult() {
        return this.squeezeResult.create();
    }

    public static Identifier idOf(Holder<SlimeType> type) {
        return type.unwrapKey().map(ResourceKey::identifier).orElse(Constants.id("unknown"));
    }

    public static String translationKey(Holder<SlimeType> type) {
        Identifier id = idOf(type);
        return "slime_type." + id.getNamespace() + "." + id.getPath();
    }

    public static Component name(Holder<SlimeType> type) {
        return Component.translatable(translationKey(type));
    }

    private static DataResult<Integer> parseHex(String value) {
        String hex = value.startsWith("#") ? value.substring(1) : value;
        try {
            return DataResult.success(Integer.parseUnsignedInt(hex, 16) & 0xFFFFFF);
        } catch (NumberFormatException e) {
            return DataResult.error(() -> "Invalid hex color: " + value);
        }
    }
}
