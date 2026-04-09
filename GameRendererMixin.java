package com.ll0k4.client.modules.hud;

import com.ll0k4.client.core.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class CoordinatesModule extends Module {

    private int color = 0xFFFFFFFF;
    private boolean showNether = true; // Affiche aussi les coords nether/overworld correspondantes

    public CoordinatesModule() {
        super("Coordonnées", "Affiche X Y Z et direction", Category.HUD);
    }

    public List<String> getDisplayLines() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return List.of();

        BlockPos pos = mc.player.getBlockPos();
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();

        String main = String.format("§7XYZ: §f%d §7/ §f%d §7/ §f%d", x, y, z);

        if (showNether && mc.world != null) {
            String key = mc.world.getRegistryKey().getValue().getPath();
            if (key.equals("overworld")) {
                String nether = String.format("§8Nether: §7%d / %d", x / 8, z / 8);
                return List.of(main, nether);
            } else if (key.equals("the_nether")) {
                String ow = String.format("§8Overworld: §7%d / %d", x * 8, z * 8);
                return List.of(main, ow);
            }
        }
        return List.of(main);
    }

    public int getColor() { return color; }
    public boolean isShowNether() { return showNether; }
    public void setShowNether(boolean v) { this.showNether = v; }
}
