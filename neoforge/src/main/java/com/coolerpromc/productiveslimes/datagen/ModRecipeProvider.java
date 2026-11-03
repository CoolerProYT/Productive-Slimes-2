package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tag.ModTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Set;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    public static MultiRegistryBootstrap create() {
        return new MultiRegistryBootstrap() {
            @Override
            public Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
                return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
            }

            @Override
            public void run(MultiRegistryBootstrap.BootstrapGetter registries) {
                new ModRecipeProvider(registries.get(Registries.RECIPE), registries.get(Registries.ADVANCEMENT)).buildRecipes();
            }
        };
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
}
