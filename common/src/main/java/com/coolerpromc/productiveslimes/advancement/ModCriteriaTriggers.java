package com.coolerpromc.productiveslimes.advancement;

import com.coolerpromc.productiveslimes.platform.Services;
import com.coolerpromc.productiveslimes.platform.util.RegistryHandler;
import net.minecraft.advancements.CriterionTrigger;

public class ModCriteriaTriggers {
    public static final RegistryHandler<CriterionTrigger<?>, SlimeTrigger> CAPTURE_SLIME = Services.REGISTRY.registerCriterionTrigger("capture_slime", SlimeTrigger::new);
    public static final RegistryHandler<CriterionTrigger<?>, SlimeTrigger> BREED_SLIME = Services.REGISTRY.registerCriterionTrigger("breed_slime", SlimeTrigger::new);

    public static void load() {
    }
}
