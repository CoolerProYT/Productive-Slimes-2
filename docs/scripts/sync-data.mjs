// Pulls wiki data straight from the mod so the docs never drift from the game:
// datagen output (slime types, recipes, lang, shrine loot) and the mod's own textures.
// Run `./gradlew :neoforge:runData` first when the mod's data changes.
import { copyFileSync, existsSync, mkdirSync, readdirSync, readFileSync, writeFileSync } from 'node:fs'
import { basename, dirname, join } from 'node:path'
import { fileURLToPath } from 'node:url'

const NS = 'productiveslimes'
const docs = join(dirname(fileURLToPath(import.meta.url)), '..')
const root = join(docs, '..')
const generated = join(root, 'common/src/generated/resources')
const assets = join(root, `common/src/main/resources/assets/${NS}`)
const data = join(generated, `data/${NS}`)

if (!existsSync(generated)) {
  console.error(`No datagen output at ${generated}. Run ./gradlew :neoforge:runData first.`)
  process.exit(1)
}

const readJson = (file) => JSON.parse(readFileSync(file, 'utf8'))
const jsonFiles = (dir) => (existsSync(dir) ? readdirSync(dir).filter((f) => f.endsWith('.json')).sort() : [])
const list = (value) => (value == null ? [] : Array.isArray(value) ? value : [value])

const lang = readJson(join(generated, `assets/${NS}/lang/en_us.json`))

// Item and block names for mod content; vanilla names are prettified on the page.
const names = {}
for (const [key, value] of Object.entries(lang)) {
  const match = key.match(new RegExp(`^(item|block)\\.${NS}\\.([a-z0-9_]+)$`))
  if (match) names[`${NS}:${match[2]}`] = value
}

// Flat item textures are copied from the mod. Block items have no flat texture, so their icons are
// in-game renders committed in public/icons/.
const pngNames = (dir) => (existsSync(dir) ? readdirSync(dir).filter((f) => f.endsWith('.png')).map((f) => basename(f, '.png')) : [])
mkdirSync(join(docs, 'public/items'), { recursive: true })
const textures = {}
for (const name of pngNames(join(assets, 'textures/item'))) {
  copyFileSync(join(assets, 'textures/item', `${name}.png`), join(docs, 'public/items', `${name}.png`))
  textures[`${NS}:${name}`] = `/items/${name}.png`
}
for (const name of pngNames(join(docs, 'public/icons'))) {
  textures[`${NS}:${name}`] ??= `/icons/${name}.png`
}
mkdirSync(join(docs, 'public/gui'), { recursive: true })
for (const gui of ['slime_pen', 'slime_squeezer']) {
  copyFileSync(join(assets, `textures/gui/container/${gui}.png`), join(docs, 'public/gui', `${gui}.png`))
}

// Holder sets in slime type files: a single id, a list of ids, or a #tag.
const holderSet = (value) => list(value).map((id) => (typeof id === 'string' ? id : '?'))

const slimeTypes = jsonFiles(join(data, `${NS}/slime_type`)).map((file) => {
  const id = `${NS}:${basename(file, '.json')}`
  const json = readJson(join(data, `${NS}/slime_type`, file))
  return {
    id,
    name: lang[`slime_type.${NS}.${basename(file, '.json')}`] ?? basename(file, '.json'),
    color: json.color,
    food: holderSet(json.food),
    squeeze: { id: json.squeeze_result.id, count: json.squeeze_result.count ?? 1 },
    productionTime: json.production_time,
    tier: json.tier ?? 0,
    naturalSpawn: json.natural_spawn ?? false,
    breeding: json.breeding
      ? {
          parentA: json.breeding.parent_a,
          parentB: json.breeding.parent_b,
          catalyst: json.breeding.catalyst ? holderSet(json.breeding.catalyst) : [],
          consumeCatalyst: json.breeding.consume_catalyst ?? false,
        }
      : null,
  }
})

const ingredient = (value) => {
  if (typeof value === 'string') return value
  if (Array.isArray(value)) return ingredient(value[0])
  if (value?.item) return value.item
  if (value?.tag) return `#${value.tag}`
  return '?'
}

const recipes = jsonFiles(join(data, 'recipe')).map((file) => {
  const json = readJson(join(data, 'recipe', file))
  const recipe = { id: `${NS}:${basename(file, '.json')}`, type: json.type, result: { id: json.result?.id, count: json.result?.count ?? 1 } }
  if (json.type === 'minecraft:crafting_shaped') {
    recipe.pattern = json.pattern
    recipe.key = Object.fromEntries(Object.entries(json.key).map(([symbol, value]) => [symbol, ingredient(value)]))
  } else if (json.type === 'minecraft:crafting_shapeless') {
    recipe.ingredients = json.ingredients.map(ingredient)
  } else if (json.type === 'minecraft:stonecutting') {
    recipe.ingredient = ingredient(json.ingredient)
  } else {
    recipe.ingredient = ingredient(json.ingredient)
    recipe.cookingTime = json.cookingtime
    recipe.experience = json.experience
  }
  return recipe
})

// Slime Shrine chest loot: every item entry with its weight and count range
const lootFile = join(data, 'loot_table/chests/slime_shrine.json')
const shrineLoot = existsSync(lootFile)
  ? readJson(lootFile).pools.map((pool) => ({
      rolls: pool.rolls,
      entries: (pool.entries ?? [])
        .filter((entry) => entry.type === 'minecraft:item')
        .map((entry) => {
          const count = list(entry.functions ?? entry.modifier).find((f) => f?.function === 'minecraft:set_count' || f?.type === 'minecraft:set_count')?.count
          return { item: entry.name, weight: entry.weight ?? 1, min: count?.min ?? count ?? 1, max: count?.max ?? count ?? 1 }
        }),
    }))
  : []

// Advancements: generated ones plus the hand-written biome advancement in src/main/resources
const text = (component) => (typeof component === 'string' ? component : component?.translate ?? component?.text ?? '')
const advancementDirs = [join(data, 'advancement/main'), join(root, `common/src/main/resources/data/${NS}/advancement/main`)]
const advancements = advancementDirs.flatMap((dir) =>
  jsonFiles(dir).map((file) => {
    const json = readJson(join(dir, file))
    const display = json.display ?? {}
    return {
      id: `${NS}:main/${basename(file, '.json')}`,
      parent: json.parent ?? null,
      icon: display.icon?.id ?? null,
      title: lang[text(display.title)] ?? text(display.title),
      description: lang[text(display.description)] ?? text(display.description),
      frame: display.frame ?? 'task',
    }
  }),
)

// Guide book pages, without Minecraft formatting codes
const guidePages = Object.entries(lang)
  .filter(([key]) => key.startsWith(`guide.${NS}.page.`))
  .sort(([a], [b]) => Number(a.split('.').pop()) - Number(b.split('.').pop()))
  .map(([, value]) => value.replace(/§./g, ''))

mkdirSync(join(docs, '.vitepress/data'), { recursive: true })
writeFileSync(join(docs, '.vitepress/data/data.json'), JSON.stringify({ names, textures, slimeTypes, recipes, shrineLoot, advancements, guidePages }, null, 2))
console.log(`Synced ${slimeTypes.length} slime types, ${recipes.length} recipes, ${advancements.length} advancements, ${Object.keys(textures).length} textures.`)
