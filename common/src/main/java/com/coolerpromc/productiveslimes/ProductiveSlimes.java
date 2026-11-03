package com.coolerpromc.productiveslimes;

import com.coolerpromc.productiveslimes.advancement.ModCriteriaTriggers;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.block.entity.custom.SlimePenBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.custom.SlimeSqueezerBlockEntity;
import com.coolerpromc.productiveslimes.data.component.ModDataComponents;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.ModEntityDataSerializers;
import com.coolerpromc.productiveslimes.entity.custom.ProductiveSlime;
import com.coolerpromc.productiveslimes.item.ModCreativeTabs;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.network.HandledCustomPacketPayload;
import com.coolerpromc.productiveslimes.platform.Services;
import com.coolerpromc.productiveslimes.screen.ModMenuTypes;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import com.coolerpromc.productiveslimes.worldgen.SlimyLandSurface;
import com.mojang.serialization.Codec;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jspecify.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ProductiveSlimes {
    public static void init() {
        ModDataComponents.load();
        ModCriteriaTriggers.load();
        ModEntityDataSerializers.load();
        ModBlocks.load();
        ModItems.load();
        ModEntities.load();
        ModBlockEntities.load();
        ModMenuTypes.load();
        ModCreativeTabs.load();
        SlimyLandSurface.load();
    }

    public static void initCapability(){
        registerCapability(ModBlockEntities.SLIME_PEN, SlimePenBlockEntity::getContainer, SlimePenBlockEntity::getContainers);
        registerCapability(ModBlockEntities.SLIME_SQUEEZER, SlimeSqueezerBlockEntity::getContainer, SlimeSqueezerBlockEntity::getContainers);
    }

    public static void initEntityAttribute(){
        registerEntityAttribute(ModEntities.PRODUCTIVE_SLIME.get(), ProductiveSlime.createAttributes().build());
    }

    public static void initSpawnPlacement(){
        registerSpawnPlacement(ModEntities.PRODUCTIVE_SLIME.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ProductiveSlime::checkSpawnRules);
    }

    public static void initBiomeModifier(){

    }

    public static void initDatapackRegistry(){
        registerDatapackRegistry(SlimeType.REGISTRY_KEY, SlimeType.DIRECT_CODEC, SlimeType.DIRECT_CODEC);
    }

    public static void initPayloadType(){

    }

    public static void initReloadListener(){

    }

    public static void initCommand(){

    }

    public static void onDatapackSync(ServerPlayer player){

    }

    private static <T extends BlockEntity> void registerCapability(Supplier<BlockEntityType<T>> type, BiFunction<T, @Nullable Direction, Container> provider, Function<T, Container[]> containers){
        Services.CAPABILITIES.registerBlockEntityItemStorage(type, provider, containers);
    }

    private static void registerEntityAttribute(EntityType<? extends LivingEntity> entityType, AttributeSupplier supplier){
        Services.REGISTRY.registerEntityAttribute(entityType, supplier);
    }

    private static <T extends Mob> void registerSpawnPlacement(EntityType<T> entityType, SpawnPlacementType placementType, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate){
        Services.REGISTRY.registerSpawnPlacement(entityType, placementType, heightmap, predicate);
    }

    private static <T> void registerDatapackRegistry(ResourceKey<Registry<T>> resourceKey, Codec<T> serverCodec, Codec<T> clientCodec){
        Services.REGISTRY.registerDatapackRegistry(resourceKey, serverCodec, clientCodec);
    }

    private static <T extends HandledCustomPacketPayload> void registerClientboundPayload(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec){
        Services.REGISTRY.registerClientboundPayload(type, streamCodec);
    }
}
