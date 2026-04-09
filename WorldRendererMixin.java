package com.ll0k4.client.modules.hud;

import com.ll0k4.client.core.Module;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

import java.util.ArrayDeque;
import java.util.Deque;

public class CpsModule extends Module {

    private static final int WINDOW_MS = 1000; // Fenêtre 1 seconde
    private final Deque<Long> leftClicks  = new ArrayDeque<>();
    private final Deque<Long> rightClicks = new ArrayDeque<>();
    private int color = 0xFF00AAFF;

    public CpsModule() {
        super("CPS", "Clics par seconde (gauche/droite)", Category.HUD);
    }

    @Override
    public void onInit() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            long now = System.currentTimeMillis();

            // Détection clics
            if (client.options.attackKey.isPressed()) {
                leftClicks.addLast(now);
            }
            if (client.options.useKey.isPressed()) {
                rightClicks.addLast(now);
            }

            // Nettoyage fenêtre glissante
            pruneOld(leftClicks, now);
            pruneOld(rightClicks, now);
        });
    }

    private void pruneOld(Deque<Long> clicks, long now) {
        while (!clicks.isEmpty() && now - clicks.peekFirst() > WINDOW_MS) {
            clicks.pollFirst();
        }
    }

    public String getDisplayText() {
        return "§7CPS: §b" + leftClicks.size() + "§7/§b" + rightClicks.size();
    }

    public int getColor() { return color; }
}
