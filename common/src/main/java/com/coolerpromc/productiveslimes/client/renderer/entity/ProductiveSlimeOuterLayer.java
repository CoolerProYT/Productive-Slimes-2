package com.coolerpromc.productiveslimes.client.renderer.entity;

import com.coolerpromc.productiveslimes.client.renderer.entity.state.ProductiveSlimeRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.slime.SlimeModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.ARGB;

public class ProductiveSlimeOuterLayer extends RenderLayer<ProductiveSlimeRenderState, SlimeModel> {
    private final SlimeModel model;

    public ProductiveSlimeOuterLayer(RenderLayerParent<ProductiveSlimeRenderState, SlimeModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new SlimeModel(modelSet.bakeLayer(ModelLayers.SLIME_OUTER));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, ProductiveSlimeRenderState state, float yRot, float xRot) {
        boolean appearsGlowingWithInvisibility = state.appearsGlowing() && state.isInvisible;
        if (state.isInvisible && !appearsGlowingWithInvisibility) {
            return;
        }

        int overlayCoords = LivingEntityRenderer.getOverlayCoords(state, 0.0F);
        if (appearsGlowingWithInvisibility) {
            submitNodeCollector.order(1).submitModel(this.model, state, poseStack, RenderTypes.outline(ProductiveSlimeRenderer.TEXTURE), lightCoords, overlayCoords, state.outlineColor, null);
        } else {
            submitNodeCollector.order(1).submitModel(this.model, state, poseStack, RenderTypes.entityTranslucent(ProductiveSlimeRenderer.TEXTURE), lightCoords, overlayCoords, ARGB.opaque(state.color), null, state.outlineColor, null);
        }
    }
}
