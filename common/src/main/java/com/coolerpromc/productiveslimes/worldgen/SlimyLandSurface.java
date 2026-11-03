package com.coolerpromc.productiveslimes.worldgen;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.platform.Services;
import com.coolerpromc.productiveslimes.platform.util.RegistryHandler;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.material.MaterialRules;
import net.minecraft.world.level.levelgen.material.condition.MaterialCondition;
import net.minecraft.world.level.levelgen.material.condition.StoneDepthCondition;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class SlimyLandSurface {
    public static final RegistryHandler<MapCodec<? extends MaterialCondition>, MapCodec<SlimyLandCondition>> SLIMY_LAND_CONDITION = Services.REGISTRY.register(Registries.MATERIAL_CONDITION_TYPE, "slimy_land", () -> SlimyLandCondition.CODEC);

    private static MaterialRule rule;

    public static MaterialRule rule() {
        if (rule == null) {
            MaterialCondition onFloor = new StoneDepthCondition(0, false, 0, CaveSurface.FLOOR);
            MaterialCondition underFloor = new StoneDepthCondition(0, true, 0, CaveSurface.FLOOR);
            MaterialCondition deepUnderFloor = new StoneDepthCondition(0, true, 6, CaveSurface.FLOOR);
            MaterialCondition aboveWater = MaterialRules.waterBlockCheck(-1, 0);

            rule = MaterialRules.ifTrue(SlimyLandCondition.INSTANCE, MaterialRules.ifTrue(MaterialRules.abovePreliminarySurface(), MaterialRules.sequence(
                    MaterialRules.ifTrue(onFloor, MaterialRules.ifTrue(aboveWater, MaterialRules.state(ModBlocks.SLIMY_GRASS_BLOCK.get().defaultBlockState()))),
                    MaterialRules.ifTrue(underFloor, MaterialRules.state(ModBlocks.SLIMY_DIRT.get().defaultBlockState())),
                    MaterialRules.ifTrue(deepUnderFloor, MaterialRules.state(ModBlocks.SLIMY_STONE.get().defaultBlockState()))
            )));
        }
        return rule;
    }

    public static void load() {
    }
}
