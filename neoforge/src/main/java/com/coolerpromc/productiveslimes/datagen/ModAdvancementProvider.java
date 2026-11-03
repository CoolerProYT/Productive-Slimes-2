package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.advancement.ModCriteriaTriggers;
import com.coolerpromc.productiveslimes.advancement.SlimeTrigger;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider implements AdvancementSubProvider {
    private Consumer<AdvancementHolder> output;

    public static AdvancementProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        return new AdvancementProvider(output, lookupProvider, List.of(new ModAdvancementProvider()));
    }

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> output) {
        this.output = output;
        AdvancementHolder root = Advancement.Builder.advancement()
                .display(ModItems.GUIDE_BOOK.get(), title("root"), description("root"), Constants.id("block/slimy_stone"), AdvancementType.TASK, false, false, false)
                .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
                .rewards(AdvancementRewards.Builder.function(Constants.id("give_guide_book")))
                .save(this.output, id("root"));

        AdvancementHolder crystal = Advancement.Builder.advancement()
                .parent(Constants.id("main/slimy_land"))
                .display(ModItems.SLIME_CRYSTAL.get(), title("slime_crystal"), description("slime_crystal"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("has_slime_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SLIME_CRYSTAL.get()))
                .save(this.output, id("slime_crystal"));

        AdvancementHolder capture = slime(crystal, "capture_slime", ModItems.SLIME_JAR.get(), AdvancementType.TASK,
                SlimeTrigger.TriggerInstance.any(ModCriteriaTriggers.CAPTURE_SLIME));
        slime(capture, "big_slime", Items.SLIME_BLOCK, AdvancementType.GOAL,
                SlimeTrigger.TriggerInstance.minSize(ModCriteriaTriggers.CAPTURE_SLIME, 4));

        AdvancementHolder pen = obtain(capture, "slime_pen", ModBlocks.SLIME_PEN.get(), AdvancementType.TASK);
        AdvancementHolder squeezer = obtain(pen, "slime_squeezer", ModBlocks.SLIME_SQUEEZER.get(), AdvancementType.TASK);
        AdvancementHolder speed = obtain(squeezer, "speed_upgrade", ModItems.SPEED_UPGRADE.get(), AdvancementType.TASK);
        obtain(speed, "advanced_speed_upgrade", ModItems.ADVANCED_SPEED_UPGRADE.get(), AdvancementType.GOAL);

        AdvancementHolder breed = slime(capture, "breed_slime", Items.SLIME_BALL, AdvancementType.TASK,
                SlimeTrigger.TriggerInstance.any(ModCriteriaTriggers.BREED_SLIME));
        AdvancementHolder tier3 = slime(breed, "breed_tier_3", Items.IRON_INGOT, AdvancementType.TASK,
                SlimeTrigger.TriggerInstance.minTier(ModCriteriaTriggers.BREED_SLIME, 3));
        AdvancementHolder tier5 = slime(tier3, "breed_tier_5", Items.DIAMOND, AdvancementType.GOAL,
                SlimeTrigger.TriggerInstance.minTier(ModCriteriaTriggers.BREED_SLIME, 5));
        slime(tier5, "breed_tier_6", Items.NETHERITE_SCRAP, AdvancementType.CHALLENGE,
                SlimeTrigger.TriggerInstance.minTier(ModCriteriaTriggers.BREED_SLIME, 6));
    }

    private AdvancementHolder obtain(AdvancementHolder parent, String name, ItemLike item, AdvancementType type) {
        return Advancement.Builder.advancement()
                .parent(parent)
                .display(item, title(name), description(name), null, type, true, true, false)
                .addCriterion("has_" + name, InventoryChangeTrigger.TriggerInstance.hasItems(item))
                .save(this.output, id(name));
    }

    private AdvancementHolder slime(AdvancementHolder parent, String name, ItemLike icon, AdvancementType type, Criterion<SlimeTrigger.TriggerInstance> criterion) {
        return Advancement.Builder.advancement()
                .parent(parent)
                .display(icon, title(name), description(name), null, type, true, true, false)
                .addCriterion(name, criterion)
                .save(this.output, id(name));
    }

    private static Component title(String name) {
        return Component.translatable("advancements." + Constants.MODID + "." + name + ".title");
    }

    private static Component description(String name) {
        return Component.translatable("advancements." + Constants.MODID + "." + name + ".description");
    }

    private static String id(String name) {
        return Constants.MODID + ":main/" + name;
    }
}
