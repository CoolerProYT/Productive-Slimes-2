package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.data.component.ModDataComponents;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.screen.custom.SlimePenScreen;
import com.coolerpromc.productiveslimes.screen.custom.SlimeSqueezerScreen;
import com.coolerpromc.productiveslimes.slime.SlimeBreeding;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@JeiPlugin
public class ProductiveSlimesJeiPlugin implements IModPlugin {
    @Override
    public Identifier getPluginUid() {
        return Constants.id("jei_plugin");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerFromDataComponentTypes(ModItems.SLIMEBALL.get(), ModDataComponents.SLIME_TYPE.get());
        registration.registerFromDataComponentTypes(ModItems.SLIME_JAR.get(), ModDataComponents.SLIME_TYPE.get());
        registration.registerFromDataComponentTypes(ModItems.PRODUCTIVE_SLIME_SPAWN_EGG.get(), ModDataComponents.SLIME_TYPE.get());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new BreedingCategory(guiHelper), new SqueezingCategory(guiHelper), new ProductionCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addItemStackInfo(new ItemStack(ModItems.SLIME_CRYSTAL.get()), Component.translatable("jei.productiveslimes.info.slime_crystal"));
        registration.addItemStackInfo(new ItemStack(ModBlocks.SLIME_CRYSTAL_ORE.get()), Component.translatable("jei.productiveslimes.info.slime_crystal_ore"));

        HolderLookup.Provider registries = registries();
        if (registries == null) {
            return;
        }
        List<Holder.Reference<SlimeType>> types = SlimeBreeding.sortedTypes(registries);
        registration.addRecipes(BreedingCategory.TYPE, breedingRecipes(registries, types));
        registration.addRecipes(SqueezingCategory.TYPE, types.stream().<Holder<SlimeType>>map(type -> type).toList());
        registration.addRecipes(ProductionCategory.TYPE, types.stream().<Holder<SlimeType>>map(type -> type).toList());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(SqueezingCategory.TYPE, ModBlocks.SLIME_SQUEEZER);
        registration.addCraftingStation(ProductionCategory.TYPE, ModBlocks.SLIME_PEN);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SlimePenScreen.class, SlimePenScreen.ARROW_X, SlimePenScreen.ARROW_Y, SlimePenScreen.ARROW_WIDTH, SlimePenScreen.ARROW_HEIGHT, ProductionCategory.TYPE);
        registration.addRecipeClickArea(SlimeSqueezerScreen.class, SlimeSqueezerScreen.ARROW_X, SlimeSqueezerScreen.ARROW_Y, SlimeSqueezerScreen.ARROW_WIDTH, SlimeSqueezerScreen.ARROW_HEIGHT, SqueezingCategory.TYPE);
    }

    private static List<BreedingRecipe> breedingRecipes(HolderLookup.Provider registries, List<Holder.Reference<SlimeType>> types) {
        HolderLookup.RegistryLookup<SlimeType> lookup = registries.lookupOrThrow(SlimeType.REGISTRY_KEY);
        List<BreedingRecipe> recipes = new ArrayList<>();
        for (Holder.Reference<SlimeType> child : types) {
            child.value().breeding().ifPresent(breeding -> {
                Optional<Holder.Reference<SlimeType>> parentA = lookup.get(breeding.parentA());
                Optional<Holder.Reference<SlimeType>> parentB = lookup.get(breeding.parentB());
                if (parentA.isPresent() && parentB.isPresent()) {
                    recipes.add(BreedingRecipe.of(parentA.get(), parentB.get(), breeding, child));
                }
            });
        }
        for (Holder.Reference<SlimeType> type : types) {
            recipes.add(BreedingRecipe.sameType(type));
        }
        return recipes;
    }

    private static HolderLookup.Provider registries() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.getConnection() != null) {
            return minecraft.getConnection().registryAccess();
        }
        return minecraft.level == null ? null : minecraft.level.registryAccess();
    }
}
