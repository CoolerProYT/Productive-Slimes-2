package com.coolerpromc.productiveslimes.worldgen;

import com.coolerpromc.productiveslimes.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.storage.loot.LootTable;

public class ModStructures {
    public static final ResourceKey<Structure> SLIME_SHRINE = ResourceKey.create(Registries.STRUCTURE, Constants.id("slime_shrine"));
    public static final ResourceKey<StructureSet> SLIME_SHRINES = ResourceKey.create(Registries.STRUCTURE_SET, Constants.id("slime_shrines"));
    public static final ResourceKey<StructureTemplatePool> SLIME_SHRINE_POOL = ResourceKey.create(Registries.TEMPLATE_POOL, Constants.id("slime_shrine"));
    public static final ResourceKey<LootTable> SLIME_SHRINE_CHEST = ResourceKey.create(Registries.LOOT_TABLE, Constants.id("chests/slime_shrine"));
}
