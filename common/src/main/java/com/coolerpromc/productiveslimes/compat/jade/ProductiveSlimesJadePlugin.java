package com.coolerpromc.productiveslimes.compat.jade;

import com.coolerpromc.productiveslimes.block.custom.SlimePenBlock;
import com.coolerpromc.productiveslimes.block.entity.custom.SlimePenBlockEntity;
import com.coolerpromc.productiveslimes.entity.custom.ProductiveSlime;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ProductiveSlimesJadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerEntityDataProvider(SlimeJadeProvider.Data.INSTANCE, ProductiveSlime.class);
        registration.registerBlockDataProvider(SlimePenJadeProvider.Data.INSTANCE, SlimePenBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerEntityComponent(SlimeJadeProvider.INSTANCE, ProductiveSlime.class);
        registration.registerBlockComponent(SlimePenJadeProvider.INSTANCE, SlimePenBlock.class);
    }
}
