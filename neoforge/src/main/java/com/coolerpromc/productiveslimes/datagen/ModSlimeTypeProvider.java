package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.slime.Breeding;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import com.coolerpromc.productiveslimes.slime.SlimeTypes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;

public class ModSlimeTypeProvider {
    private static final int TIER_0 = 1200;
    private static final int TIER_1 = 1600;
    private static final int TIER_2 = 2400;
    private static final int TIER_3 = 3600;
    private static final int TIER_4 = 4800;
    private static final int TIER_5 = 7200;
    private static final int TIER_6 = 12000;

    public static void bootstrap(BootstrapContext<SlimeType> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
        Builder b = new Builder(context, items, blocks);

        b.wild(SlimeTypes.SLIME, 0x7EBF6E, Items.SLIME_BALL, Items.SLIME_BALL, 2, TIER_0);
        b.wild(SlimeTypes.DIRT, 0x866043, Items.DIRT, Items.DIRT, 2, TIER_0);
        b.wild(SlimeTypes.STONE, 0x8A8A8A, Items.COBBLESTONE, Items.STONE, 2, TIER_0);
        b.wild(SlimeTypes.SAND, 0xDBCFA3, Items.SAND, Items.SAND, 2, TIER_0);
        b.wild(SlimeTypes.OAK, 0xA88754, Items.OAK_LOG, Items.OAK_LOG, 2, TIER_0);

        b.bred(SlimeTypes.GRAVEL, 0x857F7E, Items.GRAVEL, Items.GRAVEL, 2, 1, TIER_1, SlimeTypes.SAND, SlimeTypes.STONE, Blocks.GRAVEL);
        b.bred(SlimeTypes.MUD, 0x3C393D, Items.MUD, Items.MUD, 2, 1, TIER_1, SlimeTypes.DIRT, SlimeTypes.DIRT, Blocks.MUD);
        b.bred(SlimeTypes.CLAY, 0xA0A6B3, Items.CLAY_BALL, Items.CLAY_BALL, 4, 1, TIER_1, SlimeTypes.MUD, SlimeTypes.SAND, Blocks.CLAY);
        b.bred(SlimeTypes.MOSS, 0x597A30, Items.MOSS_BLOCK, Items.MOSS_BLOCK, 2, 1, TIER_1, SlimeTypes.DIRT, SlimeTypes.OAK, Blocks.MOSS_BLOCK);
        b.bred(SlimeTypes.OAK_LEAVES, 0x48B518, Items.OAK_LEAVES, Items.OAK_LEAVES, 2, 1, TIER_1, SlimeTypes.OAK, SlimeTypes.SLIME, Blocks.OAK_LEAVES);
        b.bred(SlimeTypes.CACTUS, 0x5A8A2E, Items.CACTUS, Items.CACTUS, 2, 1, TIER_1, SlimeTypes.SAND, SlimeTypes.OAK, Blocks.CACTUS);
        b.bred(SlimeTypes.SNOW, 0xF2FCFC, Items.SNOWBALL, Items.SNOWBALL, 4, 1, TIER_1, SlimeTypes.SLIME, SlimeTypes.SLIME, Blocks.SNOW_BLOCK);
        b.bred(SlimeTypes.RED_SAND, 0xBB6520, Items.RED_SAND, Items.RED_SAND, 2, 1, TIER_1, SlimeTypes.SAND, SlimeTypes.SAND, Blocks.RED_SAND);
        b.bred(SlimeTypes.ANDESITE, 0x8B8B8C, Items.ANDESITE, Items.ANDESITE, 2, 1, TIER_1, SlimeTypes.STONE, SlimeTypes.STONE, Blocks.ANDESITE);
        b.bred(SlimeTypes.GRANITE, 0x9A6B57, Items.GRANITE, Items.GRANITE, 2, 1, TIER_1, SlimeTypes.STONE, SlimeTypes.STONE, Blocks.GRANITE);
        b.bred(SlimeTypes.DIORITE, 0xBEBEBF, Items.DIORITE, Items.DIORITE, 2, 1, TIER_1, SlimeTypes.STONE, SlimeTypes.STONE, Blocks.DIORITE);
        b.bred(SlimeTypes.CALCITE, 0xDFE0DC, Items.CALCITE, Items.CALCITE, 2, 1, TIER_1, SlimeTypes.STONE, SlimeTypes.STONE, Blocks.CALCITE);
        b.bred(SlimeTypes.TUFF, 0x6C6D66, Items.TUFF, Items.TUFF, 2, 1, TIER_1, SlimeTypes.STONE, SlimeTypes.STONE, Blocks.TUFF);
        b.bred(SlimeTypes.DEEPSLATE, 0x4F4F55, Items.COBBLED_DEEPSLATE, Items.COBBLED_DEEPSLATE, 2, 1, TIER_1, SlimeTypes.STONE, SlimeTypes.STONE, Blocks.DEEPSLATE);
        b.bred(SlimeTypes.COAL, 0x3B3D3B, Items.COAL, Items.COAL, 2, 1, TIER_1, SlimeTypes.STONE, SlimeTypes.OAK, Blocks.COAL_BLOCK);

        b.bred(SlimeTypes.DRIPSTONE, 0x866B5C, Items.POINTED_DRIPSTONE, Items.DRIPSTONE_BLOCK, 2, 2, TIER_2, SlimeTypes.STONE, SlimeTypes.MUD, Blocks.DRIPSTONE_BLOCK);
        b.bred(SlimeTypes.ICE, 0x89B1FC, Items.ICE, Items.ICE, 1, 2, TIER_2, SlimeTypes.SNOW, SlimeTypes.SNOW, Blocks.ICE);
        b.bred(SlimeTypes.COPPER, 0xC46D4A, Items.RAW_COPPER, Items.COPPER_INGOT, 1, 2, TIER_2, SlimeTypes.STONE, SlimeTypes.COAL, Blocks.COPPER_BLOCK);
        b.bred(SlimeTypes.NETHERRACK, 0x763535, Items.NETHERRACK, Items.NETHERRACK, 2, 2, TIER_2, SlimeTypes.STONE, SlimeTypes.COAL, Blocks.NETHERRACK);
        b.bred(SlimeTypes.PRISMARINE, 0x63A08F, Items.PRISMARINE_SHARD, Items.PRISMARINE_SHARD, 2, 2, TIER_2, SlimeTypes.SAND, SlimeTypes.CLAY, Blocks.PRISMARINE);
        b.bred(SlimeTypes.BROWN_MUSHROOM, 0x967251, Items.BROWN_MUSHROOM, Items.BROWN_MUSHROOM, 2, 2, TIER_2, SlimeTypes.MUD, SlimeTypes.MOSS, Blocks.BROWN_MUSHROOM_BLOCK);
        b.bred(SlimeTypes.RED_MUSHROOM, 0xC02624, Items.RED_MUSHROOM, Items.RED_MUSHROOM, 2, 2, TIER_2, SlimeTypes.MUD, SlimeTypes.MOSS, Blocks.RED_MUSHROOM_BLOCK);

        b.bred(SlimeTypes.IRON, 0xD8D8D8, Items.RAW_IRON, Items.IRON_INGOT, 1, 3, TIER_3, SlimeTypes.COPPER, SlimeTypes.STONE, Blocks.IRON_BLOCK);
        b.bred(SlimeTypes.SOUL_SAND, 0x513E32, Items.SOUL_SAND, Items.SOUL_SAND, 2, 3, TIER_3, SlimeTypes.SAND, SlimeTypes.NETHERRACK, Blocks.SOUL_SAND);
        b.bred(SlimeTypes.SOUL_SOIL, 0x4B3A2F, Items.SOUL_SOIL, Items.SOUL_SOIL, 2, 3, TIER_3, SlimeTypes.DIRT, SlimeTypes.NETHERRACK, Blocks.SOUL_SOIL);
        b.bred(SlimeTypes.BLACKSTONE, 0x2A2328, Items.BLACKSTONE, Items.BLACKSTONE, 2, 3, TIER_3, SlimeTypes.DEEPSLATE, SlimeTypes.NETHERRACK, Blocks.BLACKSTONE);
        b.bred(SlimeTypes.BASALT, 0x505055, Items.BASALT, Items.BASALT, 2, 3, TIER_3, SlimeTypes.DEEPSLATE, SlimeTypes.NETHERRACK, Blocks.BASALT);
        b.bred(SlimeTypes.MAGMA, 0x8E3B1B, Items.MAGMA_CREAM, Items.MAGMA_BLOCK, 1, 3, TIER_3, SlimeTypes.NETHERRACK, SlimeTypes.COAL, Blocks.MAGMA_BLOCK);

        b.bred(SlimeTypes.GOLD, 0xF4CB3C, Items.RAW_GOLD, Items.GOLD_INGOT, 1, 4, TIER_4, SlimeTypes.IRON, SlimeTypes.COPPER, Blocks.GOLD_BLOCK);
        b.bred(SlimeTypes.LAPIS, 0x1C41BA, Items.LAPIS_LAZULI, Items.LAPIS_LAZULI, 2, 4, TIER_4, SlimeTypes.IRON, SlimeTypes.CLAY, Blocks.LAPIS_BLOCK);
        b.bred(SlimeTypes.REDSTONE, 0xB01010, Items.REDSTONE, Items.REDSTONE, 2, 4, TIER_4, SlimeTypes.IRON, SlimeTypes.MAGMA, Blocks.REDSTONE_BLOCK);
        b.bred(SlimeTypes.QUARTZ, 0xE4DDD3, Items.QUARTZ, Items.QUARTZ, 2, 4, TIER_4, SlimeTypes.IRON, SlimeTypes.NETHERRACK, Blocks.QUARTZ_BLOCK);
        b.bred(SlimeTypes.GLOWSTONE, 0xFFD88A, Items.GLOWSTONE_DUST, Items.GLOWSTONE_DUST, 2, 4, TIER_4, SlimeTypes.NETHERRACK, SlimeTypes.MAGMA, Blocks.GLOWSTONE);
        b.bred(SlimeTypes.OBSIDIAN, 0x2B1D45, Items.OBSIDIAN, Items.OBSIDIAN, 1, 4, TIER_4, SlimeTypes.DEEPSLATE, SlimeTypes.MAGMA, Blocks.OBSIDIAN);

        b.bred(SlimeTypes.AMETHYST, 0x9A5CC6, Items.AMETHYST_SHARD, Items.AMETHYST_SHARD, 2, 5, TIER_5, SlimeTypes.CALCITE, SlimeTypes.GLOWSTONE, Blocks.AMETHYST_BLOCK);
        b.bred(SlimeTypes.END_STONE, 0xDBDE9E, Items.END_STONE, Items.END_STONE, 2, 5, TIER_5, SlimeTypes.OBSIDIAN, SlimeTypes.CALCITE, Blocks.END_STONE);
        b.bred(SlimeTypes.DIAMOND, 0x4AEDD9, Items.DIAMOND, Items.DIAMOND, 1, 5, TIER_5, SlimeTypes.GOLD, SlimeTypes.LAPIS, Blocks.DIAMOND_BLOCK);

        b.bred(SlimeTypes.NETHERITE, 0x5A4A4D, Items.NETHERITE_SCRAP, Items.NETHERITE_SCRAP, 1, 6, TIER_6, SlimeTypes.DIAMOND, SlimeTypes.OBSIDIAN, Blocks.NETHERITE_BLOCK);
    }

    private record Builder(BootstrapContext<SlimeType> context, HolderGetter<Item> items, HolderGetter<Block> blocks) {
        void wild(ResourceKey<SlimeType> key, int color, Item food, Item result, int count, int productionTime) {
            this.context.register(key, new SlimeType(color, food(food), new ItemStackTemplate(result, count), productionTime, 0, Optional.empty(), true));
        }

        void bred(ResourceKey<SlimeType> key, int color, Item food, Item result, int count, int tier, int productionTime, ResourceKey<SlimeType> parentA, ResourceKey<SlimeType> parentB, Block catalyst) {
            Breeding breeding = new Breeding(parentA, parentB, Optional.of(HolderSet.direct(this.blocks.getOrThrow(catalyst.builtInRegistryHolder().key()))), false);
            this.context.register(key, new SlimeType(color, food(food), new ItemStackTemplate(result, count), productionTime, tier, Optional.of(breeding), false));
        }

        private HolderSet<Item> food(Item item) {
            return HolderSet.direct(this.items.getOrThrow(item.builtInRegistryHolder().key()));
        }
    }
}
