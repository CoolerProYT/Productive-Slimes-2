// @ts-ignore
import raw from '../data/data.json'

export interface Breeding {
  parentA: string
  parentB: string
  /** Blocks that count as the catalyst; empty when the rule needs none. */
  catalyst: string[]
  consumeCatalyst: boolean
}

export interface SlimeType {
  id: string
  name: string
  color: string
  food: string[]
  squeeze: { id: string; count: number }
  productionTime: number
  tier: number
  naturalSpawn: boolean
  breeding: Breeding | null
}

export interface Recipe {
  id: string
  type: string
  result: { id: string; count: number }
  pattern?: string[]
  key?: Record<string, string>
  ingredients?: string[]
  ingredient?: string
  cookingTime?: number
  experience?: number
}

export interface Advancement {
  id: string
  parent: string | null
  icon: string | null
  title: string
  description: string
  frame: 'task' | 'goal' | 'challenge'
}

export interface LootEntry {
  item: string
  weight: number
  min: number
  max: number
}

export const data = raw as unknown as {
  names: Record<string, string>
  textures: Record<string, string>
  slimeTypes: SlimeType[]
  recipes: Recipe[]
  shrineLoot: { rolls: { min: number; max: number } | number; entries: LootEntry[] }[]
  advancements: Advancement[]
  guidePages: string[]
}

export const NS = 'productiveslimes'
export const MAX_SLIME_SIZE = 4
/** Pen speed multiplier for a fed batch, and the upgrade multipliers (from the mod's code). */
export const FED_MULTIPLIER = 0.5
export const SPEED_UPGRADE = 0.8
/** Pen batches one food item keeps a slime fed for. */
export const BATCHES_PER_FOOD = 4
export const ADVANCED_SPEED_UPGRADE = 0.5
export const WILD_PRODUCTION_MULTIPLIER = 2
export const SQUEEZE_TICKS = 80

/** Items that take a slime type: rendered tinted with the type's color. */
export const SLIME_ITEMS = {
  slimeball: `${NS}:slimeball`,
  jar: `${NS}:slime_jar`,
  egg: `${NS}:productive_slime_spawn_egg`,
}

export function slimeType(id: string): SlimeType | undefined {
  const full = id.includes(':') ? id : `${NS}:${id}`
  return data.slimeTypes.find((type) => type.id === full)
}

export function typesByTier(): SlimeType[] {
  return [...data.slimeTypes].sort((a, b) => a.tier - b.tier || a.name.localeCompare(b.name))
}

/** Name of an item, or of a typed slime item when `slime` is given. */
export function itemName(id: string, slime?: string | null): string {
  const type = slime ? slimeType(slime) : undefined
  if (type) {
    if (id === SLIME_ITEMS.slimeball) return `${type.name} Slimeball`
    if (id === SLIME_ITEMS.jar) return `Jar of ${type.name} Slime`
    if (id === SLIME_ITEMS.egg) return `${type.name} Slime Spawn Egg`
  }
  if (data.names[id]) return data.names[id]
  const path = id.replace(/^#/, '').split(':').pop() ?? id
  return path
    .split('_')
    .map((word) => (['of', 'the'].includes(word) ? word : word.charAt(0).toUpperCase() + word.slice(1)))
    .join(' ')
}

/** Hosted renders of vanilla items, one PNG per item id. Mojang's textures are not bundled here. */
const VANILLA_ICONS = 'https://storage.googleapis.com/coolerpromc/textures'

/** Where to load an item's icon from; mod paths are relative to the site base. */
export function itemIcon(id: string): string | null {
  if (data.textures[id]) return data.textures[id]
  const [namespace, path] = id.includes(':') ? id.split(':') : ['minecraft', id]
  if (namespace !== 'minecraft') return null
  return `${VANILLA_ICONS}/${namespace}/${path}.png`
}

export interface Layer {
  src: string
  tint: boolean
}

/** Texture layers of a typed slime item, drawn bottom to top. Tinted layers are multiplied by the slime color. */
export function slimeLayers(id: string): Layer[] | null {
  if (id === SLIME_ITEMS.slimeball) return [{ src: '/items/slimeball.png', tint: true }]
  if (id === SLIME_ITEMS.jar)
    return [
      { src: '/items/slime_jar_contents.png', tint: true },
      { src: '/items/slime_jar.png', tint: false },
    ]
  if (id === SLIME_ITEMS.egg)
    return [
      { src: '/items/productive_slime_spawn_egg.png', tint: true },
      { src: '/items/productive_slime_spawn_egg_overlay.png', tint: true },
    ]
  return null
}

export function seconds(ticks: number): string {
  const value = ticks / 20
  return Number.isInteger(value) ? `${value}` : value.toFixed(1)
}

/** Seconds as "1 min 30 s" style text. */
export function duration(ticks: number): string {
  const total = Math.round(ticks / 20)
  const minutes = Math.floor(total / 60)
  const rest = total % 60
  if (minutes === 0) return `${rest} s`
  return rest === 0 ? `${minutes} min` : `${minutes} min ${rest} s`
}

export function percent(fraction: number): string {
  const value = Math.round(fraction * 1000) / 10
  return `${Number.isInteger(value) ? value : value.toFixed(1)}%`
}

