# Slime Pen

The Slime Pen holds one jarred slime and collects its slimeballs on a fixed timer. It is the main way to farm resources. You can see the slime inside the pen, at its own size and color.

<RecipeCard id="slime_pen" />

## The interface

![Slime Pen interface](/gui/slime_pen.png){.pixelated width=352}

| Slot | What goes there |
| --- | --- |
| Left column | Up to two speed upgrades |
| Top middle | A filled [Slime Jar](./slime-jar) |
| Bottom middle | The slime's food (optional) |
| Right grid | Output: 9 slots of slimeballs |

Hover an empty slot in game to see what it is for. The arrow fills as the current batch progresses. If the pen stops, a red message in the top-right corner says why. With JEI installed, click the arrow to see every slime's production.

## How production works

- Every batch adds **as many slimeballs as the slime's size** to the output.
- A batch takes the slime type's **pen time**: from 1 minute for tier 0 up to 10 minutes for Netherite. See [Slime types](./slime-types).
- When the output is full, the pen waits until you take something out.
- The slime never dies and never leaves the pen, and it keeps working without food.
- Hoppers and pipes can feed it and empty it. See [Automation](./automation).

## Food (optional)

Put the slime's own food in the food slot to make it work faster. When the slime is hungry it eats **1** food item, and that item keeps it fed for **4 batches**. Fed batches run **twice as fast**, including the batch already in progress when it eats. A green **2x** under the arrow shows the slime is fed; hover it to see how many fed batches are left.

Without food the pen still runs at normal speed, so food is a boost, never a requirement. Food for a different slime type is not accepted.

Because one item lasts 4 batches, feeding always pays off. A size 1 Diamond Slime eats 1 diamond and returns 4 Diamond Slimeballs, which squeeze into 4 diamonds.

## Speed upgrades

<RecipeCard id="speed_upgrade" />
<RecipeCard id="advanced_speed_upgrade" />

| Upgrade | Batch time |
| --- | --- |
| <ItemSlot id="productiveslimes:speed_upgrade" label /> | × 0.8 (25% faster) |
| <ItemSlot id="productiveslimes:advanced_speed_upgrade" label /> | × 0.5 (twice as fast) |

Two upgrades multiply together, and food multiplies on top. Two Advanced upgrades plus food bring a batch down to 1/8 of its normal time. A batch never takes less than 1 second. The same upgrades also fit the [Slime Squeezer](./slime-squeezer).

## Calculator

<PenCalculator />
