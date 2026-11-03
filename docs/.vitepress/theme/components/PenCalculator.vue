<script setup lang="ts">
import { computed, ref } from 'vue'
import {
  ADVANCED_SPEED_UPGRADE,
  BATCHES_PER_FOOD,
  FED_MULTIPLIER,
  MAX_SLIME_SIZE,
  SLIME_ITEMS,
  SPEED_UPGRADE,
  duration,
  itemName,
  slimeType,
  typesByTier,
} from '../slimes'
import ItemSlot from './ItemSlot.vue'

const types = typesByTier()
const typeId = ref('productiveslimes:iron')
const size = ref(1)
const upgrades = ref<[string, string]>(['none', 'none'])
const fed = ref(false)

const UPGRADE_MULTIPLIERS: Record<string, number> = { none: 1, speed: SPEED_UPGRADE, advanced: ADVANCED_SPEED_UPGRADE }

const type = computed(() => slimeType(typeId.value)!)

/** Same formula as the pen: base time × each upgrade × food bonus, at least 1 second. */
const ticks = computed(() => {
  let multiplier = UPGRADE_MULTIPLIERS[upgrades.value[0]] * UPGRADE_MULTIPLIERS[upgrades.value[1]]
  if (fed.value) multiplier *= FED_MULTIPLIER
  return Math.max(20, Math.round(type.value.productionTime * multiplier))
})
const batchesPerHour = computed(() => 72000 / ticks.value)
const slimeballsPerHour = computed(() => batchesPerHour.value * size.value)
const resourcesPerHour = computed(() => slimeballsPerHour.value * type.value.squeeze.count)
const foodPerHour = computed(() => (fed.value ? batchesPerHour.value / BATCHES_PER_FOOD : 0))
const round = (value: number) => (value >= 10 ? Math.round(value) : Math.round(value * 10) / 10)
</script>

<template>
  <div class="ps-calc">
    <div class="inputs">
      <label>
        Slime
        <select v-model="typeId">
          <option v-for="t in types" :key="t.id" :value="t.id">{{ t.name }} (tier {{ t.tier }})</option>
        </select>
      </label>
      <label>
        Size
        <select v-model.number="size">
          <option v-for="s in MAX_SLIME_SIZE" :key="s" :value="s">{{ s }}</option>
        </select>
      </label>
      <label v-for="i in 2" :key="i">
        Upgrade {{ i }}
        <select v-model="upgrades[i - 1]">
          <option value="none">Empty</option>
          <option value="speed">Speed Upgrade</option>
          <option value="advanced">Advanced Speed Upgrade</option>
        </select>
      </label>
      <label class="check">
        <input v-model="fed" type="checkbox" />
        Keep the food slot stocked
      </label>
    </div>

    <div class="results">
      <div class="result">
        <ItemSlot :id="SLIME_ITEMS.jar" :slime="typeId" />
        <div>
          <div class="value">{{ duration(ticks) }}</div>
          <div class="ps-muted">per batch of {{ size }} slimeball{{ size > 1 ? 's' : '' }}</div>
        </div>
      </div>
      <div class="result">
        <ItemSlot :id="SLIME_ITEMS.slimeball" :slime="typeId" />
        <div>
          <div class="value">{{ round(slimeballsPerHour) }} / hour</div>
          <div class="ps-muted">slimeballs</div>
        </div>
      </div>
      <div class="result">
        <ItemSlot :id="type.squeeze.id" />
        <div>
          <div class="value">{{ round(resourcesPerHour) }} / hour</div>
          <div class="ps-muted">{{ itemName(type.squeeze.id) }} after squeezing</div>
        </div>
      </div>
      <div v-if="fed" class="result">
        <ItemSlot :id="type.food[0]" />
        <div>
          <div class="value">{{ round(foodPerHour) }} / hour</div>
          <div class="ps-muted">{{ itemName(type.food[0]) }} eaten</div>
        </div>
      </div>
    </div>
    <p class="ps-muted">Rates are per real-time hour (72,000 ticks) while the pen's chunk is loaded.</p>
  </div>
</template>

<style scoped>
.ps-calc {
  margin: 16px 0;
  padding: 16px;
  border: 1px solid var(--vp-c-divider);
  border-radius: 10px;
  background: var(--vp-c-bg-soft);
}

.inputs {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 12px;
}

label {
  display: flex;
  flex-direction: column;
  gap: 4px;
  font-size: 13px;
  font-weight: 500;
}

label.check {
  flex-direction: row;
  align-items: center;
  gap: 8px;
}

select {
  padding: 5px 8px;
  border: 1px solid var(--vp-c-divider);
  border-radius: 6px;
  background: var(--vp-c-bg);
}

.results {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 12px;
  margin-top: 16px;
}

.result {
  display: flex;
  align-items: center;
  gap: 10px;
}

.value {
  font-size: 18px;
  font-weight: 600;
}
</style>
