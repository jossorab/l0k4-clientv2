package com.ll0k4.client.modules.gameplay;

import com.ll0k4.client.core.Module;
import net.minecraft.client.MinecraftClient;

public class FullbrightModule extends Module {

    private float savedGamma = 1.0f;

    public FullbrightModule() {
        super("Fullbright", "Luminosité maximale sans torches", Category.GAMEPLAY);
    }

    @Override
    public void onEnable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        savedGamma = mc.options.getGamma().getValue().floatValue();
        // Le vrai effet est appliqué par GameRendererMixin via getNightVisionStrength
    }

    @Override
    public void onDisable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        // Restaure le gamma sauvegardé
        mc.options.getGamma().setValue((double) savedGamma);
    }
}
