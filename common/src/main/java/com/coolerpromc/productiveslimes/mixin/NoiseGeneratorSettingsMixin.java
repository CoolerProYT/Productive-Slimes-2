package com.coolerpromc.productiveslimes.mixin;

import com.coolerpromc.productiveslimes.worldgen.SlimyLandSurface;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.material.MaterialRules;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseGeneratorSettings.class)
public class NoiseGeneratorSettingsMixin {
    @Unique
    private static final ResourceKey<MaterialRule> productiveslimes$OVERWORLD = ResourceKey.create(Registries.MATERIAL_RULE, Identifier.withDefaultNamespace("overworld"));

    @Unique
    private Holder<MaterialRule> productiveslimes$wrapped;

    @Inject(method = "materialRule", at = @At("RETURN"), cancellable = true)
    private void productiveslimes$wrapMaterialRule(CallbackInfoReturnable<Holder<MaterialRule>> cir) {
        Holder<MaterialRule> original = cir.getReturnValue();
        if (!original.is(productiveslimes$OVERWORLD)) {
            return;
        }

        if (this.productiveslimes$wrapped == null) {
            this.productiveslimes$wrapped = Holder.direct(MaterialRules.sequence(SlimyLandSurface.rule(), new MaterialRule.HolderHolder(original)));
        }
        cir.setReturnValue(this.productiveslimes$wrapped);
    }
}
