package com.coolerpromc.productiveslimes.network;

import com.coolerpromc.productiveslimes.platform.util.PayloadContext;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface HandledCustomPacketPayload extends CustomPacketPayload {
    void handle(PayloadContext context);
}
