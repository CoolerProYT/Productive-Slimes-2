package com.coolerpromc.productiveslimes;

import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.client.renderer.blockentity.SlimePenRenderer;
import com.coolerpromc.productiveslimes.item.custom.GuideBookItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import com.coolerpromc.productiveslimes.client.renderer.entity.ProductiveSlimeRenderer;
import com.coolerpromc.productiveslimes.client.tint.SlimeTypeTintSource;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.screen.ModMenuTypes;
import com.coolerpromc.productiveslimes.screen.custom.SlimePenScreen;
import com.coolerpromc.productiveslimes.screen.custom.SlimeSqueezerScreen;

import com.coolerpromc.productiveslimes.network.HandledCustomPacketPayload;
import com.coolerpromc.productiveslimes.platform.ServicesClient;
import com.coolerpromc.productiveslimes.platform.services.client.IRegistryHelper;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.Supplier;

public class ProductiveSlimesClient {
    public static void init(){
        GuideBookItem.openScreen = () -> Minecraft.getInstance().gui.setScreen(new BookViewScreen(new BookViewScreen.BookAccess(GuideBookItem.pages())));
    }

    public static void initAll(){
        init();
        initRenderer();
        initModelLayer();
        initGuiLayer();
        initItemTintSource();
        initItemCondition();
        initItemSelect();
        initMenuScreen();
        initSpecialModelRenderer();
        initClientPayloadHandler();
        initClientReloadListener();
    }

    public static void initClientReloadListener(){

    }

    public static void initMenuScreen(){
        registerMenuScreen(ModMenuTypes.SLIME_PEN.get(), SlimePenScreen::new);
        registerMenuScreen(ModMenuTypes.SLIME_SQUEEZER.get(), SlimeSqueezerScreen::new);
    }

    public static void initRenderer(){
        registerEntityRenderer(ModEntities.PRODUCTIVE_SLIME.get(), ProductiveSlimeRenderer::new);
        registerBlockEntityRenderer(ModBlockEntities.SLIME_PEN.get(), SlimePenRenderer::new);
    }

    public static void initModelLayer(){

    }

    public static void initGuiLayer(){

    }

    public static void initItemTintSource(){
        registerItemTintSource(Constants.id("slime_type"), SlimeTypeTintSource.MAP_CODEC);
    }

    public static void initItemCondition(){

    }

    public static void initItemSelect(){

    }

    public static void initSpecialModelRenderer(){

    }

    public static void initClientPayloadHandler(){
    }

    public static void appendTooltip(ItemStack stack, List<Component> lines){
    }

    private static <T extends Entity> void registerEntityRenderer(EntityType<T> entityType, EntityRendererProvider<T> provider){
        ServicesClient.REGISTRY.registerEntityRenderer(entityType, provider);
    }

    private static void registerEntityModelLayer(ModelLayerLocation layer, Supplier<LayerDefinition> definition) {
        ServicesClient.REGISTRY.registerEntityModelLayer(layer, definition);
    }

    private static void registerGuiLayer(Identifier id, IRegistryHelper.ModGuiLayer layer){
        ServicesClient.REGISTRY.registerGuiLayer(id, layer);
    }

    private static void registerItemTintSource(Identifier id, MapCodec<? extends ItemTintSource> mapCodec){
        ServicesClient.REGISTRY.registerItemTintSource(id, mapCodec);
    }

    private static void registerItemCondition(Identifier id, MapCodec<? extends ConditionalItemModelProperty> mapCodec){
        ServicesClient.REGISTRY.registerItemCondition(id, mapCodec);
    }

    private static void registerItemSelect(Identifier id, SelectItemModelProperty.Type<?, ?> type){
        ServicesClient.REGISTRY.registerItemSelect(id, type);
    }

    private static <T extends BlockEntity, S extends BlockEntityRenderState> void registerBlockEntityRenderer(BlockEntityType<? extends T> blockEntityType, BlockEntityRendererProvider<T, S> provider){
        ServicesClient.REGISTRY.registerBlockEntityRenderer(blockEntityType, provider);
    }

    private static <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerMenuScreen(MenuType<? extends M> menuType, MenuScreens.ScreenConstructor<M, @NonNull U> screenConstructor){
        ServicesClient.REGISTRY.registerMenuScreen(menuType, screenConstructor);
    }

    private static void registerSpecialModelRenderer(Identifier id, MapCodec<? extends SpecialModelRenderer.Unbaked<?>> mapCodec){
        ServicesClient.REGISTRY.registerSpecialModelRenderer(id, mapCodec);
    }

    private static <T extends HandledCustomPacketPayload> void registerClientPayloadReceiver(CustomPacketPayload.Type<T> type){
        ServicesClient.REGISTRY.registerClientPayloadReceiver(type);
    }

    private static void registerClientReloadListener(Identifier id, PreparableReloadListener listener){
        ServicesClient.REGISTRY.registerClientReloadListener(id, listener);
    }
}
