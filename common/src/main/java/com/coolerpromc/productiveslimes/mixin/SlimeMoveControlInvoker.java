package com.coolerpromc.productiveslimes.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(targets = "net.minecraft.world.entity.monster.Slime$SlimeMoveControl")
public interface SlimeMoveControlInvoker {
    @Invoker("setDirection")
    void productiveslimes$setDirection(float yRot, boolean isAggressive);

    @Invoker("setWantedMovement")
    void productiveslimes$setWantedMovement(double speedModifier);
}
