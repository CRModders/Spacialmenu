package io.github.crmodders.spacialmenu;

import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class SpacialMenuMod implements ModInitializer {
    public static final String MOD_ID = "spacialmenu";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello non Cosmic world!");
    }
}