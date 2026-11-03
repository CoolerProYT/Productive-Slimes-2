package com.coolerpromc.productiveslimes.slime;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class SlimeBreeding {
    private SlimeBreeding() {
    }

    public record Outcome(Holder<SlimeType> child, Optional<BlockPos> catalystPos, boolean consumeCatalyst) {
    }

    public record Rule(Holder<SlimeType> child, Breeding breeding) {
    }

    public static Optional<Outcome> findOutcome(Level level, Holder<SlimeType> a, Holder<SlimeType> b, BlockPos posA, BlockPos posB) {
        Optional<ResourceKey<SlimeType>> keyA = a.unwrapKey();
        Optional<ResourceKey<SlimeType>> keyB = b.unwrapKey();
        if (keyA.isEmpty() || keyB.isEmpty()) {
            return Optional.empty();
        }

        List<Rule> rules = rulesFor(level.registryAccess(), keyA.get(), keyB.get());

        for (Rule rule : rules) {
            if (rule.breeding().catalyst().isEmpty()) {
                continue;
            }
            Optional<BlockPos> catalyst = findCatalyst(level, rule.breeding(), posA).or(() -> findCatalyst(level, rule.breeding(), posB));
            if (catalyst.isPresent()) {
                return Optional.of(new Outcome(rule.child(), catalyst, rule.breeding().consumeCatalyst()));
            }
        }

        for (Rule rule : rules) {
            if (rule.breeding().catalyst().isEmpty()) {
                return Optional.of(new Outcome(rule.child(), Optional.empty(), false));
            }
        }

        if (keyA.get().equals(keyB.get())) {
            return Optional.of(new Outcome(a, Optional.empty(), false));
        }

        return Optional.empty();
    }

    public static List<Rule> rulesFor(RegistryAccess access, ResourceKey<SlimeType> a, ResourceKey<SlimeType> b) {
        List<Rule> rules = new ArrayList<>();
        for (Rule rule : allRules(access)) {
            if (rule.breeding().matchesParents(a, b)) {
                rules.add(rule);
            }
        }
        return rules;
    }

    public static List<Rule> allRules(HolderLookup.Provider access) {
        Optional<? extends HolderLookup.RegistryLookup<SlimeType>> lookup = access.lookup(SlimeType.REGISTRY_KEY);
        if (lookup.isEmpty()) {
            return List.of();
        }

        List<Rule> rules = new ArrayList<>();
        lookup.get().listElements().forEach(holder -> holder.value().breeding().ifPresent(breeding -> rules.add(new Rule(holder, breeding))));
        rules.sort(Comparator.comparing(rule -> SlimeType.idOf(rule.child())));
        return rules;
    }

    public static List<Holder.Reference<SlimeType>> sortedTypes(HolderLookup.Provider access) {
        Optional<? extends HolderLookup.RegistryLookup<SlimeType>> lookup = access.lookup(SlimeType.REGISTRY_KEY);
        if (lookup.isEmpty()) {
            return List.of();
        }
        return lookup.get().listElements()
                .sorted(Comparator.<Holder.Reference<SlimeType>>comparingInt(holder -> holder.value().tier()).thenComparing(holder -> holder.key().identifier()))
                .toList();
    }

    public static Optional<Holder.Reference<SlimeType>> byKey(RegistryAccess access, ResourceKey<SlimeType> key) {
        return access.lookup(SlimeType.REGISTRY_KEY).flatMap(registry -> registry.get(key));
    }

    private static Optional<BlockPos> findCatalyst(Level level, Breeding breeding, BlockPos around) {
        int radius = Breeding.CATALYST_RADIUS;
        for (BlockPos pos : BlockPos.betweenClosed(around.offset(-radius, -1, -radius), around.offset(radius, 1, radius))) {
            BlockState state = level.getBlockState(pos);
            if (breeding.isCatalyst(state)) {
                return Optional.of(pos.immutable());
            }
        }
        return Optional.empty();
    }

    public static Registry<SlimeType> registry(RegistryAccess access) {
        return access.lookupOrThrow(SlimeType.REGISTRY_KEY);
    }
}
