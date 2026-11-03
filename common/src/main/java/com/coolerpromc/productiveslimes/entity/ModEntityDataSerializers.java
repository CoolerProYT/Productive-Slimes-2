package com.coolerpromc.productiveslimes.entity;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.platform.Services;
import com.coolerpromc.productiveslimes.platform.util.RegistryHandler;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;

public class ModEntityDataSerializers {
    public static final RegistryHandler<EntityDataSerializer<?>, EntityDataSerializer<Holder<SlimeType>>> SLIME_TYPE = Services.REGISTRY.registerEntityDataSerializer("slime_type", SlimeType.STREAM_CODEC);

    public static void load(){
        Constants.LOG.info("Registering entity data serializers.");
    }
}
