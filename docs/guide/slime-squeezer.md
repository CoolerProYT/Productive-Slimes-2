# Slime Squeezer

The Slime Squeezer presses slimeballs into the resource of their slime type. It needs no fuel and no power.

<RecipeCard id="slime_squeezer" />

## Using it

![Slime Squeezer interface](/gui/slime_squeezer.png){.pixelated width=352}

Put slimeballs in the input slot. Every **4 seconds** one slimeball is pressed into its resource, which goes into the two output slots on the right. The two slots on the left take [speed upgrades](./slime-pen#speed-upgrades), the same ones the Slime Pen uses: two Advanced upgrades bring a squeeze down to 1 second. While it works, the press plate lowers into the basin. With JEI installed, click the arrow to see every slimeball's result.

Each type always gives the same result:

| Slimeball | Result |
| --- | --- |
| <ItemSlot id="productiveslimes:slimeball" slime="stone" label /> | <ItemSlot id="minecraft:stone" :count="2" label /> |
| <ItemSlot id="productiveslimes:slimeball" slime="iron" label /> | <ItemSlot id="minecraft:iron_ingot" label /> |
| <ItemSlot id="productiveslimes:slimeball" slime="diamond" label /> | <ItemSlot id="minecraft:diamond" label /> |

The [slime types](./slime-types) page lists the result of every type.

When both output slots are full or hold a different item, the squeezer waits. Hoppers and pipes can fill and empty it, see [Automation](./automation).
