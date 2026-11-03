package com.coolerpromc.productiveslimes.advancement;

import com.coolerpromc.productiveslimes.platform.util.RegistryHandler;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;

import java.util.Optional;

public class SlimeTrigger extends SimpleCriterionTrigger<SlimeTrigger.TriggerInstance> {
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Holder<SlimeType> type, int size) {
        this.trigger(player, instance -> instance.matches(type, size));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, MinMaxBounds.Ints size, MinMaxBounds.Ints tier) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                MinMaxBounds.Ints.CODEC.optionalFieldOf("size", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::size),
                MinMaxBounds.Ints.CODEC.optionalFieldOf("tier", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::tier)
        ).apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> any(RegistryHandler<CriterionTrigger<?>, SlimeTrigger> trigger) {
            return trigger.get().createCriterion(new TriggerInstance(Optional.empty(), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY));
        }

        public static Criterion<TriggerInstance> minSize(RegistryHandler<CriterionTrigger<?>, SlimeTrigger> trigger, int size) {
            return trigger.get().createCriterion(new TriggerInstance(Optional.empty(), MinMaxBounds.Ints.atLeast(size), MinMaxBounds.Ints.ANY));
        }

        public static Criterion<TriggerInstance> minTier(RegistryHandler<CriterionTrigger<?>, SlimeTrigger> trigger, int tier) {
            return trigger.get().createCriterion(new TriggerInstance(Optional.empty(), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.atLeast(tier)));
        }

        public boolean matches(Holder<SlimeType> type, int size) {
            return this.size.matches(size) && this.tier.matches(type.value().tier());
        }
    }
}
