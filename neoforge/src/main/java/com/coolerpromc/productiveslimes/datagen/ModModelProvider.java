package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.custom.SlimeSqueezerBlock;
import com.coolerpromc.productiveslimes.client.tint.SlimeTypeTintSource;
import com.coolerpromc.productiveslimes.data.component.ModDataComponents;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.item.properties.conditional.HasComponent;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

public class ModModelProvider extends ModelProvider {
    public static final BlockFamily SLIMY_PLANKS = new BlockFamily.Builder(ModBlocks.SLIMY_PLANKS.get())
            .log(ModBlocks.SLIMY_LOG.get())
            .strippedLog(ModBlocks.STRIPPED_SLIMY_LOG.get())
            .button(ModBlocks.SLIMY_BUTTON.get())
            .fence(ModBlocks.SLIMY_FENCE.get())
            .fenceGate(ModBlocks.SLIMY_FENCE_GATE.get())
            .pressurePlate(ModBlocks.SLIMY_PRESSURE_PLATE.get())
            .slab(ModBlocks.SLIMY_SLAB.get())
            .stairs(ModBlocks.SLIMY_STAIRS.get())
            .door(ModBlocks.SLIMY_DOOR.get())
            .trapdoor(ModBlocks.SLIMY_TRAPDOOR.get())
            .recipeGroupPrefix("wooden")
            .recipeUnlockedBy("has_planks")
            .getFamily();

    public static final BlockFamily SLIMY_STONE_BRICKS = new BlockFamily.Builder(ModBlocks.SLIMY_STONE_BRICKS.get())
            .stairs(ModBlocks.SLIMY_STONE_BRICK_STAIRS.get())
            .slab(ModBlocks.SLIMY_STONE_BRICK_SLAB.get())
            .wall(ModBlocks.SLIMY_STONE_BRICK_WALL.get())
            .getFamily();

    public ModModelProvider(PackOutput output) {
        super(output, Constants.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        blockModels.createTrivialCube(ModBlocks.SLIMY_DIRT.get());
        blockModels.createTrivialCube(ModBlocks.SLIMY_STONE.get());
        blockModels.createTrivialCube(ModBlocks.SLIMY_COBBLESTONE.get());
        blockModels.createTrivialCube(ModBlocks.SLIME_CRYSTAL_ORE.get());
        blockModels.family(ModBlocks.SLIMY_STONE_BRICKS.get()).generateFor(SLIMY_STONE_BRICKS);

        TextureMapping grassTextures = new TextureMapping()
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(ModBlocks.SLIMY_DIRT.get()))
                .copyForced(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(ModBlocks.SLIMY_GRASS_BLOCK.get(), "_top"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(ModBlocks.SLIMY_GRASS_BLOCK.get(), "_side"));
        Identifier grassModel = ModelTemplates.CUBE_BOTTOM_TOP.create(ModBlocks.SLIMY_GRASS_BLOCK.get(), grassTextures, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(ModBlocks.SLIMY_GRASS_BLOCK.get(), BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(grassModel))));

        blockModels.createCrossBlockWithDefaultItem(ModBlocks.SLIME_SPROUTS.get(), BlockModelGenerators.PlantType.NOT_TINTED);

        blockModels.woodProvider(ModBlocks.SLIMY_LOG.get()).logWithHorizontal(ModBlocks.SLIMY_LOG.get()).wood(ModBlocks.SLIMY_WOOD.get());
        blockModels.woodProvider(ModBlocks.STRIPPED_SLIMY_LOG.get()).logWithHorizontal(ModBlocks.STRIPPED_SLIMY_LOG.get()).wood(ModBlocks.STRIPPED_SLIMY_WOOD.get());
        blockModels.family(ModBlocks.SLIMY_PLANKS.get()).generateFor(SLIMY_PLANKS);
        blockModels.createTrivialBlock(ModBlocks.SLIMY_LEAVES.get(), TexturedModel.LEAVES);
        blockModels.createCrossBlockWithDefaultItem(ModBlocks.SLIMY_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);

        Identifier penModel = Constants.id("block/slime_pen");
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(ModBlocks.SLIME_PEN.get(), BlockModelGenerators.plainVariant(penModel)).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
        blockModels.registerSimpleItemModel(ModBlocks.SLIME_PEN.get(), penModel);

        Identifier squeezerModel = Constants.id("block/slime_squeezer");
        Identifier squeezerWorkingModel = Constants.id("block/slime_squeezer_working");
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(ModBlocks.SLIME_SQUEEZER.get())
                .with(PropertyDispatch.initial(SlimeSqueezerBlock.WORKING).generate(working -> BlockModelGenerators.plainVariant(working ? squeezerWorkingModel : squeezerModel)))
                .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
        blockModels.registerSimpleItemModel(ModBlocks.SLIME_SQUEEZER.get(), squeezerModel);

        itemModels.generateFlatItem(ModItems.SLIME_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GUIDE_BOOK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SPEED_UPGRADE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ADVANCED_SPEED_UPGRADE.get(), ModelTemplates.FLAT_ITEM);

        SlimeTypeTintSource slimeTint = new SlimeTypeTintSource(0xFF7EBF6E);

        Identifier slimeball = itemModels.createFlatItemModel(ModItems.SLIMEBALL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.itemModelOutput.accept(ModItems.SLIMEBALL.get(), ItemModelUtils.tintedModel(slimeball, slimeTint));

        Identifier emptyJar = itemModels.createFlatItemModel(ModItems.SLIME_JAR.get(), ModelTemplates.FLAT_ITEM);
        Identifier filledJar = itemModels.generateLayeredItem(Constants.id("item/slime_jar_filled"), new Material(Constants.id("item/slime_jar_contents")), new Material(Constants.id("item/slime_jar")));
        itemModels.itemModelOutput.accept(ModItems.SLIME_JAR.get(), ItemModelUtils.conditional(
                new HasComponent(ModDataComponents.SLIME_TYPE.get(), false),
                ItemModelUtils.tintedModel(filledJar, slimeTint, ItemModelUtils.constantTint(-1)),
                ItemModelUtils.plainModel(emptyJar)));

        Identifier spawnEgg = itemModels.generateLayeredItem(ModItems.PRODUCTIVE_SLIME_SPAWN_EGG.get(), new Material(Constants.id("item/productive_slime_spawn_egg")), new Material(Constants.id("item/productive_slime_spawn_egg_overlay")));
        itemModels.itemModelOutput.accept(ModItems.PRODUCTIVE_SLIME_SPAWN_EGG.get(), ItemModelUtils.tintedModel(spawnEgg, slimeTint, slimeTint));
    }
}
