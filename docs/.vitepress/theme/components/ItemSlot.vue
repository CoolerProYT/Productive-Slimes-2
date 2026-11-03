<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import { withBase } from 'vitepress'
import { itemIcon, itemName, slimeLayers, slimeType } from '../slimes'

const props = withDefaults(
  defineProps<{ id?: string | null; slime?: string | null; count?: number; label?: boolean; size?: number }>(),
  { id: null, slime: null, count: 1, label: false, size: 36 },
)

const name = computed(() => (props.id ? itemName(props.id, props.slime) : ''))
const resolve = (src: string) => (src.startsWith('/') ? withBase(src) : src)
const src = computed(() => {
  if (!props.id) return null
  const icon = itemIcon(props.id)
  return icon ? resolve(icon) : null
})

// Typed slime items are grey textures tinted with the slime color, like the game does.
// The tinted image is drawn on a canvas after the page loads; until then the plain icon shows.
const tinted = ref<string | null>(null)
const tintCache = new Map<string, string>()

function loadImage(url: string): Promise<HTMLImageElement> {
  return new Promise((resolveImage, reject) => {
    const image = new Image()
    image.onload = () => resolveImage(image)
    image.onerror = reject
    image.src = url
  })
}

async function renderTinted() {
  tinted.value = null
  const layers = props.id && props.slime ? slimeLayers(props.id) : null
  const type = props.slime ? slimeType(props.slime) : undefined
  if (!layers || !type) return
  const key = `${props.id}|${type.id}`
  if (tintCache.has(key)) {
    tinted.value = tintCache.get(key)!
    return
  }
  const canvas = document.createElement('canvas')
  canvas.width = 16
  canvas.height = 16
  const context = canvas.getContext('2d')!
  const [r, g, b] = [1, 3, 5].map((i) => parseInt(type.color.slice(i, i + 2), 16) / 255)
  for (const layer of layers) {
    const image = await loadImage(resolve(layer.src))
    const scratch = document.createElement('canvas')
    scratch.width = 16
    scratch.height = 16
    const scratchContext = scratch.getContext('2d')!
    scratchContext.drawImage(image, 0, 0)
    if (layer.tint) {
      const pixels = scratchContext.getImageData(0, 0, 16, 16)
      for (let i = 0; i < pixels.data.length; i += 4) {
        pixels.data[i] *= r
        pixels.data[i + 1] *= g
        pixels.data[i + 2] *= b
      }
      scratchContext.putImageData(pixels, 0, 0)
    }
    context.drawImage(scratch, 0, 0)
  }
  const url = canvas.toDataURL()
  tintCache.set(key, url)
  tinted.value = url
}

onMounted(renderTinted)
watch(() => [props.id, props.slime], renderTinted)

// Falls back to initials when an item has no icon or the hosted icon fails to load.
const failed = ref(false)
watch(src, () => (failed.value = false))
const initials = computed(() =>
  name.value
    .split(' ')
    .filter((word) => /^[A-Z]/.test(word))
    .slice(0, 2)
    .map((word) => word[0])
    .join(''),
)
const slotStyle = computed(() => ({ width: `${props.size}px`, height: `${props.size}px` }))
const imageStyle = computed(() => ({ width: `${props.size - 4}px`, height: `${props.size - 4}px` }))
</script>

<template>
  <span class="ps-item" :class="{ 'with-label': label }">
    <span class="ps-slot" :style="slotStyle" :title="name" :aria-label="name" role="img">
      <img v-if="tinted" class="pixelated" :style="imageStyle" :src="tinted" alt="" />
      <img v-else-if="src && !failed" class="pixelated" :style="imageStyle" :src="src" alt="" loading="lazy" @error="failed = true" />
      <span v-else-if="id" class="ps-initials">{{ initials }}</span>
      <span v-if="count > 1" class="ps-count">{{ count }}</span>
    </span>
    <span v-if="label && id" class="ps-label">{{ name }}</span>
  </span>
</template>

<style scoped>
.ps-item {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  vertical-align: middle;
}

.ps-slot {
  position: relative;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  flex: none;
  background: var(--ps-slot-bg);
  border: 2px solid;
  border-color: var(--ps-slot-dark) var(--ps-slot-light) var(--ps-slot-light) var(--ps-slot-dark);
}

.ps-initials {
  font: 600 12px/1 var(--vp-font-family-mono);
  color: #fff;
  text-shadow: 1px 1px 0 #3f3f3f;
}

.ps-count {
  position: absolute;
  right: 1px;
  bottom: -1px;
  font: 700 12px/1 var(--vp-font-family-mono);
  color: #fff;
  text-shadow: 1px 1px 0 #3f3f3f;
}

.ps-label {
  font-weight: 500;
}
</style>
