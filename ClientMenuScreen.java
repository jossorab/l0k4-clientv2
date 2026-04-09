package com.ll0k4.client.modules.gameplay;

import com.ll0k4.client.core.Module;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.lwjgl.glfw.GLFW;

public class ToggleSneakModule extends Module {

    private boolean sneakToggled = false;
    private KeyBinding sneakKey;
    private boolean wasPressed = false;

    public ToggleSneakModule() {
        super("Toggle Sneak", "Active/désactive le sneak permanent", Category.GAMEPLAY);
    }

    @Override
    public void onInit() {
        sneakKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ll0k4client.togglesneak",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_SHIFT,
                "category.ll0k4client"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!isEnabled()) return;
            // Toggle au press, pas en continu
            boolean pressed = sneakKey.isPressed();
            if (pressed && !wasPressed) {
                sneakToggled = !sneakToggled;
            }
            wasPressed = pressed;
        });
    }

    public boolean isSneakToggled() { return sneakToggled; }
}
