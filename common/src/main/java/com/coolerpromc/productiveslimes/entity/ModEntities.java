package com.coolerpromc.productiveslimes.entity;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.entity.custom.ProductiveSlime;
import com.coolerpromc.productiveslimes.platform.Services;
import com.coolerpromc.productiveslimes.platform.util.RegistryHandler;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
    public static final RegistryHandler.Entities<ProductiveSlime> PRODUCTIVE_SLIME = Services.REGISTRY.registerEntity("productive_slime", ProductiveSlime::new, MobCategory.CREATURE, b -> b.sized(0.52F, 0.52F).eyeHeight(0.325F).clientTrackingRange(10).noLootTable());

    public static void load(){
        Constants.LOG.info("Registering entities.");
    }
}
