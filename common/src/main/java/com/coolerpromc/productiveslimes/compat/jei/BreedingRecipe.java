package com.coolerpromc.productiveslimes.compat.jei;

import com.coolerpromc.productiveslimes.slime.Breeding;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public record BreedingRecipe(Holder<SlimeType> parentA, Holder<SlimeType> parentB, Optional<HolderSet<Block>> catalyst, boolean consumeCatalyst, Holder<SlimeType> child, boolean sameType) {
    public static BreedingRecipe of(Holder<SlimeType> parentA, Holder<SlimeType> parentB, Breeding breeding, Holder<SlimeType> child) {
        return new BreedingRecipe(parentA, parentB, breeding.catalyst(), breeding.consumeCatalyst(), child, false);
    }

    public static BreedingRecipe sameType(Holder<SlimeType> type) {
        return new BreedingRecipe(type, type, Optional.empty(), false, type, true);
    }
}
