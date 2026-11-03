package com.coolerpromc.productiveslimes.worldgen;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class SlimyLandSurface {
    private static SurfaceRules.RuleSource rule;

    public static synchronized SurfaceRules.RuleSource rule() {
        if (rule == null) {
            rule = SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.SLIMY_LAND), SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), SurfaceRules.state(ModBlocks.SLIMY_GRASS_BLOCK.get().defaultBlockState()))),
                    SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.state(ModBlocks.SLIMY_DIRT.get().defaultBlockState())),
                    SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, SurfaceRules.state(ModBlocks.SLIMY_STONE.get().defaultBlockState())))));
        }
        return rule;
    }

    public static void load() {}
}
