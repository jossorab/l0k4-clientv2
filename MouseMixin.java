package com.ll0k4.client.modules.hud;

import com.ll0k4.client.core.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

public class DirectionModule extends Module {

    private int color = 0xFFFFFFAA;

    public DirectionModule() {
        super("Direction", "Affiche la direction cardinale et yaw/pitch", Category.HUD);
    }

    public String getDisplayText() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return "";

        float yaw = MathHelper.wrapDegrees(mc.player.getYaw());
        float pitch = mc.player.getPitch();

        String cardinal = getCardinal(yaw);
        return String.format("§7Dir: §e%s §7(§f%.1f°§7) P:§f%.1f°", cardinal, yaw, pitch);
    }

    private String getCardinal(float yaw) {
        // Minecraft: 0=Sud, 90=Ouest, -90=Est, 180/-180=Nord
        if (yaw >= -45 && yaw < 45)   return "§cS";
        if (yaw >= 45  && yaw < 135)  return "§aW";
        if (yaw >= -135 && yaw < -45) return "§aE";
        return "§9N";
    }

    public int getColor() { return color; }
}
