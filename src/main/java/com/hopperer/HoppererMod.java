package com.hopperer;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HoppererMod implements ModInitializer {
    public static final String MOD_ID = "hopperer";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hopperer has initialized successfully! Fast hoppers are online.");
    }
}