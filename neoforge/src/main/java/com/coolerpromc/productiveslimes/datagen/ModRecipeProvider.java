package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider lookupProvider, RecipeOutput output) {
        super(lookupProvider, output);
    }

    @Override
    protected void buildRecipes() {
        shaped(RecipeCategory.TOOLS, ModItems.SLIME_JAR, 2)
                .define('G', Items.GLASS)
                .define('C', ModItems.SLIME_CRYSTAL)
                .pattern("GCG")
                .pattern("G G")
                .pattern("GGG")
                .unlockedBy(getHasName(ModItems.SLIME_CRYSTAL), has(ModItems.SLIME_CRYSTAL))
                .save(output);

        shaped(RecipeCategory.MISC, ModBlocks.SLIME_PEN)
                .define('G', Items.GLASS)
                .define('C', ModItems.SLIME_CRYSTAL)
                .define('P', ModBlocks.SLIMY_PLANKS)
                .define('S', ModBlocks.SLIMY_SLAB)
                .pattern("SSS")
                .pattern("GCG")
                .pattern("PPP")
                .unlockedBy(getHasName(ModItems.SLIME_CRYSTAL), has(ModItems.SLIME_CRYSTAL))
                .save(output);

        shaped(RecipeCategory.MISC, ModBlocks.SLIME_SQUEEZER)
                .define('I', Items.IRON_INGOT)
                .define('P', Items.PISTON)
                .define('C', ModItems.SLIME_CRYSTAL)
                .define('B', ModBlocks.SLIMY_STONE_BRICKS)
                .pattern("IPI")
                .pattern("ICI")
                .pattern("BBB")
                .unlockedBy(getHasName(ModItems.SLIME_CRYSTAL), has(ModItems.SLIME_CRYSTAL))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.SPEED_UPGRADE)
                .define('C', ModItems.SLIME_CRYSTAL)
                .define('R', Items.REDSTONE)
                .define('S', Items.SUGAR)
                .pattern("CRC")
                .pattern("RSR")
                .pattern("CRC")
                .unlockedBy(getHasName(ModItems.SLIME_CRYSTAL), has(ModItems.SLIME_CRYSTAL))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.ADVANCED_SPEED_UPGRADE)
                .define('C', ModItems.SLIME_CRYSTAL)
                .define('D', Items.DIAMOND)
                .define('U', ModItems.SPEED_UPGRADE)
                .pattern("CDC")
                .pattern("DUD")
                .pattern("CDC")
                .unlockedBy(getHasName(ModItems.SPEED_UPGRADE), has(ModItems.SPEED_UPGRADE))
                .save(output);

        planksFromLog(ModBlocks.SLIMY_PLANKS, ModTags.SLIMY_LOGS, 4);
        woodFromLogs(ModBlocks.SLIMY_WOOD, ModBlocks.SLIMY_LOG);
        woodFromLogs(ModBlocks.STRIPPED_SLIMY_WOOD, ModBlocks.STRIPPED_SLIMY_LOG);
        generateRecipes(ModModelProvider.SLIMY_PLANKS, net.minecraft.world.flag.FeatureFlagSet.of(net.minecraft.world.flag.FeatureFlags.VANILLA));

        smeltingResultFromBase(ModBlocks.SLIMY_STONE, ModBlocks.SLIMY_COBBLESTONE);
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_STONE_BRICKS, 4)
                .define('S', ModBlocks.SLIMY_STONE)
                .pattern("SS")
                .pattern("SS")
                .unlockedBy(getHasName(ModBlocks.SLIMY_STONE), has(ModBlocks.SLIMY_STONE))
                .save(output);
        generateRecipes(ModModelProvider.SLIMY_STONE_BRICKS, net.minecraft.world.flag.FeatureFlagSet.of(net.minecraft.world.flag.FeatureFlags.VANILLA));
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_STONE_BRICKS, ModBlocks.SLIMY_STONE);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_STONE_BRICK_STAIRS, ModBlocks.SLIMY_STONE_BRICKS);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLIMY_STONE_BRICK_SLAB, ModBlocks.SLIMY_STONE_BRICKS, 2);
        stonecutterResultFromBase(RecipeCategory.DECORATIONS, ModBlocks.SLIMY_STONE_BRICK_WALL, ModBlocks.SLIMY_STONE_BRICKS);

        shapeless(RecipeCategory.MISC, ModItems.GUIDE_BOOK)
                .requires(Items.BOOK)
                .requires(Items.SLIME_BALL)
                .unlockedBy(getHasName(Items.SLIME_BALL), has(Items.SLIME_BALL))
                .save(output);

        shapeless(RecipeCategory.MISC, Items.SLIME_BALL, 2)
                .requires(ModItems.SLIME_CRYSTAL)
                .unlockedBy(getHasName(ModItems.SLIME_CRYSTAL), has(ModItems.SLIME_CRYSTAL))
                .save(output, "productiveslimes:slime_ball_from_slime_crystal");
    }

    public static final class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider lookupProvider, RecipeOutput output) {
            return new ModRecipeProvider(lookupProvider, output);
        }

        @Override
        public String getName() {
            return "Productive Slimes recipes";
        }
    }
}
