package com.coolerpromc.productiveslimes.worldgen;

import com.coolerpromc.productiveslimes.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SLIMY_TREE = feature("slimy_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SLIME_SPROUTS = feature("slime_sprouts");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SLIME_BOULDER = feature("slime_boulder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SLIMY_STONE_BLOB = feature("slimy_stone_blob");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SLIME_CRYSTAL_ORE = feature("slime_crystal_ore");

    public static final ResourceKey<PlacedFeature> SLIMY_TREE_PLACED = placed("slimy_tree");
    public static final ResourceKey<PlacedFeature> SLIME_SPROUTS_PLACED = placed("slime_sprouts");
    public static final ResourceKey<PlacedFeature> SLIME_BOULDER_PLACED = placed("slime_boulder");
    public static final ResourceKey<PlacedFeature> SLIMY_STONE_BLOB_PLACED = placed("slimy_stone_blob");
    public static final ResourceKey<PlacedFeature> SLIME_CRYSTAL_ORE_PLACED = placed("slime_crystal_ore");

    private static ResourceKey<ConfiguredFeature<?, ?>> feature(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Constants.id(name));
    }

    private static ResourceKey<PlacedFeature> placed(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Constants.id(name));
    }
}
