package com.coolerpromc.productiveslimes.client.renderer.entity;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.client.renderer.entity.state.ProductiveSlimeRenderState;
import com.coolerpromc.productiveslimes.entity.custom.ProductiveSlime;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.slime.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;

public class ProductiveSlimeRenderer extends MobRenderer<ProductiveSlime, ProductiveSlimeRenderState, SlimeModel> {
    public static final Identifier TEXTURE = Constants.id("textures/entity/productive_slime.png");

    public ProductiveSlimeRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel(context.bakeLayer(ModelLayers.SLIME)), 0.25F);
        this.addLayer(new ProductiveSlimeOuterLayer(this, context.getModelSet()));
    }

    @Override
    protected float getShadowRadius(ProductiveSlimeRenderState state) {
        return state.size * 0.25F;
    }

    @Override
    protected void scale(ProductiveSlimeRenderState state, PoseStack poseStack) {
        poseStack.scale(0.999F, 0.999F, 0.999F);
        poseStack.translate(0.0F, 0.001F, 0.0F);
        float size = state.size;
        float squish = state.squish / (size * 0.5F + 1.0F);
        float width = 1.0F / (squish + 1.0F);
        poseStack.scale(width * size, 1.0F / width * size, width * size);
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
        state.squish = Mth.lerp(partialTicks, entity.oSquish, entity.squish);
        state.size = entity.getSize();
        state.color = entity.getColor();
    }

    @Override
    protected int getModelTint(ProductiveSlimeRenderState state) {
        return ARGB.opaque(state.color);
    }
}
