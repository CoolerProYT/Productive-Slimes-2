package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.platform.util.RegistryHandler;
import com.coolerpromc.productiveslimes.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockItemTags;
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
        add(BlockItemTags.LOGS_THAT_BURN.item(), ModBlocks.SLIMY_LOG, ModBlocks.SLIMY_WOOD, ModBlocks.STRIPPED_SLIMY_LOG, ModBlocks.STRIPPED_SLIMY_WOOD);
        add(ModTags.SLIMY_LOGS, ModBlocks.SLIMY_LOG, ModBlocks.SLIMY_WOOD, ModBlocks.STRIPPED_SLIMY_LOG, ModBlocks.STRIPPED_SLIMY_WOOD);
        add(BlockItemTags.PLANKS.item(), ModBlocks.SLIMY_PLANKS);
        add(BlockItemTags.WOODEN_STAIRS.item(), ModBlocks.SLIMY_STAIRS);
        add(BlockItemTags.WOODEN_SLABS.item(), ModBlocks.SLIMY_SLAB);
        add(BlockItemTags.WOODEN_FENCES.item(), ModBlocks.SLIMY_FENCE);
        add(BlockItemTags.FENCE_GATES.item(), ModBlocks.SLIMY_FENCE_GATE);
        add(BlockItemTags.WOODEN_DOORS.item(), ModBlocks.SLIMY_DOOR);
        add(BlockItemTags.WOODEN_TRAPDOORS.item(), ModBlocks.SLIMY_TRAPDOOR);
        add(BlockItemTags.WOODEN_BUTTONS.item(), ModBlocks.SLIMY_BUTTON);
        add(BlockItemTags.WOODEN_PRESSURE_PLATES.item(), ModBlocks.SLIMY_PRESSURE_PLATE);
        add(BlockItemTags.SAPLINGS.item(), ModBlocks.SLIMY_SAPLING);
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
            appender.add(ResourceKey.create(Registries.ITEM, block.id()));
        }
    }
}
