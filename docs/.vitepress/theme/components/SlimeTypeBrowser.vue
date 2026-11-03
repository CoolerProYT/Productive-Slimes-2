<script setup lang="ts">
import { computed, ref } from 'vue'
import { SLIME_ITEMS, duration, itemName, typesByTier } from '../slimes'
import ItemSlot from './ItemSlot.vue'

const types = typesByTier()
const tiers = [...new Set(types.map((type) => type.tier))]

const search = ref('')
const tier = ref<number | null>(null)

const shown = computed(() => {
  const query = search.value.trim().toLowerCase()
  return types.filter((type) => {
    if (tier.value !== null && type.tier !== tier.value) return false
    if (!query) return true
    const haystack = [type.name, itemName(type.squeeze.id), ...type.food.map((id) => itemName(id))].join(' ').toLowerCase()
    return haystack.includes(query)
  })
})
</script>

<template>
  <div class="ps-browser">
    <div class="controls">
      <input v-model="search" type="search" placeholder="Search slime, food or product…" aria-label="Search slime types" />
      <div class="tiers" role="group" aria-label="Filter by tier">
        <button :class="{ active: tier === null }" @click="tier = null">All</button>
        <button v-for="t in tiers" :key="t" :class="{ active: tier === t }" @click="tier = t">Tier {{ t }}</button>
      </div>
    </div>

    <div class="ps-table-wrap">
      <table>
        <thead>
          <tr>
            <th>Slime</th>
            <th>Tier</th>
            <th>Food</th>
            <th>Squeezes into</th>
            <th>Pen time</th>
            <th>How to get</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="type in shown" :key="type.id" :id="type.id.split(':')[1]">
            <td>
              <span class="slime">
                <ItemSlot :id="SLIME_ITEMS.egg" :slime="type.id" />
                <span class="swatch" :style="{ background: type.color }" />
                <strong>{{ type.name }}</strong>
              </span>
            </td>
            <td>{{ type.tier }}</td>
            <td><ItemSlot v-for="food in type.food" :key="food" :id="food" /></td>
            <td>
              <span class="pair">
                <ItemSlot :id="SLIME_ITEMS.slimeball" :slime="type.id" />
                ➜
                <ItemSlot :id="type.squeeze.id" :count="type.squeeze.count" />
              </span>
            </td>
            <td class="nowrap">{{ duration(type.productionTime) }}</td>
            <td>
              <span v-if="type.naturalSpawn" class="wild">Wild in the Slimy Land</span>
              <span v-else-if="type.breeding" class="pair">
                <ItemSlot :id="SLIME_ITEMS.egg" :slime="type.breeding.parentA" />
                +
                <ItemSlot :id="SLIME_ITEMS.egg" :slime="type.breeding.parentB" />
                <template v-if="type.breeding.catalyst.length">
                  near
                  <ItemSlot v-for="block in type.breeding.catalyst" :key="block" :id="block" />
                </template>
              </span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <p class="ps-muted">{{ shown.length }} of {{ types.length }} slime types. Pen time is one batch with no food or upgrades.</p>
  </div>
</template>

<style scoped>
.controls {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  align-items: center;
  margin: 16px 0 8px;
}

input[type='search'] {
  flex: 1 1 220px;
  padding: 6px 10px;
  border: 1px solid var(--vp-c-divider);
  border-radius: 8px;
  background: var(--vp-c-bg-soft);
}

.tiers {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

.tiers button {
  padding: 4px 10px;
  border: 1px solid var(--vp-c-divider);
  border-radius: 999px;
  font-size: 13px;
}

.tiers button.active {
  border-color: var(--vp-c-brand-1);
  background: var(--vp-c-brand-soft);
  color: var(--vp-c-brand-1);
}

.slime,
.pair {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  white-space: nowrap;
}

.swatch {
  width: 10px;
  height: 10px;
  border-radius: 2px;
  border: 1px solid rgba(0, 0, 0, 0.3);
}

.wild {
  color: var(--vp-c-brand-1);
  font-weight: 500;
}

.nowrap {
  white-space: nowrap;
}
</style>
