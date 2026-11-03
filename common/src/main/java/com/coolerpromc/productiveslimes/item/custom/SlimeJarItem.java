package com.coolerpromc.productiveslimes.item.custom;

import com.coolerpromc.productiveslimes.advancement.ModCriteriaTriggers;
import com.coolerpromc.productiveslimes.data.component.ModDataComponents;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.custom.ProductiveSlime;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.function.Consumer;

public class SlimeJarItem extends Item {
    public SlimeJarItem(Properties properties) {
        super(properties);
    }

    public static boolean isFilled(ItemStack stack) {
        return stack.has(ModDataComponents.SLIME_TYPE.get());
    }

    public static int getSlimeSize(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.SLIME_SIZE.get(), 1);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity target, InteractionHand hand) {
        if (isFilled(itemStack) || !(target instanceof ProductiveSlime slime) || !slime.isAlive()) {
            return InteractionResult.PASS;
        }

        if (player.level().isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        ItemStack filled = ModItems.slimeJar(slime.getSlimeType(), slime.getSize());
        if (slime.hasCustomName()) {
            filled.set(net.minecraft.core.component.DataComponents.CUSTOM_NAME, slime.getCustomName());
        }
        player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, filled, false));
        if (player instanceof ServerPlayer serverPlayer) {
            ModCriteriaTriggers.CAPTURE_SLIME.get().trigger(serverPlayer, slime.getSlimeType(), slime.getSize());
        }
        slime.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
        slime.gameEvent(GameEvent.ENTITY_INTERACT, player);
        slime.discard();
        return InteractionResult.SUCCESS_SERVER;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Holder<SlimeType> type = stack.get(ModDataComponents.SLIME_TYPE.get());
        if (type == null) {
            return InteractionResult.PASS;
        }

        Level level = context.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.SUCCESS;
        }

        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        ProductiveSlime slime = ModEntities.PRODUCTIVE_SLIME.get().create(serverLevel, EntitySpawnReason.BUCKET);
        if (slime == null) {
            return InteractionResult.FAIL;
        }

        slime.setSlimeType(type);
        slime.setSize(getSlimeSize(stack), true);
        slime.setPersistenceRequired();
        if (stack.has(net.minecraft.core.component.DataComponents.CUSTOM_NAME)) {
            slime.setCustomName(stack.getHoverName());
        }
        slime.snapTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, context.getRotation(), 0.0F);
        serverLevel.addFreshEntity(slime);
        level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.NEUTRAL, 1.0F, 1.0F);
        level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, pos);

        Player player = context.getPlayer();
        if (player == null || !player.hasInfiniteMaterials()) {
            stack.shrink(1);
            ItemStack empty = ModItems.SLIME_JAR.toStack();
            if (player == null) {
                return InteractionResult.SUCCESS_SERVER;
            }
            if (stack.isEmpty()) {
                player.setItemInHand(context.getHand(), empty);
            } else if (!player.addItem(empty)) {
                player.spawnAtLocation(serverLevel, empty);
            }
        }
        return InteractionResult.SUCCESS_SERVER;
    }

    @Override
    public Component getName(ItemStack itemStack) {
        Holder<SlimeType> type = itemStack.get(ModDataComponents.SLIME_TYPE.get());
        if (type != null) {
            return Component.translatable("item.productiveslimes.slime_jar.filled", SlimeType.name(type));
        }
        return super.getName(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        if (isFilled(itemStack)) {
            builder.accept(Component.translatable("tooltip.productiveslimes.slime_size", getSlimeSize(itemStack), ProductiveSlime.MAX_SLIME_SIZE).withStyle(ChatFormatting.GRAY));
            builder.accept(Component.translatable("tooltip.productiveslimes.slime_jar.release").withStyle(ChatFormatting.DARK_GRAY));
        } else {
            builder.accept(Component.translatable("tooltip.productiveslimes.slime_jar.capture").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
