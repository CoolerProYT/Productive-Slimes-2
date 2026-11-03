package com.coolerpromc.productiveslimes.slime;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public record Breeding(ResourceKey<SlimeType> parentA, ResourceKey<SlimeType> parentB, Optional<HolderSet<Block>> catalyst, boolean consumeCatalyst) {
    public static final int CATALYST_RADIUS = 2;

    public static final Codec<Breeding> CODEC = RecordCodecBuilder.create(i -> i.group(
            ResourceKey.codec(SlimeType.REGISTRY_KEY).fieldOf("parent_a").forGetter(Breeding::parentA),
            ResourceKey.codec(SlimeType.REGISTRY_KEY).fieldOf("parent_b").forGetter(Breeding::parentB),
            RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("catalyst").forGetter(Breeding::catalyst),
            Codec.BOOL.optionalFieldOf("consume_catalyst", false).forGetter(Breeding::consumeCatalyst)
    ).apply(i, Breeding::new));

    public boolean matchesParents(ResourceKey<SlimeType> a, ResourceKey<SlimeType> b) {
        return (this.parentA.equals(a) && this.parentB.equals(b)) || (this.parentA.equals(b) && this.parentB.equals(a));
    }

    public boolean isCatalyst(BlockState state) {
        return this.catalyst.map(state::is).orElse(false);
    }
}
