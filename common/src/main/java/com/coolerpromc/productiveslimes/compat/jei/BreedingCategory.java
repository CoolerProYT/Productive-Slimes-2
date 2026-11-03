package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.entity.custom.ProductiveSlime;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.slime.Breeding;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BreedingCategory extends AbstractRecipeCategory<BreedingRecipe> {
    public static final IRecipeType<BreedingRecipe> TYPE = IRecipeType.create(Constants.id("breeding"), BreedingRecipe.class);

    private static final int WIDTH = 160;
    private static final int HEIGHT = 78;
    private static final int PARENT_A_X = 1;
    private static final int PARENT_B_X = 37;
    private static final int PARENT_Y = 1;
    private static final int FOOD_Y = 23;
    private static final int CATALYST_X = 75;
    private static final int CATALYST_Y = 12;
    private static final int ARROW_X = 98;
    private static final int CHILD_X = 130;
    private static final int CHILD_Y = 8;
    private static final int TEXT_Y = 46;

    public BreedingCategory(IGuiHelper guiHelper) {
        super(TYPE, Component.translatable("jei.productiveslimes.breeding"), guiHelper.createDrawableItemLike(ModItems.PRODUCTIVE_SLIME_SPAWN_EGG), WIDTH, HEIGHT);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BreedingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, PARENT_A_X, PARENT_Y).setStandardSlotBackground()
                .add(ModItems.spawnEgg(recipe.parentA()))
                .addRichTooltipCallback((view, tooltip) -> parentTooltip(tooltip));
        builder.addSlot(RecipeIngredientRole.INPUT, PARENT_B_X, PARENT_Y).setStandardSlotBackground()
                .add(ModItems.spawnEgg(recipe.parentB()))
                .addRichTooltipCallback((view, tooltip) -> parentTooltip(tooltip));

        builder.addSlot(RecipeIngredientRole.INPUT, PARENT_A_X, FOOD_Y).setStandardSlotBackground()
                .add(Ingredient.of(recipe.parentA().value().food()))
                .addRichTooltipCallback((view, tooltip) -> foodTooltip(tooltip, recipe.parentA()));
        builder.addSlot(RecipeIngredientRole.INPUT, PARENT_B_X, FOOD_Y).setStandardSlotBackground()
                .add(Ingredient.of(recipe.parentB().value().food()))
                .addRichTooltipCallback((view, tooltip) -> foodTooltip(tooltip, recipe.parentB()));

        List<ItemStack> catalysts = recipe.catalyst().map(set -> set.stream().map(Holder::value).map(Block::asItem).map(ItemStack::new).filter(stack -> !stack.isEmpty()).toList()).orElse(List.of());
        if (!catalysts.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.CRAFTING_STATION, CATALYST_X, CATALYST_Y).setStandardSlotBackground()
                    .addItemStacks(catalysts)
                    .addRichTooltipCallback((view, tooltip) -> {
                        tooltip.add(Component.translatable("jei.productiveslimes.breeding.catalyst.tooltip", Breeding.CATALYST_RADIUS).withStyle(ChatFormatting.GRAY));
                        if (recipe.consumeCatalyst()) {
                            tooltip.add(Component.translatable("jei.productiveslimes.breeding.consumed").withStyle(ChatFormatting.RED));
                        }
                    });
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, CHILD_X, CHILD_Y).setOutputSlotBackground()
                .add(ModItems.spawnEgg(recipe.child()))
                .add(ModItems.slimeJar(recipe.child(), 1))
                .addRichTooltipCallback((view, tooltip) -> tooltip.add(Component.translatable("jei.productiveslimes.breeding.baby.tooltip").withStyle(ChatFormatting.GRAY)));
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, BreedingRecipe recipe, IFocusGroup focuses) {
        builder.addRecipePlusSign().setPosition(21, 3);
        builder.addRecipeArrow().setPosition(ARROW_X, CHILD_Y + 5);

        List<FormattedText> lines = new ArrayList<>();
        lines.add(Component.translatable("jei.productiveslimes.breeding.parents", SlimeType.name(recipe.parentA()), SlimeType.name(recipe.parentB())));
        if (recipe.sameType()) {
            lines.add(Component.translatable("jei.productiveslimes.breeding.same_type").withStyle(ChatFormatting.DARK_GRAY));
        } else if (recipe.catalyst().isPresent()) {
            Component catalystName = recipe.catalyst().get().stream().findFirst().map(block -> block.value().getName()).orElse(Component.empty());
            lines.add(Component.translatable(recipe.consumeCatalyst() ? "jei.productiveslimes.breeding.catalyst.consumed" : "jei.productiveslimes.breeding.catalyst", catalystName, Breeding.CATALYST_RADIUS).withStyle(ChatFormatting.DARK_GRAY));
        } else {
            lines.add(Component.translatable("jei.productiveslimes.breeding.no_catalyst").withStyle(ChatFormatting.DARK_GRAY));
        }
        lines.add(Component.translatable("jei.productiveslimes.breeding.requirement", 2, ProductiveSlime.BREED_COOLDOWN / 1200).withStyle(ChatFormatting.DARK_GRAY));
        builder.addText(lines, WIDTH, HEIGHT - TEXT_Y).setPosition(0, TEXT_Y);
    }

    private static void parentTooltip(ITooltipBuilder tooltip) {
        tooltip.add(Component.translatable("jei.productiveslimes.breeding.parent.tooltip").withStyle(ChatFormatting.GRAY));
    }

    private static void foodTooltip(ITooltipBuilder tooltip, Holder<SlimeType> parent) {
        tooltip.add(Component.translatable("jei.productiveslimes.breeding.food.tooltip", SlimeType.name(parent)).withStyle(ChatFormatting.GRAY));
    }
}
