package com.ll0k4.client.core;

import com.ll0k4.client.Ll0k4Client;
import com.ll0k4.client.gui.ClientMenuScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeybindManager {

    private KeyBinding openMenuKey;

    public void init() {
        // RSHIFT = ouvre le menu principal lL0k4
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ll0k4client.menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.ll0k4client"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Ouvrir le menu lL0k4
            while (openMenuKey.wasPressed()) {
                client.setScreen(new ClientMenuScreen(null));
            }

            // Keybinds par module
            for (Module module : Ll0k4Client.moduleManager.getModules()) {
                int key = module.getKeybind();
                if (key != -1 && InputUtil.isKeyPressed(
                        MinecraftClient.getInstance().getWindow().getHandle(),
                        key)) {
                    // Géré dans les modules eux-mêmes via wasPressed()
                }
            }
        });
    }

    public KeyBinding getOpenMenuKey() { return openMenuKey; }
}
