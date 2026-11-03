package com.coolerpromc.productiveslimes.item.custom;

import com.coolerpromc.productiveslimes.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.stream.IntStream;

public class GuideBookItem extends Item {
    public static final int PAGE_COUNT = 12;

    public static Runnable openScreen = () -> {
    };

    public GuideBookItem(Properties properties) {
        super(properties);
    }

    public static List<Component> pages() {
        return IntStream.rangeClosed(1, PAGE_COUNT).<Component>mapToObj(page -> Component.translatable("guide." + Constants.MODID + ".page." + page)).toList();
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            openScreen.run();
        }
        return InteractionResult.SUCCESS;
    }
}
