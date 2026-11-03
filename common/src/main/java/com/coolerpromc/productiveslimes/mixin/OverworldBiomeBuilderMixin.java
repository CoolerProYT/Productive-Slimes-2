package com.coolerpromc.productiveslimes.mixin;

import com.coolerpromc.productiveslimes.worldgen.SlimyLandPlacement;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public class OverworldBiomeBuilderMixin {
    @ModifyVariable(method = "addBiomes", at = @At("HEAD"), argsOnly = true)
    private Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> productiveslimes$injectSlimyLand(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes) {
        return pair -> biomes.accept(SlimyLandPlacement.apply(pair));
    }
}
