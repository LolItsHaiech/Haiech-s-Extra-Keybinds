package com.visiblebarrierblocks.haiechextrakeybinds.client;

import com.visiblebarrierblocks.haiechextrakeybinds.client.init.KeyInputInit;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

import java.util.logging.Logger;

public class HaiechextrakeybindsClient implements ClientModInitializer {

    public static final String MOD_ID = "haiechextrakeybinds";
    public static final String MOD_NAME = "HaiechExtraKeybinds";

    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {

        KeyInputInit.init();
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
