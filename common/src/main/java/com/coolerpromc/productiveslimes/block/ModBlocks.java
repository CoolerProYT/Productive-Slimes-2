package com.coolerpromc.productiveslimes.block;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.block.custom.*;
import com.coolerpromc.productiveslimes.block.custom.SlimePenBlock;
import com.coolerpromc.productiveslimes.block.custom.SlimeSproutsBlock;
import com.coolerpromc.productiveslimes.block.custom.SlimeSqueezerBlock;
import com.coolerpromc.productiveslimes.block.custom.SlimyGrassBlock;
import com.coolerpromc.productiveslimes.block.custom.StrippableLogBlock;
import com.coolerpromc.productiveslimes.platform.Services;
import com.coolerpromc.productiveslimes.platform.util.RegistryHandler;
import com.coolerpromc.productiveslimes.worldgen.ModFeatures;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.sounds.AmbientLeavesBlockSoundPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.UntintedParticleLeavesBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.util.random.WeightedList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModBlocks {
    public static final List<RegistryHandler.Blocks<? extends Block>> ALL = new ArrayList<>();

    public static final BlockSetType SLIMY_SET = new BlockSetType(Constants.MODID + ":slimy");
    public static final WoodType SLIMY_WOOD_TYPE = new WoodType(Constants.MODID + ":slimy", SLIMY_SET);
    public static final TreeGrower SLIMY_TREE = new TreeGrower(Constants.MODID + ":slimy", WeightedList.of(ModFeatures.SLIMY_TREE), WeightedList.of(), WeightedList.of(), null);

    public static final RegistryHandler.Blocks<SlimePenBlock> SLIME_PEN = registerBlock("slime_pen", SlimePenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(2.0F).sound(SoundType.GLASS).noOcclusion().requiresCorrectToolForDrops());
    public static final RegistryHandler.Blocks<SlimeSqueezerBlock> SLIME_SQUEEZER = registerBlock("slime_squeezer", SlimeSqueezerBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.0F).sound(SoundType.COPPER).noOcclusion().requiresCorrectToolForDrops());

    public static final RegistryHandler.Blocks<SlimyGrassBlock> SLIMY_GRASS_BLOCK = registerBlock("slimy_grass_block", SlimyGrassBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).mapColor(MapColor.COLOR_LIGHT_GREEN).sound(SoundType.SLIME_BLOCK));
    public static final RegistryHandler.Blocks<Block> SLIMY_DIRT = registerBlock("slimy_dirt", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).mapColor(MapColor.COLOR_GREEN));
    public static final RegistryHandler.Blocks<SlimeSproutsBlock> SLIME_SPROUTS = registerBlock("slime_sprouts", SlimeSproutsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(MapColor.COLOR_LIGHT_GREEN).sound(SoundType.SLIME_BLOCK));
    public static final RegistryHandler.Blocks<Block> SLIMY_STONE = registerBlock("slimy_stone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_GREEN));
    public static final RegistryHandler.Blocks<Block> SLIMY_COBBLESTONE = registerBlock("slimy_cobblestone", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE).mapColor(MapColor.COLOR_GREEN));
    public static final RegistryHandler.Blocks<Block> SLIMY_STONE_BRICKS = registerBlock("slimy_stone_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_GREEN));
    public static final RegistryHandler.Blocks<ModStairBlock> SLIMY_STONE_BRICK_STAIRS = registerBlock("slimy_stone_brick_stairs", p -> new ModStairBlock(SLIMY_STONE_BRICKS.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.COLOR_GREEN));
    public static final RegistryHandler.Blocks<SlabBlock> SLIMY_STONE_BRICK_SLAB = registerBlock("slimy_stone_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.COLOR_GREEN));
    public static final RegistryHandler.Blocks<WallBlock> SLIMY_STONE_BRICK_WALL = registerBlock("slimy_stone_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_GREEN));
    public static final RegistryHandler.Blocks<DropExperienceBlock> SLIME_CRYSTAL_ORE = registerBlock("slime_crystal_ore", p -> new DropExperienceBlock(UniformInt.of(2, 5), p), BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE).mapColor(MapColor.COLOR_GREEN).lightLevel(state -> 3));

    public static final RegistryHandler.Blocks<RotatedPillarBlock> STRIPPED_SLIMY_LOG = registerBlock("stripped_slimy_log", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_LIGHT_GREEN));
    public static final RegistryHandler.Blocks<RotatedPillarBlock> STRIPPED_SLIMY_WOOD = registerBlock("stripped_slimy_wood", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_LIGHT_GREEN));
    public static final RegistryHandler.Blocks<StrippableLogBlock> SLIMY_LOG = registerBlock("slimy_log", p -> new StrippableLogBlock(STRIPPED_SLIMY_LOG, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_GREEN));
    public static final RegistryHandler.Blocks<StrippableLogBlock> SLIMY_WOOD = registerBlock("slimy_wood", p -> new StrippableLogBlock(STRIPPED_SLIMY_WOOD, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_GREEN));
    public static final RegistryHandler.Blocks<Block> SLIMY_PLANKS = registerBlock("slimy_planks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_LIGHT_GREEN));
    public static final RegistryHandler.Blocks<ModStairBlock> SLIMY_STAIRS = registerBlock("slimy_stairs", p -> new ModStairBlock(SLIMY_PLANKS.get().defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_LIGHT_GREEN));
    public static final RegistryHandler.Blocks<SlabBlock> SLIMY_SLAB = registerBlock("slimy_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_LIGHT_GREEN));
    public static final RegistryHandler.Blocks<FenceBlock> SLIMY_FENCE = registerBlock("slimy_fence", FenceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_LIGHT_GREEN));
    public static final RegistryHandler.Blocks<FenceGateBlock> SLIMY_FENCE_GATE = registerBlock("slimy_fence_gate", p -> new FenceGateBlock(SLIMY_WOOD_TYPE, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_LIGHT_GREEN));
    public static final RegistryHandler.Blocks<ModDoorBlock> SLIMY_DOOR = registerBlock("slimy_door", p -> new ModDoorBlock(SLIMY_SET, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_LIGHT_GREEN));
    public static final RegistryHandler.Blocks<ModTrapDoorBlock> SLIMY_TRAPDOOR = registerBlock("slimy_trapdoor", p -> new ModTrapDoorBlock(SLIMY_SET, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_LIGHT_GREEN));
    public static final RegistryHandler.Blocks<ModButtonBlock> SLIMY_BUTTON = registerBlock("slimy_button", p -> new ModButtonBlock(SLIMY_SET, 30, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON));
    public static final RegistryHandler.Blocks<ModPressurePlateBlock> SLIMY_PRESSURE_PLATE = registerBlock("slimy_pressure_plate", p -> new ModPressurePlateBlock(SLIMY_SET, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_LIGHT_GREEN));
    public static final RegistryHandler.Blocks<UntintedParticleLeavesBlock> SLIMY_LEAVES = registerBlock("slimy_leaves", p -> new UntintedParticleLeavesBlock(0.02F, ParticleTypes.ITEM_SLIME, AmbientLeavesBlockSoundPlayer.noAmbientSound(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_LIGHT_GREEN));
    public static final RegistryHandler.Blocks<ModSaplingBlock> SLIMY_SAPLING = registerBlock("slimy_sapling", p -> new ModSaplingBlock(SLIMY_TREE, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_LIGHT_GREEN));

    private static <T extends Block> RegistryHandler.Blocks<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> func, BlockBehaviour.Properties properties) {
        RegistryHandler.Blocks<T> block = Services.REGISTRY.registerBlock(name, func, properties);
        Services.REGISTRY.registerItem(name, p -> new BlockItem(block.get(), p.useBlockDescriptionPrefix()));
        ALL.add(block);
        return block;
    }

    public static void load() {
        Constants.LOG.info("Registering blocks.");
    }
}
