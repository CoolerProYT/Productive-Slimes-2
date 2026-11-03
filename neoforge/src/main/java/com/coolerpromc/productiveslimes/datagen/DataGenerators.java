package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = Constants.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModModelProvider::new);
        event.createProvider(ModLanguageProvider::new);
        event.createProvider(ModBlockTagsProvider::new);
        event.createProvider(ModItemTagsProvider::new);
        event.createWorldRegistryObjects(new RegistrySetBuilder()
                .add(SlimeType.REGISTRY_KEY, ModSlimeTypeProvider::bootstrap));
        event.createReloadableRegistryObjects(new RegistrySetBuilder()
                .add(Registries.LOOT_TABLE, ModLootTableProvider.create())
                .add(Registries.ADVANCEMENT, ModAdvancementProvider.create())
                .add(ModRecipeProvider.create()));
    }
}
