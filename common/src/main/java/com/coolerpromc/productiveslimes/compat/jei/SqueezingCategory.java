package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.entity.custom.SlimeSqueezerBlockEntity;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;

public class SqueezingCategory extends AbstractRecipeCategory<Holder<SlimeType>> {
    @SuppressWarnings("unchecked")
    public static final IRecipeType<Holder<SlimeType>> TYPE = IRecipeType.create(Constants.id("squeezing"), (Class<Holder<SlimeType>>) (Class<?>) Holder.class);

    public SqueezingCategory(IGuiHelper guiHelper) {
        super(TYPE, Component.translatable("jei.productiveslimes.squeezing"), guiHelper.createDrawableItemLike(ModBlocks.SLIME_SQUEEZER), 82, 26);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Holder<SlimeType> type, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 5).setStandardSlotBackground().add(ModItems.slimeball(type, 1));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 5).setOutputSlotBackground().add(type.value().createSqueezeResult());
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, Holder<SlimeType> type, IFocusGroup focuses) {
        builder.addAnimatedRecipeArrow(SlimeSqueezerBlockEntity.SQUEEZE_TIME).setPosition(26, 5);
    }
}
