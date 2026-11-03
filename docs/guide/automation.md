# Automation

The Slime Pen and the Slime Squeezer work with hoppers and with item pipes from other mods. Which slot an item goes into depends on the side it enters from.

## Slime Pen

| Side | What it does |
| --- | --- |
| Top | Inserts a filled [Slime Jar](./slime-jar) |
| Sides | Insert the slime's food |
| Bottom | Takes slimeballs out of the output |

Food for a different slime type is refused, so a pipe can't fill the food slot with the wrong item.

## Slime Squeezer

| Side | What it does |
| --- | --- |
| Top and sides | Insert slimeballs |
| Bottom | Takes the results out |

## A full chain

Pen, squeezer and chest can be chained with three hoppers, so the resource collects in a chest on its own:

1. A hopper **above the pen** holding a filled jar (optional, only needed once).
2. A hopper **on the side of the pen**, pointing into it, holding the slime's food.
3. A hopper **under the pen**, pointing sideways into the squeezer.
4. A hopper **under the squeezer**, pointing down into a chest.

::: tip
Speed upgrades can't be inserted with hoppers. Put them in by hand through each machine's interface.
:::
