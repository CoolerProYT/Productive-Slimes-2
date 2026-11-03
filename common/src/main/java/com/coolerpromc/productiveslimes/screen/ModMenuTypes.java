package com.coolerpromc.productiveslimes.screen;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.platform.Services;
import com.coolerpromc.productiveslimes.platform.util.RegistryHandler;
import com.coolerpromc.productiveslimes.screen.custom.SlimePenMenu;
import com.coolerpromc.productiveslimes.screen.custom.SlimeSqueezerMenu;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {
    public static final RegistryHandler<MenuType<?>, MenuType<SlimePenMenu>> SLIME_PEN = Services.REGISTRY.registerMenuType("slime_pen", SlimePenMenu::new);
    public static final RegistryHandler<MenuType<?>, MenuType<SlimeSqueezerMenu>> SLIME_SQUEEZER = Services.REGISTRY.registerMenuType("slime_squeezer", SlimeSqueezerMenu::new);

    public static void load() {
        Constants.LOG.info("Registering menu types.");
    }
}
