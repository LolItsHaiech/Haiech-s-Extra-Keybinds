package com.visiblebarrierblocks.haiechextrakeybinds.client.init;

import com.visiblebarrierblocks.haiechextrakeybinds.client.interfaces.BooleanOptionsLambda;
import com.visiblebarrierblocks.haiechextrakeybinds.client.interfaces.IntegerOptionsLambda;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.network.message.ChatVisibility;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputInit {
    public static final String MAIN_CATEGORY_KEY = "key.category.haiechextrakeybinds";

    public static KeyBinding TOGGLE_SPRINT = registerBoolean("key.haiechextrakeybinds.toggle_sprint",
            GLFW.GLFW_KEY_O, client -> client.options.getSprintToggled());

    public static KeyBinding TOGGLE_SNEAK = registerBoolean("key.haiechextrakeybinds.toggle_sneak",
            GLFW.GLFW_KEY_K, client -> client.options.getSneakToggled());

    public static KeyBinding AUTO_JUMP = registerBoolean("key.haiechextrakeybinds.auto_jump",
            GLFW.GLFW_KEY_U, client -> client.options.getAutoJump());

    public static KeyBinding VIEW_BOBBING = registerBoolean("key.haiechextrakeybinds.view_bobbing",
            GLFW.GLFW_KEY_U, client -> client.options.getBobView());

    public static KeyBinding SHOW_SUBTITLES = registerBoolean("key.haiechextrakeybinds.show_subtitles",
            GLFW.GLFW_KEY_I, client -> client.options.getShowSubtitles());

    public static KeyBinding CHAT_VISIBILITY = registerChatVisibility("key.haiechextrakeybinds.chat_visibility",
            GLFW.GLFW_KEY_I);

    public static KeyBinding RENDER_DISTANCE_INCREMENT = registerIntegerIncrement("key.haiechextrakeybinds.render_distance.increment",
            GLFW.GLFW_KEY_KP_ADD, client -> client.options.getViewDistance(), 32);

    public static KeyBinding RENDER_DISTANCE_DECREMENT = registerIntegerDecrement("key.haiechextrakeybinds.render_distance.decrement",
            GLFW.GLFW_KEY_KP_SUBTRACT, client -> client.options.getViewDistance(), 2);

    public static KeyBinding SIMULATION_DISTANCE_INCREMENT = registerIntegerIncrement("key.haiechextrakeybinds.simulation_distance.increment",
            GLFW.GLFW_KEY_KP_9, client -> client.options.getViewDistance(), 32);

    public static KeyBinding SIMULATION_DISTANCE_DECREMENT = registerIntegerDecrement("key.haiechextrakeybinds.simulation_distance.decrement",
            GLFW.GLFW_KEY_KP_MULTIPLY, client -> client.options.getViewDistance(), 5);



    public static KeyBinding registerIntegerIncrement(String id, int key, IntegerOptionsLambda option, int max) {
        KeyBinding registry = KeyBindingHelper.registerKeyBinding(new KeyBinding(id, key, MAIN_CATEGORY_KEY));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (registry.isPressed()) {
                SimpleOption<Integer> opt = option.operation(client);
                if(max > opt.getValue()){
                    opt.setValue(opt.getValue() + 1);
                    client.player.sendMessage(Text.translatable(id + ".message", opt.getValue()), true);
                } else {
                    client.player.sendMessage(Text.translatable(id + ".message.failed", opt.getValue()), true);
                }
            }
        });
        return registry;
    }

    public static KeyBinding registerIntegerDecrement(String id, int key, IntegerOptionsLambda option, int min) {
        KeyBinding registry = KeyBindingHelper.registerKeyBinding(new KeyBinding(id, key, MAIN_CATEGORY_KEY));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (registry.isPressed()) {
                SimpleOption<Integer> opt = option.operation(client);
                if (min < opt.getValue()) {
                    opt.setValue(opt.getValue() - 1);
                    client.player.sendMessage(Text.translatable(id + ".message", opt.getValue()), true);
                } else {
                    client.player.sendMessage(Text.translatable(id + ".message.failed", opt.getValue()), true);
                }
            }
        });
        return registry;
    }


    public static KeyBinding registerBoolean(String id, int key, BooleanOptionsLambda option) {
        KeyBinding registry = KeyBindingHelper.registerKeyBinding(new KeyBinding(id, key, MAIN_CATEGORY_KEY));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (registry.isPressed()) {
                option.operation(client).setValue(!option.operation(client).getValue());
                client.player.sendMessage(Text.translatable(
                        id + (option.operation(client).getValue() ? ".on" : ".off")), true);
            }
        });
        return registry;
    }

    public static KeyBinding registerChatVisibility(String id, int key) {
        KeyBinding registry = KeyBindingHelper.registerKeyBinding(new KeyBinding(id, key, MAIN_CATEGORY_KEY));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (registry.isPressed()) {
                client.options.getChatVisibility().setValue(client.options.getChatVisibility().getValue() == ChatVisibility.HIDDEN ?
                        ChatVisibility.FULL : ChatVisibility.HIDDEN);
                client.player.sendMessage(Text.translatable(
                        id + (client.options.getChatVisibility().getValue() == ChatVisibility.HIDDEN ? ".hidden" : ".shown")), true);
            }
        });
        return registry;
    }


    public static void init() {
    }
}
