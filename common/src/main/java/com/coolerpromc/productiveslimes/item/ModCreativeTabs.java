package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.platform.Services;
import com.coolerpromc.productiveslimes.platform.util.RegistryHandler;
import com.coolerpromc.productiveslimes.slime.SlimeBreeding;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class ModCreativeTabs {
    public static final RegistryHandler<CreativeModeTab, CreativeModeTab> PRODUCTIVE_SLIMES = Services.REGISTRY.registerCreativeTab("productive_slimes", ModItems.SLIME_JAR::toStack, Component.translatable("creativetab.productiveslimes"),
            (output, parameters) -> {
                output.accept(ModItems.GUIDE_BOOK.toStack());
                output.accept(ModBlocks.SLIME_PEN.toStack());
                output.accept(ModBlocks.SLIME_SQUEEZER.toStack());
                output.accept(ModItems.SLIME_JAR.toStack());
                output.accept(ModItems.SLIME_CRYSTAL.toStack());
                output.accept(ModItems.SPEED_UPGRADE.toStack());
                output.accept(ModItems.ADVANCED_SPEED_UPGRADE.toStack());
                ModBlocks.ALL.stream().filter(block -> block != ModBlocks.SLIME_PEN && block != ModBlocks.SLIME_SQUEEZER).forEach(block -> output.accept(block.toStack()));
            });

    public static final RegistryHandler<CreativeModeTab, CreativeModeTab> SLIMES = Services.REGISTRY.registerCreativeTab("slimes", ModItems.PRODUCTIVE_SLIME_SPAWN_EGG::toStack, Component.translatable("creativetab.productiveslimes.slimes"),
            (output, parameters) -> {
                for (Holder<SlimeType> type : SlimeBreeding.sortedTypes(parameters.holders())) {
                    output.accept(ModItems.slimeball(type, 1));
                }
                for (Holder<SlimeType> type : SlimeBreeding.sortedTypes(parameters.holders())) {
                    output.accept(ModItems.slimeJar(type, 2));
                }
                for (Holder<SlimeType> type : SlimeBreeding.sortedTypes(parameters.holders())) {
                    output.accept(ModItems.spawnEgg(type));
                }
            });

    public static void load() {
        Constants.LOG.info("Registering creative tabs.");
    }
}
