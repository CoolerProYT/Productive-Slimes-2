package com.coolerpromc.productiveslimes.worldgen;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class SlimyLandSurface {
    public static volatile HolderGetter<Biome> biomes;

    private static HolderGetter<Biome> builtFor;
    private static SurfaceRules.RuleSource rule;

    public static synchronized SurfaceRules.RuleSource rule(HolderGetter<Biome> biomes) {
        if (rule == null || builtFor != biomes) {
            rule = SurfaceRules.ifTrue(SurfaceRules.isBiome(biomes, ModBiomes.SLIMY_LAND), SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), SurfaceRules.state(ModBlocks.SLIMY_GRASS_BLOCK.get().defaultBlockState()))),
                    SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.state(ModBlocks.SLIMY_DIRT.get().defaultBlockState())),
                    SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, SurfaceRules.state(ModBlocks.SLIMY_STONE.get().defaultBlockState()))
            )));
            builtFor = biomes;
        }
        return rule;
    }

    public static void load() {
    }
}
