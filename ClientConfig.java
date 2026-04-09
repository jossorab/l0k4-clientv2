package com.ll0k4.client.modules.hud;

import com.ll0k4.client.core.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ArmorStatusModule extends Module {

    private int color = 0xFFFFFFFF;

    public ArmorStatusModule() {
        super("Armor Status", "Affiche la durabilité de l'armure", Category.HUD);
    }

    public void render(DrawContext context, TextRenderer tr) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        EquipmentSlot[] slots = {
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
        };

        int screenH = mc.getWindow().getScaledHeight();
        int x = 2;
        int y = screenH - 70;

        for (EquipmentSlot slot : slots) {
            ItemStack stack = mc.player.getEquippedStack(slot);
            if (stack.isEmpty() || stack.getItem() == Items.AIR) continue;

            int maxDur  = stack.getMaxDamage();
            int curDur  = maxDur - stack.getDamage();
            float ratio = maxDur > 0 ? (float) curDur / maxDur : 1f;

            int barColor = ratio > 0.6f ? 0xFF55FF55
                         : ratio > 0.3f ? 0xFFFFAA00
                         : 0xFFFF5555;

            // Icône de l'item
            context.drawItem(stack, x, y);
            // Barre de durabilité
            context.fill(x + 18, y + 6, x + 18 + 14, y + 8, 0xFF333333);
            context.fill(x + 18, y + 6, x + 18 + (int)(14 * ratio), y + 8, barColor);
            // Valeur
            context.drawTextWithShadow(tr, "§7" + curDur, x + 34, y + 4, color);

            y += 18;
        }
    }

    public int getColor() { return color; }
}
