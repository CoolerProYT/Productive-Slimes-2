import DefaultTheme from 'vitepress/theme'
import type { Theme } from 'vitepress'
import AdvancementTree from './components/AdvancementTree.vue'
import BreedingTable from './components/BreedingTable.vue'
import GuidePages from './components/GuidePages.vue'
import ItemSlot from './components/ItemSlot.vue'
import PenCalculator from './components/PenCalculator.vue'
import RecipeCard from './components/RecipeCard.vue'
import ShrineLoot from './components/ShrineLoot.vue'
import SlimeTypeBrowser from './components/SlimeTypeBrowser.vue'
import './style.css'

export default {
  extends: DefaultTheme,
  enhanceApp({ app }) {
    app.component('AdvancementTree', AdvancementTree)
    app.component('BreedingTable', BreedingTable)
    app.component('GuidePages', GuidePages)
    app.component('ItemSlot', ItemSlot)
    app.component('PenCalculator', PenCalculator)
    app.component('RecipeCard', RecipeCard)
    app.component('ShrineLoot', ShrineLoot)
    app.component('SlimeTypeBrowser', SlimeTypeBrowser)
  },
} satisfies Theme
