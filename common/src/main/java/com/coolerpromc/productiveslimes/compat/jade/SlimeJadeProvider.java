package com.coolerpromc.productiveslimes.compat.jade;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.entity.custom.ProductiveSlime;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum SlimeJadeProvider implements IEntityComponentProvider {
    INSTANCE;

    static final Identifier UID = Constants.id("productive_slime");

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (!(accessor.getEntity() instanceof ProductiveSlime slime)) {
            return;
        }
        tooltip.add(Component.translatable("jade.productiveslimes.size", slime.getSize(), ProductiveSlime.MAX_SLIME_SIZE));

        CompoundTag data = accessor.getServerData();
        if (!slime.isAdult()) {
            tooltip.add(Component.translatable("jade.productiveslimes.growth", data.getIntOr(Data.GROWTH, 0), ProductiveSlime.FOOD_TO_ADULT));
        }
        if (data.getBooleanOr(Data.IN_LOVE, false)) {
            tooltip.add(Component.translatable("jade.productiveslimes.in_love").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
        int cooldown = data.getIntOr(Data.COOLDOWN, 0);
        if (cooldown > 0) {
            tooltip.add(Component.translatable("jade.productiveslimes.cooldown", cooldown / 20).withStyle(ChatFormatting.GRAY));
        }
        if (data.contains(Data.NEXT_DROP)) {
            tooltip.add(Component.translatable("jade.productiveslimes.next_drop", data.getIntOr(Data.NEXT_DROP, 0) / 20).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public Identifier getUid() {
        return UID;
    }

    public enum Data implements IServerDataProvider<EntityAccessor> {
        INSTANCE;

        static final String GROWTH = "Growth";
        static final String IN_LOVE = "InLove";
        static final String COOLDOWN = "Cooldown";
        static final String NEXT_DROP = "NextDrop";

        @Override
        public void appendServerData(CompoundTag data, EntityAccessor accessor) {
            if (accessor.getEntity() instanceof ProductiveSlime slime) {
                data.putInt(GROWTH, slime.getGrowth());
                data.putBoolean(IN_LOVE, slime.isInLove());
                data.putInt(COOLDOWN, slime.getBreedCooldown());
                data.putInt(NEXT_DROP, Math.max(0, slime.getWildProductionTime() - slime.getProductionTimer()));
            }
        }

        @Override
        public Identifier getUid() {
            return UID;
        }
    }
}
