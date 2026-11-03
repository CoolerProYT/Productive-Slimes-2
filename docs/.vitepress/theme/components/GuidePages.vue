<script setup lang="ts">
import { data } from '../slimes'

/** The in-game guide book's pages: the first line is the page title. */
const pages = data.guidePages.map((page) => {
  const [title, ...rest] = page.split('\n')
  return { title, body: rest.join('\n').trim() }
})
</script>

<template>
  <div class="ps-guide">
    <div v-for="(page, i) in pages" :key="i" class="page">
      <div class="number">Page {{ i + 1 }}</div>
      <div class="title">{{ page.title }}</div>
      <p class="body">{{ page.body }}</p>
    </div>
  </div>
</template>

<style scoped>
.ps-guide {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 12px;
  margin: 16px 0;
}

.page {
  padding: 12px 14px;
  border: 1px solid var(--vp-c-divider);
  border-radius: 10px;
  background: var(--vp-c-bg-soft);
}

.number {
  font-size: 11px;
  text-transform: uppercase;
  letter-spacing: 0.04em;
  color: var(--vp-c-text-3);
}

.title {
  font-weight: 600;
  color: var(--vp-c-brand-1);
}

.body {
  margin: 6px 0 0 !important;
  font-size: 14px;
  line-height: 1.5;
  white-space: pre-line;
}
</style>
