package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;

public class ProductionCategory extends AbstractRecipeCategory<Holder<SlimeType>> {
    @SuppressWarnings("unchecked")
    public static final IRecipeType<Holder<SlimeType>> TYPE = IRecipeType.create(Constants.id("production"), (Class<Holder<SlimeType>>) (Class<?>) Holder.class);

    public ProductionCategory(IGuiHelper guiHelper) {
        super(TYPE, Component.translatable("jei.productiveslimes.production"), guiHelper.createDrawableItemLike(ModBlocks.SLIME_PEN), 160, 70);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Holder<SlimeType> type, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 5).setStandardSlotBackground().add(ModItems.slimeJar(type, 1)).add(ModItems.slimeJar(type, 4));
        builder.addSlot(RecipeIngredientRole.INPUT, 23, 5).setStandardSlotBackground().addIngredients(net.minecraft.world.item.crafting.Ingredient.of(type.value().food()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 81, 5).setOutputSlotBackground().add(ModItems.slimeball(type, 1)).add(ModItems.slimeball(type, 4));
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, Holder<SlimeType> type, IFocusGroup focuses) {
        builder.addRecipeArrow().setPosition(48, 6);
        SlimeType value = type.value();
        builder.addText(java.util.List.of(
                Component.translatable("jei.productiveslimes.production.time", value.productionTime() / 20).withStyle(ChatFormatting.DARK_GRAY),
                Component.translatable("jei.productiveslimes.production.size").withStyle(ChatFormatting.DARK_GRAY),
                Component.translatable("jei.productiveslimes.production.food").withStyle(ChatFormatting.DARK_GRAY),
                value.naturalSpawn() ? Component.translatable("jei.productiveslimes.production.wild").withStyle(ChatFormatting.DARK_GREEN) : Component.empty()
        ), 160, 44).setPosition(0, 26);
    }
}
