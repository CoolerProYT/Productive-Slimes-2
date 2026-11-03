package com.coolerpromc.productiveslimes.mixin;

import com.coolerpromc.productiveslimes.worldgen.SlimyLandPlacement;
import net.minecraft.core.RegistrySetBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RegistrySetBuilder.class)
public class RegistrySetBuilderMixin {
    @Inject(method = {"build", "buildPatch"}, at = @At("HEAD"), require = 0)
    private void productiveslimes$startBootstrap(CallbackInfoReturnable<?> cir) {
        SlimyLandPlacement.BOOTSTRAPPING.set(true);
    }

    @Inject(method = {"build", "buildPatch"}, at = @At("RETURN"), require = 0)
    private void productiveslimes$endBootstrap(CallbackInfoReturnable<?> cir) {
        SlimyLandPlacement.BOOTSTRAPPING.set(false);
    }
}
