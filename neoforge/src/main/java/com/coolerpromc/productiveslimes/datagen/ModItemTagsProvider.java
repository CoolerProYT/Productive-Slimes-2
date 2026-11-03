package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.platform.util.RegistryHandler;
import com.coolerpromc.productiveslimes.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Constants.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        add(ItemTags.LOGS_THAT_BURN, ModBlocks.SLIMY_LOG, ModBlocks.SLIMY_WOOD, ModBlocks.STRIPPED_SLIMY_LOG, ModBlocks.STRIPPED_SLIMY_WOOD);
        add(ModTags.SLIMY_LOGS, ModBlocks.SLIMY_LOG, ModBlocks.SLIMY_WOOD, ModBlocks.STRIPPED_SLIMY_LOG, ModBlocks.STRIPPED_SLIMY_WOOD);
        add(ItemTags.PLANKS, ModBlocks.SLIMY_PLANKS);
        add(ItemTags.WOODEN_STAIRS, ModBlocks.SLIMY_STAIRS);
        add(ItemTags.WOODEN_SLABS, ModBlocks.SLIMY_SLAB);
        add(ItemTags.WOODEN_FENCES, ModBlocks.SLIMY_FENCE);
        add(ItemTags.FENCE_GATES, ModBlocks.SLIMY_FENCE_GATE);
        add(ItemTags.WOODEN_DOORS, ModBlocks.SLIMY_DOOR);
        add(ItemTags.WOODEN_TRAPDOORS, ModBlocks.SLIMY_TRAPDOOR);
        add(ItemTags.WOODEN_BUTTONS, ModBlocks.SLIMY_BUTTON);
        add(ItemTags.WOODEN_PRESSURE_PLATES, ModBlocks.SLIMY_PRESSURE_PLATE);
        add(ItemTags.SAPLINGS, ModBlocks.SLIMY_SAPLING);
        add(ItemTags.LEAVES, ModBlocks.SLIMY_LEAVES);
        add(ItemTags.DIRT, ModBlocks.SLIMY_DIRT, ModBlocks.SLIMY_GRASS_BLOCK);
        add(ItemTags.WALLS, ModBlocks.SLIMY_STONE_BRICK_WALL);
        add(ItemTags.STONE_CRAFTING_MATERIALS, ModBlocks.SLIMY_COBBLESTONE);
        add(ItemTags.STONE_TOOL_MATERIALS, ModBlocks.SLIMY_COBBLESTONE);
    }

    @SafeVarargs
    private void add(TagKey<Item> tag, RegistryHandler.Blocks<?>... blocks) {
        var appender = tag(tag);
        for (RegistryHandler.Blocks<?> block : blocks) {
            appender.add(block.get().asItem());
        }
    }
}
