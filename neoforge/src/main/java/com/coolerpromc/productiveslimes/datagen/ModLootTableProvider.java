package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.platform.util.RegistryHandler;
import com.coolerpromc.productiveslimes.worldgen.ModStructures;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.UniformContainerBase;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.List;
import java.util.Set;

public final class ModLootTableProvider {
    private ModLootTableProvider() {
    }

    public static LootTableProvider create() {
        return new LootTableProvider(Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(ChestLoot::new, LootContextParamSets.CHEST)
        ));
    }

    private static class BlockLoot extends BlockLootSubProvider {
        private static final float[] SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

        BlockLoot(LootTableSubProvider.Context output) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), output);
        }

        @Override
        protected void generate() {
            dropSelf(ModBlocks.SLIME_PEN.get());
            dropSelf(ModBlocks.SLIME_SQUEEZER.get());

            add(ModBlocks.SLIMY_GRASS_BLOCK.get(), block -> createSingleItemTableWithSilkTouch(block, ModBlocks.SLIMY_DIRT.get()));
            dropSelf(ModBlocks.SLIMY_DIRT.get());
            add(ModBlocks.SLIME_SPROUTS.get(), createShearsOnlyDrop(ModBlocks.SLIME_SPROUTS.get()));
            add(ModBlocks.SLIMY_STONE.get(), block -> createSingleItemTableWithSilkTouch(block, ModBlocks.SLIMY_COBBLESTONE.get()));
            dropSelf(ModBlocks.SLIMY_COBBLESTONE.get());
            dropSelf(ModBlocks.SLIMY_STONE_BRICKS.get());
            dropSelf(ModBlocks.SLIMY_STONE_BRICK_STAIRS.get());
            add(ModBlocks.SLIMY_STONE_BRICK_SLAB.get(), this::createSlabItemTable);
            dropSelf(ModBlocks.SLIMY_STONE_BRICK_WALL.get());
            add(ModBlocks.SLIME_CRYSTAL_ORE.get(), block -> createOreDrop(block, ModItems.SLIME_CRYSTAL.get()));

            dropSelf(ModBlocks.SLIMY_LOG.get());
            dropSelf(ModBlocks.SLIMY_WOOD.get());
            dropSelf(ModBlocks.STRIPPED_SLIMY_LOG.get());
            dropSelf(ModBlocks.STRIPPED_SLIMY_WOOD.get());
            dropSelf(ModBlocks.SLIMY_PLANKS.get());
            dropSelf(ModBlocks.SLIMY_STAIRS.get());
            add(ModBlocks.SLIMY_SLAB.get(), this::createSlabItemTable);
            dropSelf(ModBlocks.SLIMY_FENCE.get());
            dropSelf(ModBlocks.SLIMY_FENCE_GATE.get());
            add(ModBlocks.SLIMY_DOOR.get(), this::createDoorTable);
            dropSelf(ModBlocks.SLIMY_TRAPDOOR.get());
            dropSelf(ModBlocks.SLIMY_BUTTON.get());
            dropSelf(ModBlocks.SLIMY_PRESSURE_PLATE.get());
            add(ModBlocks.SLIMY_LEAVES.get(), block -> createLeavesDrops(block, ModBlocks.SLIMY_SAPLING.get(), SAPLING_CHANCES));
            dropSelf(ModBlocks.SLIMY_SAPLING.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.ALL.stream().<Block>map(RegistryHandler::get).toList();
        }
    }

    private record ChestLoot(LootTableSubProvider.Context output) implements LootTableSubProvider {
        @Override
        public void run() {
            this.output.accept(ModStructures.SLIME_SHRINE_CHEST, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ContextIntProviders.between(4, 7))
                            .add(entry(ModItems.SLIME_CRYSTAL, 20, 2, 5))
                            .add(entry(ModItems.SLIME_JAR, 15, 1, 3))
                            .add(entry(Items.SLIME_BALL, 15, 3, 8))
                            .add(entry(ModBlocks.SLIMY_SAPLING, 10, 1, 3))
                            .add(entry(Items.IRON_INGOT, 8, 1, 4))
                            .add(entry(Items.COPPER_INGOT, 8, 2, 6))
                            .add(entry(Items.COAL, 10, 3, 8))
                            .add(entry(Items.BREAD, 10, 2, 4)))
                    .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                            .add(entry(ModItems.SPEED_UPGRADE, 3, 1, 1))
                            .add(entry(ModBlocks.SLIME_PEN, 2, 1, 1))
                            .add(entry(Items.COPPER_BLOCK.weathering().unaffected(), 1, 1, 1))
                            .add(entry(ModItems.SLIME_CRYSTAL, 4, 4, 8))));
        }

        private static UniformContainerBase.Builder<?> entry(ItemLike item, int weight, int min, int max) {
            return LootItem.lootTableItem(item).setWeight(weight)
                    .apply(SetItemCountFunction.setCount(ContextIntProviders.between(min, max)));
        }
    }
}
