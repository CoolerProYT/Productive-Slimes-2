package com.coolerpromc.productiveslimes;

import com.coolerpromc.productiveslimes.platform.services.IRegistryHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import com.coolerpromc.productiveslimes.platform.NeoForgeRegistryHelper;
import com.coolerpromc.productiveslimes.platform.Services;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@Mod(Constants.MODID)
@EventBusSubscriber(modid = Constants.MODID)
public class NeoForgeProductiveSlimes {
    public NeoForgeProductiveSlimes(IEventBus eventBus) {
        ProductiveSlimes.init();
        NeoForgeRegistryHelper.register(eventBus);
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        ProductiveSlimes.initEntityAttribute();
        Services.REGISTRY.applyEntityAttributeRegistrations(event::put);
    }

    @SubscribeEvent
    public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        ProductiveSlimes.initSpawnPlacement();
        Services.REGISTRY.applySpawnPlacementRegistrations(new IRegistryHelper.SpawnPlacementRegistrar() {
            @Override
            public <T extends Mob> void register(EntityType<T> entityType, SpawnPlacementType placementType, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate) {
                event.register(entityType, placementType, heightmap, predicate, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            }
        });
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        ProductiveSlimes.initCapability();
        Services.CAPABILITIES.applyRegistrations(event);
    }

    @SubscribeEvent
    public static void onDataPackRegistryNewRegistry(DataPackRegistryEvent.NewRegistry event) {
        ProductiveSlimes.initDatapackRegistry();
        Services.REGISTRY.applyDatapackRegistryRegistrations(event::dataPackRegistry);
    }

    @SubscribeEvent
    public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        ProductiveSlimes.initPayloadType();
        PayloadRegistrar registrar = event.registrar("1");
        Services.REGISTRY.applyClientboundPayloadRegistrations(registrar::playToClient);
    }

    @SubscribeEvent
    public static void onAddServerReloadListeners(AddServerReloadListenersEvent event) {
        ProductiveSlimes.initReloadListener();
        Services.REGISTRY.applyServerReloadListenerRegistrations(event::addListener);
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ProductiveSlimes.initCommand();
        Services.REGISTRY.applyCommandRegistrations(event.getDispatcher(), event.getBuildContext());
    }

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        event.getRelevantPlayers().forEach(ProductiveSlimes::onDatapackSync);
    }
}
