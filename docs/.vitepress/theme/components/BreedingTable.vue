<script setup lang="ts">
import { computed, ref } from 'vue'
import { SLIME_ITEMS, data, slimeType, typesByTier } from '../slimes'
import ItemSlot from './ItemSlot.vue'

const search = ref('')

const rules = typesByTier().filter((type) => type.breeding)

const shown = computed(() => {
  const query = search.value.trim().toLowerCase()
  if (!query) return rules
  return rules.filter((type) => {
    const breeding = type.breeding!
    const names = [type.name, slimeType(breeding.parentA)?.name ?? '', slimeType(breeding.parentB)?.name ?? '']
    return names.join(' ').toLowerCase().includes(query)
  })
})

const parentName = (id: string) => slimeType(id)?.name ?? id
const wild = data.slimeTypes.filter((type) => type.naturalSpawn)
</script>

<template>
  <div class="ps-breeding">
    <input v-model="search" type="search" placeholder="Search a child or parent…" aria-label="Search breeding rules" />
    <div class="ps-table-wrap">
      <table>
        <thead>
          <tr>
            <th>Parents</th>
            <th>Catalyst (within 2 blocks)</th>
            <th>Baby</th>
            <th>Tier</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="type in shown" :key="type.id">
            <td>
              <span class="pair">
                <ItemSlot :id="SLIME_ITEMS.egg" :slime="type.breeding!.parentA" />
                <span>{{ parentName(type.breeding!.parentA) }}</span>
                +
                <ItemSlot :id="SLIME_ITEMS.egg" :slime="type.breeding!.parentB" />
                <span>{{ parentName(type.breeding!.parentB) }}</span>
              </span>
            </td>
            <td>
              <span v-if="type.breeding!.catalyst.length" class="pair">
                <ItemSlot v-for="block in type.breeding!.catalyst" :key="block" :id="block" label />
                <em v-if="type.breeding!.consumeCatalyst" class="ps-muted">(used up)</em>
              </span>
              <span v-else class="ps-muted">None</span>
            </td>
            <td>
              <span class="pair">
                <ItemSlot :id="SLIME_ITEMS.egg" :slime="type.id" />
                <strong>{{ type.name }}</strong>
              </span>
            </td>
            <td>{{ type.tier }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <p class="ps-muted">
      {{ shown.length }} breeding rules. Starting slimes found in the wild:
      <template v-for="(type, i) in wild" :key="type.id">{{ i ? ', ' : '' }}{{ type.name }}</template>.
    </p>
  </div>
</template>

<style scoped>
input[type='search'] {
  width: 100%;
  margin-top: 16px;
  padding: 6px 10px;
  border: 1px solid var(--vp-c-divider);
  border-radius: 8px;
  background: var(--vp-c-bg-soft);
}

.pair {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  white-space: nowrap;
}
</style>
