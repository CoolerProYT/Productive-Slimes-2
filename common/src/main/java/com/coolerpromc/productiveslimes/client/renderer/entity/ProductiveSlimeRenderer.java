package com.coolerpromc.productiveslimes.client.renderer.entity;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.client.renderer.entity.state.ProductiveSlimeRenderState;
import com.coolerpromc.productiveslimes.entity.custom.ProductiveSlime;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.slime.SlimeModel;
import net.minecraft.client.renderer.entity.AbstractCubeMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class ProductiveSlimeRenderer extends AbstractCubeMobRenderer<ProductiveSlime, ProductiveSlimeRenderState, SlimeModel> {
    public static final Identifier TEXTURE = Constants.id("textures/entity/productive_slime.png");

    public ProductiveSlimeRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel(context.bakeLayer(ModelLayers.SLIME)));
        this.addLayer(new ProductiveSlimeOuterLayer(this, context.getModelSet()));
    }

    @Override
    protected void scale(ProductiveSlimeRenderState state, PoseStack poseStack) {
        this.downscaleSlightly(poseStack);
        super.scale(state, poseStack);
    }

    @Override
    public Identifier getTextureLocation(ProductiveSlimeRenderState state) {
        return TEXTURE;
    }

    @Override
    public ProductiveSlimeRenderState createRenderState() {
        return new ProductiveSlimeRenderState();
    }

    @Override
    public void extractRenderState(ProductiveSlime entity, ProductiveSlimeRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.color = entity.getColor();
    }

    @Override
    protected int getModelTint(ProductiveSlimeRenderState state) {
        return ARGB.opaque(state.color);
    }
}
