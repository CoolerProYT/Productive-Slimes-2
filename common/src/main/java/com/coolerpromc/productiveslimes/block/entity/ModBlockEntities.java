package com.coolerpromc.productiveslimes.block.entity;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.entity.custom.SlimePenBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.custom.SlimeSqueezerBlockEntity;
import com.coolerpromc.productiveslimes.platform.Services;
import com.coolerpromc.productiveslimes.platform.util.RegistryHandler;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;

public class ModBlockEntities {
    public static final RegistryHandler<BlockEntityType<?>, BlockEntityType<SlimePenBlockEntity>> SLIME_PEN = Services.REGISTRY.registerBlockEntityType("slime_pen", SlimePenBlockEntity::new, List.of(ModBlocks.SLIME_PEN));
    public static final RegistryHandler<BlockEntityType<?>, BlockEntityType<SlimeSqueezerBlockEntity>> SLIME_SQUEEZER = Services.REGISTRY.registerBlockEntityType("slime_squeezer", SlimeSqueezerBlockEntity::new, List.of(ModBlocks.SLIME_SQUEEZER));

    public static void load() {
        Constants.LOG.info("Registering block entities.");
    }
}
