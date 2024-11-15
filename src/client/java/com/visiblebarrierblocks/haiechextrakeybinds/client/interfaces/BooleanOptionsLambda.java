package com.visiblebarrierblocks.haiechextrakeybinds.client.interfaces;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;

public interface BooleanOptionsLambda {
    SimpleOption<Boolean> operation(MinecraftClient client);
}
