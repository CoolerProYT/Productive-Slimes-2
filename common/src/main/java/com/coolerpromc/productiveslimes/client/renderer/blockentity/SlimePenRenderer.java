package com.coolerpromc.productiveslimes.client.renderer.blockentity;

import com.coolerpromc.productiveslimes.block.custom.SlimePenBlock;
import com.coolerpromc.productiveslimes.block.entity.custom.SlimePenBlockEntity;
import com.coolerpromc.productiveslimes.client.renderer.blockentity.state.SlimePenRenderState;
import com.coolerpromc.productiveslimes.data.component.ModDataComponents;
import com.coolerpromc.productiveslimes.entity.custom.ProductiveSlime;
import com.coolerpromc.productiveslimes.item.custom.SlimeJarItem;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class SlimePenRenderer implements BlockEntityRenderer<SlimePenBlockEntity, SlimePenRenderState> {
    private static final float FLOOR_Y = 3.0F / 16.0F;
    private static final float MIN_WIDTH = 0.34F;
    private static final float MAX_WIDTH = 0.66F;
    private static final float SLIME_WIDTH_PER_SIZE = 0.52F;

    private final EntityRenderDispatcher entityRenderer;

    public SlimePenRenderer(BlockEntityRendererProvider.Context context) {
        this.entityRenderer = context.entityRenderer();
    }

    @Override
    public SlimePenRenderState createRenderState() {
        return new SlimePenRenderState();
    }

    @Override
    public void extractRenderState(SlimePenBlockEntity pen, SlimePenRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(pen, state, partialTicks, cameraPosition, breakProgress);
        state.slime = null;

        ItemStack jar = pen.getJar();
        Holder<SlimeType> type = jar.get(ModDataComponents.SLIME_TYPE.get());
        ProductiveSlime slime = type == null ? null : pen.getDisplaySlime();
        if (slime == null) {
            return;
        }

        int size = Mth.clamp(SlimeJarItem.getSlimeSize(jar), 1, ProductiveSlime.MAX_SLIME_SIZE);
        slime.setSlimeType(type);
        if (slime.getSize() != size) {
            slime.setSize(size, false);
        }
        float yaw = pen.getBlockState().getValue(SlimePenBlock.FACING).toYRot();
        slime.setYRot(yaw);
        slime.yRotO = yaw;
        slime.setYBodyRot(yaw);
        slime.yBodyRotO = yaw;
        slime.setYHeadRot(yaw);
        slime.yHeadRotO = yaw;
        slime.setXRot(0.0F);
        slime.xRotO = 0.0F;

        EntityRenderState slimeState = this.entityRenderer.extractEntity(slime, partialTicks);
        slimeState.lightCoords = state.lightCoords;
        if (slimeState instanceof SlimeRenderState cube && pen.getLevel() != null) {
            float time = pen.getLevel().getGameTime() + partialTicks + (pen.getBlockPos().asLong() & 63L);
            float speed = pen.getStatus() == SlimePenBlockEntity.STATUS_WORKING ? 0.12F : 0.05F;
            cube.squish = Mth.sin(time * speed) * 0.35F;
        }

        float width = Mth.lerp((size - 1) / (float) (ProductiveSlime.MAX_SLIME_SIZE - 1), MIN_WIDTH, MAX_WIDTH);
        state.scale = width / (SLIME_WIDTH_PER_SIZE * size);
        state.slime = slimeState;
    }

    @Override
    public void submit(SlimePenRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (state.slime == null) {
            return;
        }
        poseStack.pushPose();
        poseStack.translate(0.5F, FLOOR_Y, 0.5F);
        poseStack.scale(state.scale, state.scale, state.scale);
        this.entityRenderer.submit(state.slime, camera, 0.0, 0.0, 0.0, poseStack, submitNodeCollector);
        poseStack.popPose();
    }
}
