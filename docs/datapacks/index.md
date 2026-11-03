# Datapacks

Slime types are data, not code. Every type you see in game, including its food, product, pen time and breeding recipe, is a JSON file that a datapack can change, remove or add to.

## What you can change

- **Add a new slime type**, for example a Copper Ore slime for a modpack.
- **Change an existing type**: its food, what it squeezes into, its pen time or its color.
- **Change breeding**: different parents, a different catalyst block, or a catalyst that gets used up.
- **Control wild spawns**: any type with `natural_spawn` set can appear in the Slimy Land.

Slime types are a **registry**, loaded when a world is created or opened. Unlike recipes, `/reload` does not apply changes: restart the world after editing a datapack.

## Where files go

```
my_datapack/
  pack.mcmeta
  data/<namespace>/productiveslimes/slime_type/<name>.json
```

The file's location is the type's id. `data/mypack/productiveslimes/slime_type/copper_ore.json` creates `mypack:copper_ore`. To change a built-in type, use the same path as the mod: `data/productiveslimes/productiveslimes/slime_type/iron.json`.

The format is on the [Slime type files](./slime-types) page.

## Names

A type's display name comes from the translation key `slime_type.<namespace>.<name>`. Add it to a resource pack's lang file:

```json
{
  "slime_type.mypack.copper_ore": "Copper Ore"
}
```

Items then read "Copper Ore Slimeball", "Jar of Copper Ore Slime" and so on.
