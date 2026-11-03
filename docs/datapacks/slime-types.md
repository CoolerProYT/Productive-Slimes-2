# Slime type files

One file per slime type, at `data/<namespace>/productiveslimes/slime_type/<name>.json`.

## Example

The built-in Iron Slime:

```json
{
  "color": "#D8D8D8",
  "food": "minecraft:raw_iron",
  "squeeze_result": {
    "id": "minecraft:iron_ingot",
    "count": 1
  },
  "production_time": 3600,
  "tier": 3,
  "breeding": {
    "parent_a": "productiveslimes:copper",
    "parent_b": "productiveslimes:stone",
    "catalyst": "minecraft:iron_block"
  }
}
```

## Fields

| Field | Type | Required | Meaning |
| --- | --- | --- | --- |
| `color` | `"#RRGGBB"` | yes | Tint for the slime, its slimeballs, jar and spawn egg. |
| `food` | item, list of items, or `#tag` | yes | What it eats to grow and breed, and what the Slime Pen's food slot accepts. |
| `squeeze_result` | item stack | yes | What one slimeball gives in the Slime Squeezer. `count` defaults to 1. |
| `production_time` | ticks | yes | Length of one Slime Pen batch with no food or upgrades. 20 ticks = 1 second. Wild slimes take twice as long. |
| `tier` | number | no | Used to sort types in JEI and the creative tab. Defaults to 0. |
| `breeding` | object | no | How to breed this type. Leave it out for types that only spawn or come from spawn eggs. |
| `natural_spawn` | boolean | no | `true` lets this type spawn in the Slimy Land. Defaults to `false`. |

### `breeding`

| Field | Type | Required | Meaning |
| --- | --- | --- | --- |
| `parent_a` | slime type id | yes | One parent. |
| `parent_b` | slime type id | yes | The other parent. Order does not matter, and both can be the same type. |
| `catalyst` | block, list of blocks, or `#tag` | no | A block that must be within 2 blocks of the parents. Leave it out for no catalyst. |
| `consume_catalyst` | boolean | no | `true` destroys the catalyst block when the baby is born. Defaults to `false`. |

When several types share the same parents, the one whose catalyst is nearby wins, then the one with no catalyst. See [Breeding](../guide/breeding#which-baby-you-get).

## Adding a type

`data/mypack/productiveslimes/slime_type/emerald.json`:

```json
{
  "color": "#41F384",
  "food": "minecraft:emerald",
  "squeeze_result": { "id": "minecraft:emerald", "count": 1 },
  "production_time": 7200,
  "tier": 5,
  "breeding": {
    "parent_a": "productiveslimes:gold",
    "parent_b": "productiveslimes:lapis",
    "catalyst": "minecraft:emerald_block"
  }
}
```

Then give it a name in a resource pack (`"slime_type.mypack.emerald": "Emerald"`) and start a new world or reopen yours. It shows up in JEI, the creative tab and the breeding rules right away.
