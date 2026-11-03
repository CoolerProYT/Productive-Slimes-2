package com.coolerpromc.productiveslimes.tag;

import com.coolerpromc.productiveslimes.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

public class ModTags {
    public static final TagKey<Item> SLIMY_LOGS = TagKey.create(Registries.ITEM, Constants.id("slimy_logs"));
    public static final TagKey<Biome> HAS_SLIME_SHRINE = TagKey.create(Registries.BIOME, Constants.id("has_structure/slime_shrine"));
}
