package com.coolerpromc.productiveslimes.worldgen;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.material.MaterialRuleContext;
import net.minecraft.world.level.levelgen.material.condition.ConditionEvaluator;
import net.minecraft.world.level.levelgen.material.condition.MaterialCondition;

import java.util.Set;

public enum SlimyLandCondition implements MaterialCondition {
    INSTANCE;

    public static final MapCodec<SlimyLandCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public ConditionEvaluator compile(MaterialRuleContext context) {
        Set<Holder<Biome>> possibleBiomes = context.possibleBiomes();
        if (possibleBiomes != null && possibleBiomes.stream().noneMatch(biome -> biome.is(ModBiomes.SLIMY_LAND))) {
            return () -> false;
        }

        return new MaterialRuleContext.LazyYCondition(context) {
            @Override
            protected boolean compute() {
                return this.context.getBiome().is(ModBiomes.SLIMY_LAND);
            }
        };
    }

    @Override
    public MapCodec<? extends MaterialCondition> codec() {
        return CODEC;
    }
}
