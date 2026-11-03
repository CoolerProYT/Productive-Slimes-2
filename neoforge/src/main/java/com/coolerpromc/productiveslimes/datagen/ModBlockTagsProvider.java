package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockItemTags;
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
                ModBlocks.SLIME_SQUEEZER.key(), ModBlocks.SLIMY_STONE.key(), ModBlocks.SLIMY_COBBLESTONE.key(), ModBlocks.SLIMY_STONE_BRICKS.key(),
                ModBlocks.SLIMY_STONE_BRICK_STAIRS.key(), ModBlocks.SLIMY_STONE_BRICK_SLAB.key(), ModBlocks.SLIMY_STONE_BRICK_WALL.key(), ModBlocks.SLIME_CRYSTAL_ORE.key());
        tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.SLIME_PEN.key());
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.SLIMY_GRASS_BLOCK.key(), ModBlocks.SLIMY_DIRT.key());
        tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.SLIMY_LEAVES.key());
        tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.SLIME_CRYSTAL_ORE.key(), ModBlocks.SLIME_SQUEEZER.key());

        tag(BlockTags.DIRT).add(ModBlocks.SLIMY_GRASS_BLOCK.key(), ModBlocks.SLIMY_DIRT.key());
        tag(BlockTags.GRASS_BLOCKS).add(ModBlocks.SLIMY_GRASS_BLOCK.key());
        tag(BlockTags.SUPPORTS_VEGETATION).add(ModBlocks.SLIMY_GRASS_BLOCK.key(), ModBlocks.SLIMY_DIRT.key());
        tag(BlockTags.BASE_STONE_OVERWORLD).add(ModBlocks.SLIMY_STONE.key());
        tag(BlockTags.STONE_ORE_REPLACEABLES).add(ModBlocks.SLIMY_STONE.key());
        tag(BlockTags.SUBSTRATE_OVERWORLD).add(ModBlocks.SLIMY_DIRT.key(), ModBlocks.SLIMY_GRASS_BLOCK.key());
        tag(BlockTags.WALLS).add(ModBlocks.SLIMY_STONE_BRICK_WALL.key());
        tag(BlockTags.STAIRS).add(ModBlocks.SLIMY_STONE_BRICK_STAIRS.key());
        tag(BlockTags.SLABS).add(ModBlocks.SLIMY_STONE_BRICK_SLAB.key());
        tag(BlockTags.LEAVES).add(ModBlocks.SLIMY_LEAVES.key());
        tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(ModBlocks.SLIMY_LOG.key());

        tag(BlockItemTags.LOGS_THAT_BURN.block()).add(ModBlocks.SLIMY_LOG.key(), ModBlocks.SLIMY_WOOD.key(), ModBlocks.STRIPPED_SLIMY_LOG.key(), ModBlocks.STRIPPED_SLIMY_WOOD.key());
        tag(BlockItemTags.PLANKS.block()).add(ModBlocks.SLIMY_PLANKS.key());
        tag(BlockItemTags.WOODEN_STAIRS.block()).add(ModBlocks.SLIMY_STAIRS.key());
        tag(BlockItemTags.WOODEN_SLABS.block()).add(ModBlocks.SLIMY_SLAB.key());
        tag(BlockItemTags.WOODEN_FENCES.block()).add(ModBlocks.SLIMY_FENCE.key());
        tag(BlockItemTags.FENCE_GATES.block()).add(ModBlocks.SLIMY_FENCE_GATE.key());
        tag(BlockItemTags.WOODEN_DOORS.block()).add(ModBlocks.SLIMY_DOOR.key());
        tag(BlockItemTags.WOODEN_TRAPDOORS.block()).add(ModBlocks.SLIMY_TRAPDOOR.key());
        tag(BlockItemTags.WOODEN_BUTTONS.block()).add(ModBlocks.SLIMY_BUTTON.key());
        tag(BlockItemTags.WOODEN_PRESSURE_PLATES.block()).add(ModBlocks.SLIMY_PRESSURE_PLATE.key());
        tag(BlockItemTags.SAPLINGS.block()).add(ModBlocks.SLIMY_SAPLING.key());
    }
}
