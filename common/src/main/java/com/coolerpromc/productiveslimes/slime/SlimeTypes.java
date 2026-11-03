package com.coolerpromc.productiveslimes.slime;

import com.coolerpromc.productiveslimes.Constants;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;

import java.util.List;
import java.util.Optional;

public class SlimeTypes {
    public static final ResourceKey<SlimeType> SLIME = key("slime");
    public static final ResourceKey<SlimeType> DIRT = key("dirt");
    public static final ResourceKey<SlimeType> STONE = key("stone");
    public static final ResourceKey<SlimeType> SAND = key("sand");
    public static final ResourceKey<SlimeType> OAK = key("oak");

    public static final ResourceKey<SlimeType> GRAVEL = key("gravel");
    public static final ResourceKey<SlimeType> MUD = key("mud");
    public static final ResourceKey<SlimeType> CLAY = key("clay");
    public static final ResourceKey<SlimeType> MOSS = key("moss");
    public static final ResourceKey<SlimeType> OAK_LEAVES = key("oak_leaves");
    public static final ResourceKey<SlimeType> CACTUS = key("cactus");
    public static final ResourceKey<SlimeType> SNOW = key("snow");
    public static final ResourceKey<SlimeType> RED_SAND = key("red_sand");
    public static final ResourceKey<SlimeType> ANDESITE = key("andesite");
    public static final ResourceKey<SlimeType> GRANITE = key("granite");
    public static final ResourceKey<SlimeType> DIORITE = key("diorite");
    public static final ResourceKey<SlimeType> CALCITE = key("calcite");
    public static final ResourceKey<SlimeType> TUFF = key("tuff");
    public static final ResourceKey<SlimeType> DEEPSLATE = key("deepslate");
    public static final ResourceKey<SlimeType> DRIPSTONE = key("dripstone");
    public static final ResourceKey<SlimeType> COAL = key("coal");

    public static final ResourceKey<SlimeType> ICE = key("ice");
    public static final ResourceKey<SlimeType> COPPER = key("copper");
    public static final ResourceKey<SlimeType> NETHERRACK = key("netherrack");
    public static final ResourceKey<SlimeType> PRISMARINE = key("prismarine");
    public static final ResourceKey<SlimeType> BROWN_MUSHROOM = key("brown_mushroom");
    public static final ResourceKey<SlimeType> RED_MUSHROOM = key("red_mushroom");

    public static final ResourceKey<SlimeType> IRON = key("iron");
    public static final ResourceKey<SlimeType> SOUL_SAND = key("soul_sand");
    public static final ResourceKey<SlimeType> SOUL_SOIL = key("soul_soil");
    public static final ResourceKey<SlimeType> BLACKSTONE = key("blackstone");
    public static final ResourceKey<SlimeType> BASALT = key("basalt");
    public static final ResourceKey<SlimeType> MAGMA = key("magma");

    public static final ResourceKey<SlimeType> GOLD = key("gold");
    public static final ResourceKey<SlimeType> LAPIS = key("lapis");
    public static final ResourceKey<SlimeType> REDSTONE = key("redstone");
    public static final ResourceKey<SlimeType> QUARTZ = key("quartz");
    public static final ResourceKey<SlimeType> GLOWSTONE = key("glowstone");
    public static final ResourceKey<SlimeType> OBSIDIAN = key("obsidian");

    public static final ResourceKey<SlimeType> AMETHYST = key("amethyst");
    public static final ResourceKey<SlimeType> END_STONE = key("end_stone");
    public static final ResourceKey<SlimeType> DIAMOND = key("diamond");

    public static final ResourceKey<SlimeType> NETHERITE = key("netherite");

    public static final ResourceKey<SlimeType> DEFAULT = SLIME;

    public static ResourceKey<SlimeType> key(String name) {
        return ResourceKey.create(SlimeType.REGISTRY_KEY, Constants.id(name));
    }

    public static Holder<SlimeType> getDefault(HolderLookup.Provider access) {
        HolderLookup.RegistryLookup<SlimeType> lookup = access.lookupOrThrow(SlimeType.REGISTRY_KEY);
        return lookup.get(DEFAULT).<Holder<SlimeType>>map(holder -> holder)
                .or(() -> lookup.listElements().findFirst())
                .orElseThrow(() -> new IllegalStateException("No slime types are loaded"));
    }

    public static Optional<Holder<SlimeType>> randomNaturalSpawn(RegistryAccess access, RandomSource random) {
        Registry<SlimeType> registry = access.lookupOrThrow(SlimeType.REGISTRY_KEY);
        List<Holder<SlimeType>> natural = registry.listElements().filter(holder -> holder.value().naturalSpawn()).<Holder<SlimeType>>map(holder -> holder).toList();
        if (natural.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(natural.get(random.nextInt(natural.size())));
    }
}
