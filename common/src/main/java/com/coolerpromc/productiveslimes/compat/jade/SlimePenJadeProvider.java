package com.coolerpromc.productiveslimes.compat.jade;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.block.entity.custom.SlimePenBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum SlimePenJadeProvider implements IBlockComponentProvider {
    INSTANCE;

    static final Identifier UID = Constants.id("slime_pen");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        if (!data.contains(Data.STATUS)) {
            return;
        }
        switch (data.getIntOr(Data.STATUS, SlimePenBlockEntity.STATUS_EMPTY)) {
            case SlimePenBlockEntity.STATUS_EMPTY -> tooltip.add(Component.translatable("jade.productiveslimes.pen.empty").withStyle(ChatFormatting.YELLOW));
            case SlimePenBlockEntity.STATUS_FULL -> tooltip.add(Component.translatable("jade.productiveslimes.pen.full").withStyle(ChatFormatting.RED));
            default -> tooltip.add(Component.translatable("jade.productiveslimes.pen.next", (data.getIntOr(Data.REMAINING, 0) + 19) / 20));
        }
        int fedBatches = data.getIntOr(Data.FED, 0);
        if (fedBatches > 0) {
            tooltip.add(Component.translatable("jade.productiveslimes.pen.fed", fedBatches).withStyle(ChatFormatting.GREEN));
        }
        int food = data.getIntOr(Data.FOOD, 0);
        if (food > 0) {
            tooltip.add(Component.translatable("jade.productiveslimes.pen.food", food));
        }
    }

    @Override
    public Identifier getUid() {
        return UID;
    }

    public enum Data implements IServerDataProvider<BlockAccessor> {
        INSTANCE;

        static final String STATUS = "Status";
        static final String REMAINING = "Remaining";
        static final String FED = "FedBatches";
        static final String FOOD = "Food";

        @Override
        public void appendServerData(CompoundTag data, BlockAccessor accessor) {
            if (accessor.getBlockEntity() instanceof SlimePenBlockEntity pen) {
                data.putInt(STATUS, pen.getStatus());
                data.putInt(REMAINING, pen.getRemainingTicks());
                data.putInt(FED, pen.getFedBatches());
                data.putInt(FOOD, pen.getFood().getCount());
            }
        }

        @Override
        public Identifier getUid() {
            return UID;
        }
    }
}
