package com.coolerpromc.productiveslimes.data.component;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.platform.Services;
import com.coolerpromc.productiveslimes.platform.util.RegistryHandler;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;

public class ModDataComponents {
    public static final RegistryHandler.Components<Holder<SlimeType>> SLIME_TYPE = Services.REGISTRY.registerDataComponent("slime_type", b -> b.persistent(SlimeType.CODEC).networkSynchronized(SlimeType.STREAM_CODEC).cacheEncoding());
    public static final RegistryHandler.Components<Integer> SLIME_SIZE = Services.REGISTRY.registerDataComponent("slime_size", b -> b.persistent(Codec.intRange(1, 4)).networkSynchronized(ByteBufCodecs.VAR_INT));

    public static void load(){
        Constants.LOG.info("Registering data components.");
    }
}
