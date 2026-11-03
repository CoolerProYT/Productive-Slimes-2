package com.coolerpromc.productiveslimes.block.entity.custom;

import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.data.component.ModDataComponents;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.entity.custom.ProductiveSlime;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.item.custom.SpeedUpgradeItem;
import com.coolerpromc.productiveslimes.item.custom.SlimeJarItem;
import com.coolerpromc.productiveslimes.screen.custom.SlimePenMenu;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import com.coolerpromc.productiveslimes.util.ContainerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class SlimePenBlockEntity extends BlockEntity implements MenuProvider {
    public static final int UPGRADE_SLOT_COUNT = 2;
    public static final int OUTPUT_SLOT_COUNT = 9;
    public static final float FED_SPEED_MULTIPLIER = 0.5F;
    public static final int BATCHES_PER_FOOD = 4;

    public static final int DATA_TIMER = 0;
    public static final int DATA_DURATION = 1;
    public static final int DATA_STATUS = 2;
    public static final int DATA_FED = 3;
    public static final int DATA_COUNT = 4;

    public static final int STATUS_WORKING = 0;
    public static final int STATUS_EMPTY = 1;
    public static final int STATUS_FULL = 2;

    private static final Component NAME = Component.translatable("container.productiveslimes.slime_pen");

    private final SimpleContainer slime = new PenContainer(1) {
        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {
            return stack.is(ModItems.SLIME_JAR.get()) && SlimeJarItem.isFilled(stack);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }
    };

    private final SimpleContainer food = new PenContainer(1) {
        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {
            return SlimePenBlockEntity.this.isFood(stack);
        }
    };

    private final SimpleContainer upgrades = new PenContainer(UPGRADE_SLOT_COUNT) {
        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {
            return stack.getItem() instanceof SpeedUpgradeItem;
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }
    };

    private final SimpleContainer output = new PenContainer(OUTPUT_SLOT_COUNT) {
        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {
            return false;
        }
    };

    private int timer;
    private int duration;
    private int status = STATUS_EMPTY;
    private int fedBatches;
    private @Nullable ProductiveSlime displaySlime;

    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case DATA_TIMER -> SlimePenBlockEntity.this.timer;
                case DATA_DURATION -> SlimePenBlockEntity.this.duration;
                case DATA_STATUS -> SlimePenBlockEntity.this.status;
                case DATA_FED -> SlimePenBlockEntity.this.fedBatches;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case DATA_TIMER -> SlimePenBlockEntity.this.timer = value;
                case DATA_DURATION -> SlimePenBlockEntity.this.duration = value;
                case DATA_STATUS -> SlimePenBlockEntity.this.status = value;
                case DATA_FED -> SlimePenBlockEntity.this.fedBatches = value;
                default -> {
                }
            }
        }

        @Override
        public int getCount() {
            return DATA_COUNT;
        }
    };

    public SlimePenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SLIME_PEN.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SlimePenBlockEntity pen) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        ItemStack jar = pen.slime.getItem(0);
        Holder<SlimeType> type = jar.get(ModDataComponents.SLIME_TYPE.get());
        if (type == null) {
            if (pen.status != STATUS_EMPTY || pen.timer != 0) {
                pen.status = STATUS_EMPTY;
                pen.timer = 0;
                pen.duration = 0;
                pen.setChanged();
            }
            return;
        }

        if (pen.fedBatches <= 0) {
            ItemStack food = pen.food.getItem(0);
            if (type.value().isFood(food)) {
                food.shrink(1);
                pen.fedBatches = BATCHES_PER_FOOD;
                pen.food.setChanged();
            }
        }

        pen.duration = pen.computeDuration(type.value());
        if (pen.timer < pen.duration) {
            pen.status = STATUS_WORKING;
            pen.timer++;
            if (pen.timer % 20 == 0) {
                pen.setChanged();
            }
            return;
        }

        ItemStack produced = ModItems.slimeball(type, SlimeJarItem.getSlimeSize(jar));
        if (!ContainerUtil.canFit(pen.output, produced)) {
            pen.status = STATUS_FULL;
            return;
        }

        ContainerUtil.insert(pen.output, produced);
        pen.timer = 0;
        if (pen.fedBatches > 0) {
            pen.fedBatches--;
        }
        pen.status = STATUS_WORKING;
        serverLevel.playSound(null, pos, SoundEvents.SLIME_SQUISH_SMALL, SoundSource.BLOCKS, 0.5F, 1.1F + serverLevel.getRandom().nextFloat() * 0.2F);
        pen.markUpdated();
    }

    public int computeDuration(SlimeType type) {
        float multiplier = SpeedUpgradeItem.timeMultiplier(this.upgrades);
        if (this.fedBatches > 0) {
            multiplier *= FED_SPEED_MULTIPLIER;
        }
        return Math.max(20, Math.round(type.productionTime() * multiplier));
    }

    public boolean isFood(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        Holder<SlimeType> type = this.slime.getItem(0).get(ModDataComponents.SLIME_TYPE.get());
        if (type != null) {
            return type.value().isFood(stack);
        }
        return this.level != null && this.level.registryAccess().lookupOrThrow(SlimeType.REGISTRY_KEY).stream().anyMatch(slimeType -> slimeType.isFood(stack));
    }

    public Container getContainer(@Nullable Direction side) {
        if (side == Direction.DOWN) {
            return this.output;
        }
        return side == Direction.UP ? this.slime : this.food;
    }

    public Container[] getContainers() {
        return new Container[]{this.slime, this.food, this.upgrades, this.output};
    }

    public ItemStack getJar() {
        return this.slime.getItem(0);
    }

    public @Nullable ProductiveSlime getDisplaySlime() {
        if (this.displaySlime == null && this.level != null && this.level.isClientSide()) {
            this.displaySlime = ModEntities.PRODUCTIVE_SLIME.get().create(this.level, EntitySpawnReason.LOAD);
            if (this.displaySlime != null) {
                this.displaySlime.setId(-1);
            }
        }
        return this.displaySlime;
    }

    public ItemStack getFood() {
        return this.food.getItem(0);
    }

    public boolean isFed() {
        return this.fedBatches > 0;
    }

    public int getFedBatches() {
        return this.fedBatches;
    }

    public int getStatus() {
        return this.status;
    }

    public float getProgress() {
        return this.duration <= 0 ? 0.0F : Math.min(1.0F, this.timer / (float) this.duration);
    }

    public int getRemainingTicks() {
        return Math.max(0, this.duration - this.timer);
    }

    public boolean tryInsertJar(Player player, ItemStack stack) {
        if (!this.slime.getItem(0).isEmpty() || !this.slime.canPlaceItem(0, stack)) {
            return false;
        }
        this.slime.setItem(0, stack.consumeAndReturn(1, player));
        return true;
    }

    public ItemStack takeJar() {
        return this.slime.removeItemNoUpdate(0);
    }

    private void markUpdated() {
        this.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveCustomOnly(registries);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        if (this.level != null) {
            Containers.dropContents(this.level, pos, this.slime);
            Containers.dropContents(this.level, pos, this.food);
            Containers.dropContents(this.level, pos, this.upgrades);
            Containers.dropContents(this.level, pos, this.output);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerUtil.loadItems(input.childOrEmpty("Slime"), this.slime.getItems());
        ContainerUtil.loadItems(input.childOrEmpty("Food"), this.food.getItems());
        ContainerUtil.loadItems(input.childOrEmpty("Upgrades"), this.upgrades.getItems());
        ContainerUtil.loadItems(input.childOrEmpty("Output"), this.output.getItems());
        this.timer = input.getIntOr("Timer", 0);
        this.duration = input.getIntOr("Duration", 0);
        this.status = input.getIntOr("Status", STATUS_EMPTY);
        this.fedBatches = input.getIntOr("FedBatches", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output.child("Slime"), this.slime.getItems());
        ContainerHelper.saveAllItems(output.child("Food"), this.food.getItems());
        ContainerHelper.saveAllItems(output.child("Upgrades"), this.upgrades.getItems());
        ContainerHelper.saveAllItems(output.child("Output"), this.output.getItems());
        output.putInt("Timer", this.timer);
        output.putInt("Duration", this.duration);
        output.putInt("Status", this.status);
        output.putInt("FedBatches", this.fedBatches);
    }

    @Override
    public Component getDisplayName() {
        return NAME;
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new SlimePenMenu(containerId, inventory, this.slime, this.food, this.upgrades, this.output, this.dataAccess, ContainerLevelAccess.create(this.level, this.worldPosition));
    }

    private class PenContainer extends SimpleContainer {
        PenContainer(int size) {
            super(size);
        }

        @Override
        public void setChanged() {
            super.setChanged();
            SlimePenBlockEntity.this.markUpdated();
        }

        @Override
        public boolean stillValid(Player player) {
            return Container.stillValidBlockEntity(SlimePenBlockEntity.this, player);
        }
    }
}
