package com.coolerpromc.productiveslimes.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.jspecify.annotations.Nullable;

public class SlimePenRenderState extends BlockEntityRenderState {
    public @Nullable EntityRenderState slime;
    public float scale;
}
