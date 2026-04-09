package com.ll0k4.client.modules.hud;

import com.ll0k4.client.core.Module;
import net.minecraft.client.MinecraftClient;

public class FpsModule extends Module {

    private int color = 0xFFFFAA00; // Orange

    public FpsModule() {
        super("FPS", "Affiche les FPS actuels", Category.HUD);
    }

    public String getDisplayText() {
        int fps = MinecraftClient.getInstance().getCurrentFps();
        String fpsColor = fps >= 60 ? "§a" : fps >= 30 ? "§e" : "§c";
        return "§7FPS: " + fpsColor + fps;
    }

    public int getColor() { return color; }
    public void setColor(int color) { this.color = color; }
}
