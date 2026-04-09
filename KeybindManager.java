package com.ll0k4.client.modules.animation;

import com.ll0k4.client.core.Module;

/**
 * Recrée les animations style 1.7 :
 * - Block-hit : le bras reste dans la position d'attaque
 * - Eating    : animation tenue en main sans swing
 *
 * L'application effective passe par HeldItemRendererMixin (voir bas de fichier).
 * Ce module sert de flag central et de config.
 */
public class OldAnimationsModule extends Module {

    private boolean oldBlockhit = true;
    private boolean oldEating   = true;
    private boolean oldSword    = true;  // Position épée style 1.7

    public OldAnimationsModule() {
        super("Old Animations", "Animations style Minecraft 1.7", Category.ANIMATION);
    }

    public boolean isOldBlockhit() { return oldBlockhit && isEnabled(); }
    public boolean isOldEating()   { return oldEating   && isEnabled(); }
    public boolean isOldSword()    { return oldSword    && isEnabled(); }

    public void setOldBlockhit(boolean v) { oldBlockhit = v; }
    public void setOldEating(boolean v)   { oldEating = v; }
    public void setOldSword(boolean v)    { oldSword = v; }
}

/*
 * ─────────────────────────────────────────────────────────────────────────────
 * MIXIN COMPLÉMENTAIRE à ajouter : HeldItemRendererMixin.java
 * (à placer dans com.ll0k4.client.mixin et à enregistrer dans le JSON)
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * @Mixin(HeldItemRenderer.class)
 * public abstract class HeldItemRendererMixin {
 *
 *     // ── Block-hit style 1.7 ──
 *     // En 1.7, frapper un bloc ne déclenche pas l'animation de swing.
 *     // On annule le swing si le joueur est en train de miner.
 *     @Inject(method = "renderItem(FLnet/minecraft/client/render/Camera;...)V",
 *             at = @At("HEAD"))
 *     private void ll0k4_oldBlockhit(float tickDelta, ..., CallbackInfo ci) {
 *         OldAnimationsModule anim = Ll0k4Client.moduleManager.getModule(OldAnimationsModule.class);
 *         if (anim == null || !anim.isOldBlockhit()) return;
 *         MinecraftClient mc = MinecraftClient.getInstance();
 *         if (mc.interactionManager != null && mc.interactionManager.isBreakingBlock()) {
 *             // Forcer la main en position neutre (swingProgress = 0)
 *             mc.player.handSwingProgress = 0f;
 *             mc.player.lastHandSwingProgress = 0f;
 *         }
 *     }
 *
 *     // ── Eating style 1.7 ──
 *     // Modifie la transformation de rendu de l'item tenu
 *     // pour pointer vers l'écran plutôt que de faire un arc.
 *     @ModifyVariable(method = "renderItem(...)V", ...)
 *     private float ll0k4_oldEating(float original) {
 *         OldAnimationsModule anim = Ll0k4Client.moduleManager.getModule(OldAnimationsModule.class);
 *         if (anim == null || !anim.isOldEating()) return original;
 *         // Clamp l'arc d'animation
 *         return Math.min(original, 0.1f);
 *     }
 * }
 *
 * Note : Les signatures exactes des méthodes de HeldItemRenderer varient
 * selon le mapping Yarn utilisé. Vérifier avec : ./gradlew genSources
 * ─────────────────────────────────────────────────────────────────────────────
 */
