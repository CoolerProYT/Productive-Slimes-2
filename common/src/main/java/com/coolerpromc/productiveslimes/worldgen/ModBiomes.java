package com.coolerpromc.productiveslimes.worldgen;

import com.coolerpromc.productiveslimes.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class ModBiomes {
    public static final ResourceKey<Biome> SLIMY_LAND = ResourceKey.create(Registries.BIOME, Constants.id("slimy_land"));
}
