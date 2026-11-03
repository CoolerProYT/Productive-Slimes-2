# Productive Slimes wiki

VitePress site for the mod. Slime types, breeding rules, recipes, shrine loot and item textures are read from the mod itself, so regenerate the mod's data before building when it changes.

```bash
./gradlew :neoforge:runData   # from the repository root, when mod data changed
cd docs
npm install
npm run dev                   # syncs data, then serves http://localhost:5173/ProductiveSlimes/
npm run build                 # syncs data, then builds to .vitepress/dist
```

`npm run sync` (run automatically by `dev` and `build`) writes `.vitepress/data/data.json` and copies the mod's item textures and GUI textures to `public/items/` and `public/gui/`. All of these are git-ignored.

- **Typed items** (slimeballs, slime jars, spawn eggs) are grey textures. `ItemSlot` tints them with the slime type's color in the browser, the same way the game does, so there is one image per item instead of one per slime type.
- **Block items** have no flat texture. Their icons are in-game renders committed in `public/icons/` (64×64, captured at GUI scale 4). Re-capture them when a block model changes.
- **Vanilla items** load from the hosted renders at `https://storage.googleapis.com/coolerpromc/textures/`, set in `.vitepress/theme/slimes.ts`. Mojang's textures are not bundled.

The site is published to GitHub Pages by `.github/workflows/docs.yml` on every push to the default branch.
