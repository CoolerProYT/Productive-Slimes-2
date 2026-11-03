import { defineConfig } from 'vitepress'

// GitHub Pages serves a project site from /<repository>/. For a custom domain or a user site, build with DOCS_BASE=/.
const base = process.env.DOCS_BASE ?? '/ProductiveSlimes/'

export default defineConfig({
  title: 'Productive Slimes',
  description: 'Breed resource slimes, house them in pens and squeeze their slimeballs into resources. For Minecraft 26.3 on Fabric and NeoForge.',
  base,
  cleanUrls: true,
  srcExclude: ['README.md', 'scripts/**'],
  head: [['link', { rel: 'icon', type: 'image/png', href: `${base}items/slime_crystal.png` }]],
  themeConfig: {
    logo: { src: '/items/slime_crystal.png', alt: '' },
    nav: [
      { text: 'Guide', link: '/guide/getting-started' },
      { text: 'Slime types', link: '/guide/slime-types' },
      { text: 'Breeding', link: '/guide/breeding' },
      { text: 'Datapacks', link: '/datapacks/' },
    ],
    sidebar: [
      {
        text: 'Guide',
        items: [
          { text: 'Getting started', link: '/guide/getting-started' },
          { text: 'Slimy Land', link: '/guide/slimy-land' },
          { text: 'Slimes', link: '/guide/slimes' },
          { text: 'Breeding', link: '/guide/breeding' },
          { text: 'Slime Jar', link: '/guide/slime-jar' },
          { text: 'Slime Pen', link: '/guide/slime-pen' },
          { text: 'Slime Squeezer', link: '/guide/slime-squeezer' },
          { text: 'Slime types', link: '/guide/slime-types' },
          { text: 'Automation', link: '/guide/automation' },
          { text: 'Blocks & items', link: '/guide/blocks-and-items' },
          { text: 'Guide book & advancements', link: '/guide/progression' },
          { text: 'JEI & Jade', link: '/guide/compat' },
        ],
      },
      {
        text: 'Datapacks',
        items: [
          { text: 'Overview', link: '/datapacks/' },
          { text: 'Slime type files', link: '/datapacks/slime-types' },
        ],
      },
      { text: 'FAQ', link: '/faq' },
    ],
    socialLinks: [{ icon: 'github', link: 'https://github.com/CoolerProYT/ProductiveSlimes' }],
    search: { provider: 'local' },
    outline: { level: [2, 3] },
    footer: { message: 'Released under the MIT License.' },
  },
})
