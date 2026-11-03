package com.coolerpromc.productiveslimes.entity.custom;

import com.coolerpromc.productiveslimes.advancement.ModCriteriaTriggers;
import com.coolerpromc.productiveslimes.entity.ModEntityDataSerializers;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.slime.SlimeBreeding;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import com.coolerpromc.productiveslimes.slime.SlimeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import com.coolerpromc.productiveslimes.mixin.SlimeMoveControlInvoker;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Optional;

public class ProductiveSlime extends Slime {
    private static final EntityDataAccessor<Holder<SlimeType>> DATA_SLIME_TYPE = SynchedEntityData.defineId(ProductiveSlime.class, ModEntityDataSerializers.SLIME_TYPE.get());

    public static final int MAX_SLIME_SIZE = 4;
    public static final int FOOD_TO_ADULT = 4;
    public static final int FOOD_PER_SIZE = 4;
    public static final int WILD_PRODUCTION_MULTIPLIER = 2;
    public static final int LOVE_DURATION = 600;
    public static final int BREED_COOLDOWN = 6000;

    private int growth;
    private int inLove;
    private int breedCooldown;
    private int productionTimer;
    private @Nullable UUID loveCause;

    public ProductiveSlime(EntityType<? extends ProductiveSlime> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.ATTACK_DAMAGE, 0.0F)
                .add(Attributes.FOLLOW_RANGE, 16.0F);
    }

    public static boolean checkSpawnRules(EntityType<ProductiveSlime> type, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return Mob.checkMobSpawnRules(type, level, spawnReason, pos, random) && level.getRawBrightness(pos, 0) > 8;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(DATA_SLIME_TYPE, SlimeTypes.getDefault(this.registryAccess()));
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.removeAllGoals(goal -> true);
        this.goalSelector.addGoal(2, new SlimeBreedGoal(this));
        this.goalSelector.addGoal(3, new SlimeTemptGoal(this));
    }

    public Holder<SlimeType> getSlimeType() {
        return this.entityData.get(DATA_SLIME_TYPE);
    }

    public void setSlimeType(Holder<SlimeType> type) {
        this.entityData.set(DATA_SLIME_TYPE, type);
    }

    public int getColor() {
        return this.getSlimeType().value().color();
    }

    public int getGrowth() {
        return this.growth;
    }

    public boolean isAdult() {
        return this.getSize() >= 2;
    }

    public boolean isInLove() {
        return this.inLove > 0;
    }

    public int getBreedCooldown() {
        return this.breedCooldown;
    }

    public int getProductionTimer() {
        return this.productionTimer;
    }

    public int getWildProductionTime() {
        return this.getSlimeType().value().productionTime() * WILD_PRODUCTION_MULTIPLIER;
    }

    public boolean canFallInLove() {
        return this.isAdult() && this.inLove <= 0 && this.breedCooldown <= 0;
    }

    public void resetLove() {
        this.inLove = 0;
    }

    public int getGrowCost() {
        if (!this.isAdult() || this.getSize() >= MAX_SLIME_SIZE) {
            return -1;
        }
        return this.getSize() * FOOD_PER_SIZE;
    }

    @Override
    public void tick() {
        super.tick();

        if (!(this.level() instanceof ServerLevel level) || this.isDeadOrDying()) {
            return;
        }

        if (this.breedCooldown > 0) {
            this.breedCooldown--;
        }

        if (this.inLove > 0) {
            this.inLove--;
            if (this.inLove % 10 == 0) {
                level.sendParticles(ParticleTypes.HEART, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 1, 0.0, 0.0, 0.0, 0.0);
            }
        }

        if (++this.productionTimer >= this.getWildProductionTime()) {
            this.productionTimer = 0;
            this.spawnAtLocation(level, ModItems.slimeball(this.getSlimeType(), this.getSize()));
            this.playSound(SoundEvents.SLIME_SQUISH_SMALL, 0.6F, 1.2F);
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        SlimeType type = this.getSlimeType().value();
        if (!type.isFood(stack)) {
            return super.mobInteract(player, hand);
        }

        if (this.level().isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!this.isAdult()) {
            stack.consume(1, player);
            this.eat(stack);
            if (++this.growth >= FOOD_TO_ADULT) {
                this.growth = 0;
                this.grow();
            }
            return InteractionResult.SUCCESS_SERVER;
        }

        if (player.isSecondaryUseActive()) {
            int cost = this.getGrowCost();
            if (cost < 0) {
                player.sendOverlayMessage(Component.translatable("message.productiveslimes.slime.max_size"));
                return InteractionResult.FAIL;
            }
            if (!player.hasInfiniteMaterials() && stack.getCount() < cost) {
                player.sendOverlayMessage(Component.translatable("message.productiveslimes.slime.need_food", cost, stack.getHoverName()));
                return InteractionResult.FAIL;
            }
            stack.consume(cost, player);
            this.eat(stack);
            this.grow();
            return InteractionResult.SUCCESS_SERVER;
        }

        if (this.canFallInLove()) {
            stack.consume(1, player);
            this.eat(stack);
            this.inLove = LOVE_DURATION;
            this.loveCause = player.getUUID();
            return InteractionResult.SUCCESS_SERVER;
        }

        if (this.breedCooldown > 0) {
            player.sendOverlayMessage(Component.translatable("message.productiveslimes.slime.cooldown", this.breedCooldown / 20));
        }
        return InteractionResult.PASS;
    }

    private void eat(ItemStack food) {
        if (this.level() instanceof ServerLevel level) {
            level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(food.copyWithCount(1))), this.getX(), this.getY() + this.getBbHeight() * 0.5, this.getZ(), 8, 0.2, 0.2, 0.2, 0.05);
        }
        this.playSound(SoundEvents.GENERIC_EAT.value(), 0.6F, 1.2F);
        this.gameEvent(GameEvent.EAT);
    }

    private void grow() {
        this.setSize(this.getSize() + 1, true);
        this.playSound(SoundEvents.SLIME_SQUISH, 1.0F, 0.8F);
        if (this.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY() + this.getBbHeight() * 0.5, this.getZ(), 12, this.getBbWidth() * 0.4, this.getBbHeight() * 0.4, this.getBbWidth() * 0.4, 0.0);
        }
    }

    public Optional<SlimeBreeding.Outcome> breedingOutcomeWith(ProductiveSlime partner) {
        return SlimeBreeding.findOutcome(this.level(), this.getSlimeType(), partner.getSlimeType(), this.blockPosition(), partner.blockPosition());
    }

    public void breedWith(ServerLevel level, ProductiveSlime partner, SlimeBreeding.Outcome outcome) {
        ProductiveSlime child = (ProductiveSlime) this.getType().create(level, EntitySpawnReason.BREEDING);
        if (child == null) {
            return;
        }

        child.setSlimeType(outcome.child());
        child.setSize(1, true);
        child.snapTo((this.getX() + partner.getX()) / 2.0, Math.max(this.getY(), partner.getY()) + 0.2, (this.getZ() + partner.getZ()) / 2.0, this.random.nextFloat() * 360.0F, 0.0F);
        child.setPersistenceRequired();
        level.addFreshEntityWithPassengers(child);

        if (outcome.consumeCatalyst()) {
            outcome.catalystPos().ifPresent(pos -> level.destroyBlock(pos, false));
        }

        Set<UUID> credited = new HashSet<>();
        for (ProductiveSlime parent : List.of(this, partner)) {
            if (parent.loveCause != null && credited.add(parent.loveCause) && level.getPlayerByUUID(parent.loveCause) instanceof ServerPlayer player) {
                ModCriteriaTriggers.BREED_SLIME.get().trigger(player, outcome.child(), 1);
            }
            parent.loveCause = null;
            parent.resetLove();
            parent.breedCooldown = BREED_COOLDOWN;
        }

        level.sendParticles(ParticleTypes.HEART, child.getX(), child.getY() + 0.5, child.getZ(), 7, 0.4, 0.3, 0.4, 0.0);
        level.playSound(null, child.blockPosition(), SoundEvents.SLIME_SQUISH, SoundSource.NEUTRAL, 1.0F, 1.4F);
        ExperienceOrb.award(level, child.position(), this.random.nextInt(7) + 1);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
        if (spawnReason == EntitySpawnReason.NATURAL || spawnReason == EntitySpawnReason.CHUNK_GENERATION) {
            SlimeTypes.randomNaturalSpawn(level.registryAccess(), level.getRandom()).ifPresent(this::setSlimeType);
        }
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnReason, groupData);
        this.setSize(level.getRandom().nextBoolean() ? 1 : 2, true);
        return data;
    }

    @Override
    public void remove(RemovalReason reason) {
        // Productive slimes don't split on death
        if (!this.level().isClientSide() && this.isDeadOrDying()) {
            this.setSize(1, false);
        }
        super.remove(reason);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean killedByPlayer) {
        super.dropCustomDeathLoot(level, source, killedByPlayer);
        this.spawnAtLocation(level, ModItems.slimeball(this.getSlimeType(), this.getSize()));
    }

    @Override
    public boolean removeWhenFarAway(double distSqr) {
        return false;
    }

    @Override
    protected boolean isDealsDamage() {
        return false;
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.NEUTRAL;
    }

    @Override
    protected Component getTypeName() {
        return Component.translatable("entity.productiveslimes.productive_slime.typed", SlimeType.name(this.getSlimeType()));
    }

    @Override
    protected @Nullable ParticleOptions getParticleType() {
        return new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(ModItems.slimeball(this.getSlimeType(), 1)));
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return this.isTiny() ? SoundEvents.SLIME_HURT_SMALL : SoundEvents.SLIME_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return this.isTiny() ? SoundEvents.SLIME_DEATH_SMALL : SoundEvents.SLIME_DEATH;
    }

    @Override
    protected SoundEvent getSquishSound() {
        return this.isTiny() ? SoundEvents.SLIME_SQUISH_SMALL : SoundEvents.SLIME_SQUISH;
    }

    @Override
    protected SoundEvent getJumpSound() {
        return this.isTiny() ? SoundEvents.SLIME_JUMP_SMALL : SoundEvents.SLIME_JUMP;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.store("SlimeType", SlimeType.CODEC, this.getSlimeType());
        output.putInt("Growth", this.growth);
        output.putInt("InLove", this.inLove);
        output.putInt("BreedCooldown", this.breedCooldown);
        output.putInt("ProductionTimer", this.productionTimer);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.read("SlimeType", SlimeType.CODEC).ifPresent(this::setSlimeType);
        this.growth = input.getIntOr("Growth", 0);
        this.inLove = input.getIntOr("InLove", 0);
        this.breedCooldown = input.getIntOr("BreedCooldown", 0);
        this.productionTimer = input.getIntOr("ProductionTimer", 0);
    }

    private void hopToward(double x, double z) {
        double dx = x - this.getX();
        double dz = z - this.getZ();
        float yRot = (float) (net.minecraft.util.Mth.atan2(dz, dx) * (180.0F / Math.PI)) - 90.0F;
        this.setYRot(yRot);
        if (this.getMoveControl() instanceof SlimeMoveControlInvoker control) {
            control.productiveslimes$setDirection(yRot, false);
            control.productiveslimes$setWantedMovement(1.0);
        }
    }

    private static class SlimeBreedGoal extends Goal {
        private static final double SEARCH_RANGE = 8.0;
        private final ProductiveSlime slime;
        private @Nullable ProductiveSlime partner;
        private int timeout;

        SlimeBreedGoal(ProductiveSlime slime) {
            this.slime = slime;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (!this.slime.isInLove()) {
                return false;
            }
            this.partner = this.findPartner();
            return this.partner != null;
        }

        @Override
        public boolean canContinueToUse() {
            return this.partner != null && this.partner.isAlive() && this.partner.isInLove() && this.slime.isInLove() && this.timeout < 200;
        }

        @Override
        public void start() {
            this.timeout = 0;
        }

        @Override
        public void stop() {
            this.partner = null;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (this.partner == null || !this.slime.isInLove() || !this.partner.isInLove()) {
                this.partner = null;
                return;
            }
            this.timeout++;
            this.slime.lookAt(this.partner, 10.0F, 10.0F);
            this.slime.hopToward(this.partner.getX(), this.partner.getZ());

            double reach = (this.slime.getBbWidth() + this.partner.getBbWidth()) * 0.5 + 0.6;
            if (this.slime.distanceToSqr(this.partner) <= reach * reach && this.slime.level() instanceof ServerLevel level) {
                Optional<SlimeBreeding.Outcome> outcome = this.slime.breedingOutcomeWith(this.partner);
                if (outcome.isPresent()) {
                    this.slime.breedWith(level, this.partner, outcome.get());
                }
                this.partner = null;
            }
        }

        private @Nullable ProductiveSlime findPartner() {
            List<ProductiveSlime> nearby = this.slime.level().getEntitiesOfClass(ProductiveSlime.class, this.slime.getBoundingBox().inflate(SEARCH_RANGE), other -> other != this.slime && other.isInLove() && other.isAdult());
            ProductiveSlime best = null;
            double bestDistance = Double.MAX_VALUE;
            for (ProductiveSlime other : nearby) {
                if (this.slime.breedingOutcomeWith(other).isEmpty()) {
                    continue;
                }
                double distance = this.slime.distanceToSqr(other);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    best = other;
                }
            }
            return best;
        }
    }

    private static class SlimeTemptGoal extends Goal {
        private static final double RANGE = 8.0;
        private final ProductiveSlime slime;
        private @Nullable Player player;

        SlimeTemptGoal(ProductiveSlime slime) {
            this.slime = slime;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            this.player = this.slime.level().getNearestPlayer(this.slime, RANGE);
            return this.player != null && !this.player.isSpectator() && this.isHoldingFood(this.player) && this.slime.distanceToSqr(this.player) > 4.0;
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse();
        }

        @Override
        public void stop() {
            this.player = null;
        }

        @Override
        public void tick() {
            if (this.player != null) {
                this.slime.lookAt(this.player, 10.0F, 10.0F);
                this.slime.hopToward(this.player.getX(), this.player.getZ());
            }
        }

        private boolean isHoldingFood(Player player) {
            SlimeType type = this.slime.getSlimeType().value();
            return type.isFood(player.getMainHandItem()) || type.isFood(player.getOffhandItem());
        }
    }
}
