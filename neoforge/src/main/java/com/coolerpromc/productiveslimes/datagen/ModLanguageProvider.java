package com.coolerpromc.productiveslimes.datagen;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.entity.ModEntities;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.slime.SlimeTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, Constants.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ModBlocks.ALL.forEach(block -> add(block.get(), title(block.id().getPath())));

        add(ModEntities.PRODUCTIVE_SLIME.get(), "Productive Slime");
        add("entity.productiveslimes.productive_slime.typed", "%s Slime");

        add(ModItems.SLIMEBALL.get(), "Slimeball");
        add("item.productiveslimes.slimeball.typed", "%s Slimeball");
        add(ModItems.SLIME_JAR.get(), "Slime Jar");
        add("item.productiveslimes.slime_jar.filled", "Jar of %s Slime");
        add(ModItems.PRODUCTIVE_SLIME_SPAWN_EGG.get(), "Productive Slime Spawn Egg");
        add("item.productiveslimes.productive_slime_spawn_egg.typed", "%s Slime Spawn Egg");
        add(ModItems.SLIME_CRYSTAL.get(), "Slime Crystal");
        add(ModItems.SPEED_UPGRADE.get(), "Speed Upgrade");
        add(ModItems.ADVANCED_SPEED_UPGRADE.get(), "Advanced Speed Upgrade");
        add(ModItems.GUIDE_BOOK.get(), "Productive Slimes Guide");

        add("creativetab.productiveslimes", "Productive Slimes");
        add("creativetab.productiveslimes.slimes", "Productive Slimes: Slimes");

        add("container.productiveslimes.slime_pen", "Slime Pen");
        add("container.productiveslimes.slime_squeezer", "Slime Squeezer");
        add("gui.productiveslimes.slime_pen.empty", "Insert a slime jar");
        add("gui.productiveslimes.slime_pen.full", "Output full");
        add("gui.productiveslimes.slime_pen.fed", "2x");
        add("gui.productiveslimes.slime_pen.fed.tooltip", "Fed: %s batches left at 2x speed");
        add("gui.productiveslimes.slime_pen.slot.jar", "Slime Jar");
        add("gui.productiveslimes.slime_pen.slot.jar.desc", "A jar with a slime in it");
        add("gui.productiveslimes.slime_pen.slot.food", "Slime Food (optional)");
        add("gui.productiveslimes.slime_pen.slot.food.desc", "1 item feeds 4 batches at 2x speed");
        add("gui.productiveslimes.slime_pen.slot.upgrade", "Speed Upgrade");
        add("gui.productiveslimes.slime_pen.slot.upgrade.desc", "Makes every batch faster");
        add("gui.productiveslimes.slime_squeezer.slot.input", "Slimeball");
        add("gui.productiveslimes.slime_squeezer.slot.input.desc", "Any productive slime's slimeball");
        add("gui.productiveslimes.slime_squeezer.slot.upgrade", "Speed Upgrade");
        add("gui.productiveslimes.slime_squeezer.slot.upgrade.desc", "Makes every squeeze faster");

        add("tooltip.productiveslimes.slime_size", "Size %s/%s");
        add("tooltip.productiveslimes.slime_jar.capture", "Use on a productive slime to capture it");
        add("tooltip.productiveslimes.slime_jar.release", "Use on a block to release it, or place it in a Slime Pen");
        add("tooltip.productiveslimes.speed_upgrade", "+%s%% speed in the Slime Pen and Slime Squeezer");

        add("message.productiveslimes.slime.max_size", "This slime cannot grow any bigger");
        add("message.productiveslimes.slime.need_food", "Needs %s %s to grow");
        add("message.productiveslimes.slime.cooldown", "Resting, can breed again in %ss");

        add("biome.productiveslimes.slimy_land", "Slimy Land");

        add("advancements.productiveslimes.root.title", "Productive Slimes");
        add("advancements.productiveslimes.root.description", "Slimes that make resources. Read your guide book!");
        add("advancements.productiveslimes.slimy_land.title", "Into the Goo");
        add("advancements.productiveslimes.slimy_land.description", "Find the Slimy Land");
        add("advancements.productiveslimes.slime_crystal.title", "Crystallized");
        add("advancements.productiveslimes.slime_crystal.description", "Mine a Slime Crystal");
        add("advancements.productiveslimes.capture_slime.title", "Gotcha!");
        add("advancements.productiveslimes.capture_slime.description", "Catch a slime in a Slime Jar");
        add("advancements.productiveslimes.big_slime.title", "Heavyweight");
        add("advancements.productiveslimes.big_slime.description", "Catch a size 4 slime");
        add("advancements.productiveslimes.slime_pen.title", "Home Sweet Pen");
        add("advancements.productiveslimes.slime_pen.description", "Build a Slime Pen");
        add("advancements.productiveslimes.slime_squeezer.title", "Squeeze the Day");
        add("advancements.productiveslimes.slime_squeezer.description", "Build a Slime Squeezer");
        add("advancements.productiveslimes.speed_upgrade.title", "Need for Speed");
        add("advancements.productiveslimes.speed_upgrade.description", "Make a Speed Upgrade");
        add("advancements.productiveslimes.advanced_speed_upgrade.title", "Overclocked");
        add("advancements.productiveslimes.advanced_speed_upgrade.description", "Make an Advanced Speed Upgrade");
        add("advancements.productiveslimes.breed_slime.title", "A New Generation");
        add("advancements.productiveslimes.breed_slime.description", "Breed two slimes");
        add("advancements.productiveslimes.breed_tier_3.title", "Iron Age");
        add("advancements.productiveslimes.breed_tier_3.description", "Breed a tier 3 slime, like an Iron Slime");
        add("advancements.productiveslimes.breed_tier_5.title", "Shiny!");
        add("advancements.productiveslimes.breed_tier_5.description", "Breed a tier 5 slime, like a Diamond Slime");
        add("advancements.productiveslimes.breed_tier_6.title", "Slime of Legends");
        add("advancements.productiveslimes.breed_tier_6.description", "Breed a Netherite Slime");
        add("guide.productiveslimes.page.1", "§2§lProductive Slimes§r\n\nRaise slimes that make resources.\n\nThis guide covers the basics. With JEI installed, press R on a spawn egg or slime jar to see how to breed it.");
        add("guide.productiveslimes.page.2", "§2§lThe Slimy Land§r\n\nA bright green biome found in mild climates. Wild productive slimes only spawn here, and Slime Crystal Ore only forms here, inside Slimy Stone.");
        add("guide.productiveslimes.page.3", "§2§lSlime Crystal§r\n\nMine Slime Crystal Ore with a stone pickaxe or better. Crystal goes into jars, pens, squeezers and upgrades. Slime Shrines hold a few too.");
        add("guide.productiveslimes.page.4", "§2§lSlime Jar§r\n\nRight-click a productive slime with an empty jar to catch it. Right-click a block to let it out. The jar keeps the slime's type, size and name.");
        add("guide.productiveslimes.page.5", "§2§lFeeding§r\n\nEach slime eats one food: a Stone Slime eats cobblestone. A size 1 slime grows to size 2 after 4 food. Sneak-feed an adult to grow it: 8 food to size 3, 12 to size 4.");
        add("guide.productiveslimes.page.6", "§2§lBreeding§r\n\nFeed two adults (size 2+) their food. They find each other and make a size 1 baby, then rest for 5 minutes.");
        add("guide.productiveslimes.page.7", "§2§lCatalysts§r\n\nMost recipes need a block within 2 blocks of the parents. Stone + Stone next to Andesite makes an Andesite Slime. Two of one type make that type when no catalyst matches.");
        add("guide.productiveslimes.page.8", "§2§lSlime Pen§r\n\nPut a filled jar in a pen. Every batch gives one slimeball per slime size, so grow your slimes before penning them.");
        add("guide.productiveslimes.page.9", "§2§lPen Food§r\n\nPut the slime's food in the pen's food slot. One item keeps the slime fed for 4 batches, and fed batches run twice as fast.");
        add("guide.productiveslimes.page.10", "§2§lSlime Squeezer§r\n\nPress slimeballs into their resource: an Iron Slimeball gives an Iron Ingot, a Stone Slimeball gives 2 Stone.");
        add("guide.productiveslimes.page.11", "§2§lSpeed Upgrades§r\n\nThe Slime Pen and the Slime Squeezer each take two upgrades. Speed: 25% faster. Advanced: twice as fast. Two upgrades stack.");
        add("guide.productiveslimes.page.12", "§2§lAutomation§r\n\nHoppers and pipes work.\n\nPen: jars go in the top, food in the sides, slimeballs come out the bottom.\n\nSqueezer: slimeballs go in the top or sides, results come out the bottom.");

        add("jei.productiveslimes.breeding", "Slime Breeding");
        add("jei.productiveslimes.squeezing", "Slime Squeezing");
        add("jei.productiveslimes.production", "Slime Pen Production");
        add("jei.productiveslimes.breeding.parents", "%s + %s");
        add("jei.productiveslimes.breeding.catalyst", "Near %s");
        add("jei.productiveslimes.breeding.catalyst.consumed", "Near %s (used up)");
        add("jei.productiveslimes.breeding.no_catalyst", "No catalyst block needed");
        add("jei.productiveslimes.breeding.same_type", "Same type, no catalyst needed");
        add("jei.productiveslimes.breeding.requirement", "Adults only, %2$s min cooldown");
        add("jei.productiveslimes.breeding.consumed", "The block is destroyed when the baby is born");
        add("jei.productiveslimes.breeding.parent.tooltip", "Parent: must be an adult (size 2 or more)");
        add("jei.productiveslimes.breeding.food.tooltip", "Feed this to the %s parent to start breeding");
        add("jei.productiveslimes.breeding.catalyst.tooltip", "Place within %s blocks of the parents");
        add("jei.productiveslimes.breeding.baby.tooltip", "The baby is born at size 1");
        add("jei.productiveslimes.production.time", "%s s per batch");
        add("jei.productiveslimes.production.wild", "Wild in the Slimy Land");
        add("jei.productiveslimes.info.slime_crystal", "Mine Slime Crystal Ore, found underground in the Slimy Land biome. Slime Shrine chests also hold a few.");
        add("jei.productiveslimes.info.slime_crystal_ore", "Generates only in the Slimy Land biome, between Y 0 and Y 110. Needs a stone pickaxe or better.");
        add("jei.productiveslimes.production.size", "1 slimeball per slime size");
        add("jei.productiveslimes.production.food", "1 food = 4 batches at 2x speed");

        add("jade.productiveslimes.slime_type", "Type: %s");
        add("jade.productiveslimes.size", "Size: %s/%s");
        add("jade.productiveslimes.growth", "Growth: %s/%s food");
        add("jade.productiveslimes.next_drop", "Next slimeball in %ss");
        add("jade.productiveslimes.cooldown", "Breed cooldown: %ss");
        add("jade.productiveslimes.in_love", "Looking for a partner");
        add("jade.productiveslimes.pen.next", "Next batch in %ss");
        add("jade.productiveslimes.pen.empty", "No slime inside");
        add("jade.productiveslimes.pen.full", "Output full");
        add("jade.productiveslimes.pen.fed", "Fed: 2x speed, %s batches left");
        add("jade.productiveslimes.pen.food", "Food: %s");
        add("config.jade.plugin_productiveslimes.productive_slime", "Productive Slime");
        add("config.jade.plugin_productiveslimes.slime_pen", "Slime Pen");

        for (ResourceKey<?> key : slimeTypeKeys()) {
            String path = key.identifier().getPath();
            add("slime_type." + key.identifier().getNamespace() + "." + path, path.equals("slime") ? "Green" : title(path));
        }
    }

    private static Iterable<ResourceKey<?>> slimeTypeKeys() {
        return Arrays.stream(SlimeTypes.class.getFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()) && field.getType() == ResourceKey.class && !field.getName().equals("DEFAULT"))
                .<ResourceKey<?>>map(ModLanguageProvider::read)
                .toList();
    }

    private static ResourceKey<?> read(Field field) {
        try {
            return (ResourceKey<?>) field.get(null);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private static String title(String path) {
        return Arrays.stream(path.split("_")).map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1)).collect(Collectors.joining(" "));
    }
}
