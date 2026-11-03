package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Constants.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.SLIME_SQUEEZER.get(), ModBlocks.SLIMY_STONE.get(), ModBlocks.SLIMY_COBBLESTONE.get(), ModBlocks.SLIMY_STONE_BRICKS.get(),
                ModBlocks.SLIMY_STONE_BRICK_STAIRS.get(), ModBlocks.SLIMY_STONE_BRICK_SLAB.get(), ModBlocks.SLIMY_STONE_BRICK_WALL.get(), ModBlocks.SLIME_CRYSTAL_ORE.get());
        tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.SLIME_PEN.get());
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.SLIMY_GRASS_BLOCK.get(), ModBlocks.SLIMY_DIRT.get());
        tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.SLIMY_LEAVES.get());
        tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.SLIME_CRYSTAL_ORE.get(), ModBlocks.SLIME_SQUEEZER.get());

        tag(BlockTags.DIRT).add(ModBlocks.SLIMY_GRASS_BLOCK.get(), ModBlocks.SLIMY_DIRT.get());
        tag(BlockTags.GRASS_BLOCKS).add(ModBlocks.SLIMY_GRASS_BLOCK.get());
        tag(BlockTags.SUPPORTS_VEGETATION).add(ModBlocks.SLIMY_GRASS_BLOCK.get(), ModBlocks.SLIMY_DIRT.get());
        tag(BlockTags.BASE_STONE_OVERWORLD).add(ModBlocks.SLIMY_STONE.get());
        tag(BlockTags.STONE_ORE_REPLACEABLES).add(ModBlocks.SLIMY_STONE.get());
        tag(BlockTags.SUBSTRATE_OVERWORLD).add(ModBlocks.SLIMY_DIRT.get(), ModBlocks.SLIMY_GRASS_BLOCK.get());
        tag(BlockTags.WALLS).add(ModBlocks.SLIMY_STONE_BRICK_WALL.get());
        tag(BlockTags.STAIRS).add(ModBlocks.SLIMY_STONE_BRICK_STAIRS.get());
        tag(BlockTags.SLABS).add(ModBlocks.SLIMY_STONE_BRICK_SLAB.get());
        tag(BlockTags.LEAVES).add(ModBlocks.SLIMY_LEAVES.get());
        tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(ModBlocks.SLIMY_LOG.get());

        tag(BlockTags.LOGS_THAT_BURN).add(ModBlocks.SLIMY_LOG.get(), ModBlocks.SLIMY_WOOD.get(), ModBlocks.STRIPPED_SLIMY_LOG.get(), ModBlocks.STRIPPED_SLIMY_WOOD.get());
        tag(BlockTags.PLANKS).add(ModBlocks.SLIMY_PLANKS.get());
        tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.SLIMY_STAIRS.get());
        tag(BlockTags.WOODEN_SLABS).add(ModBlocks.SLIMY_SLAB.get());
        tag(BlockTags.WOODEN_FENCES).add(ModBlocks.SLIMY_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(ModBlocks.SLIMY_FENCE_GATE.get());
        tag(BlockTags.WOODEN_DOORS).add(ModBlocks.SLIMY_DOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.SLIMY_TRAPDOOR.get());
        tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.SLIMY_BUTTON.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.SLIMY_PRESSURE_PLATE.get());
        tag(BlockTags.SAPLINGS).add(ModBlocks.SLIMY_SAPLING.get());
    }
}
