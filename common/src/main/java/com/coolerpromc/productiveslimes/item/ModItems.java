package com.coolerpromc.productiveslimes.item;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.data.component.ModDataComponents;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.item.custom.GuideBookItem;
import com.coolerpromc.productiveslimes.item.custom.SpeedUpgradeItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeJarItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeSpawnEggItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeballItem;
import com.coolerpromc.productiveslimes.platform.Services;
import com.coolerpromc.productiveslimes.platform.util.RegistryHandler;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.TypedEntityData;

public class ModItems {
    public static final RegistryHandler.Items<SlimeballItem> SLIMEBALL = Services.REGISTRY.registerItem("slimeball", SlimeballItem::new);
    public static final RegistryHandler.Items<SlimeJarItem> SLIME_JAR = Services.REGISTRY.registerItem("slime_jar", SlimeJarItem::new, new Item.Properties().stacksTo(16));
    public static final RegistryHandler.Items<SlimeSpawnEggItem> PRODUCTIVE_SLIME_SPAWN_EGG = Services.REGISTRY.registerItem("productive_slime_spawn_egg", SlimeSpawnEggItem::new);
    public static final RegistryHandler.Items<Item> SLIME_CRYSTAL = Services.REGISTRY.registerItem("slime_crystal", Item::new);
    public static final RegistryHandler.Items<GuideBookItem> GUIDE_BOOK = Services.REGISTRY.registerItem("guide_book", GuideBookItem::new, new Item.Properties().stacksTo(1));
    public static final RegistryHandler.Items<SpeedUpgradeItem> SPEED_UPGRADE = Services.REGISTRY.registerItem("speed_upgrade", p -> new SpeedUpgradeItem(0.8F, p), new Item.Properties().stacksTo(16));
    public static final RegistryHandler.Items<SpeedUpgradeItem> ADVANCED_SPEED_UPGRADE = Services.REGISTRY.registerItem("advanced_speed_upgrade", p -> new SpeedUpgradeItem(0.5F, p), new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON));

    public static ItemStack slimeball(Holder<SlimeType> type, int count) {
        ItemStack stack = new ItemStack(SLIMEBALL.get(), count);
        stack.set(ModDataComponents.SLIME_TYPE.get(), type);
        return stack;
    }

    public static ItemStack slimeJar(Holder<SlimeType> type, int size) {
        ItemStack stack = SLIME_JAR.toStack();
        stack.set(ModDataComponents.SLIME_TYPE.get(), type);
        stack.set(ModDataComponents.SLIME_SIZE.get(), size);
        return stack;
    }

    public static ItemStack spawnEgg(Holder<SlimeType> type) {
        ItemStack stack = PRODUCTIVE_SLIME_SPAWN_EGG.toStack();
        CompoundTag tag = new CompoundTag();
        tag.putString("SlimeType", SlimeType.idOf(type).toString());
        stack.set(DataComponents.ENTITY_DATA, TypedEntityData.of(ModEntities.PRODUCTIVE_SLIME.get(), tag));
        stack.set(ModDataComponents.SLIME_TYPE.get(), type);
        return stack;
    }

    public static void load() {
        Constants.LOG.info("Registering items.");
    }
}
