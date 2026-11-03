package com.coolerpromc.productiveslimes.worldgen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;

public class SlimyLandPlacement {
    private static final long TEMPERATURE_MIN = Climate.quantizeCoord(-0.15F);
    private static final long TEMPERATURE_MAX = Climate.quantizeCoord(0.2F);
    private static final long HUMIDITY_MIN = Climate.quantizeCoord(-0.1F);
    private static final long HUMIDITY_MAX = Climate.quantizeCoord(0.1F);
    private static final boolean DISABLED = Boolean.getBoolean("productiveslimes.datagen");
    public static final ThreadLocal<Boolean> BOOTSTRAPPING = ThreadLocal.withInitial(() -> false);

    public static Pair<Climate.ParameterPoint, ResourceKey<Biome>> apply(Pair<Climate.ParameterPoint, ResourceKey<Biome>> pair) {
        if (DISABLED || BOOTSTRAPPING.get() || pair.getSecond() != Biomes.FOREST) {
            return pair;
        }
        Climate.ParameterPoint point = pair.getFirst();
        if (within(point.temperature(), TEMPERATURE_MIN, TEMPERATURE_MAX) && within(point.humidity(), HUMIDITY_MIN, HUMIDITY_MAX)) {
            return Pair.of(point, ModBiomes.SLIMY_LAND);
        }
        return pair;
    }

    private static boolean within(Climate.Parameter parameter, long min, long max) {
        return parameter.min() >= min && parameter.max() <= max;
    }
}
