<script setup lang="ts">
import { data, percent } from '../slimes'
import ItemSlot from './ItemSlot.vue'

const rollText = (rolls: { min: number; max: number } | number) =>
  typeof rolls === 'number' ? `${rolls}` : `${rolls.min}–${rolls.max}`

const pools = data.shrineLoot.map((pool) => {
  const total = pool.entries.reduce((sum, entry) => sum + entry.weight, 0)
  return { rolls: rollText(pool.rolls), entries: pool.entries.map((entry) => ({ ...entry, chance: entry.weight / total })) }
})
</script>

<template>
  <div v-for="(pool, i) in pools" :key="i" class="ps-table-wrap">
    <p class="ps-muted">{{ pool.rolls }} rolls from:</p>
    <table>
      <thead>
        <tr>
          <th>Item</th>
          <th>Count</th>
          <th>Chance per roll</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="entry in pool.entries" :key="entry.item">
          <td><ItemSlot :id="entry.item" label /></td>
          <td>{{ entry.min === entry.max ? entry.min : `${entry.min}–${entry.max}` }}</td>
          <td>{{ percent(entry.chance) }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
