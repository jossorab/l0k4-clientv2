package com.ll0k4.client.modules.gameplay;

import com.ll0k4.client.core.Module;

public class ZoomModule extends Module {

    private boolean zooming = false;

    // FOV cible et actuel pour le lissage
    private double targetFovMultiplier = 1.0;
    private double currentFovMultiplier = 1.0;

    // Scroll ajuste le niveau de zoom
    private double scrollLevel = 0.0; // -10 à +10
    private static final double BASE_ZOOM = 0.25; // FOV x0.25 = zoom x4
    private static final double SMOOTHING  = 0.15; // Facteur de lissage (0 = instantané)

    public ZoomModule() {
        super("Zoom", "Zoom progressif (maintenez C)", Category.GAMEPLAY);
    }

    /**
     * Modifie le FOV de façon fluide.
     * Appelé par GameRendererMixin à chaque frame.
     */
    public double getZoomedFov(double originalFov) {
        // Cible : BASE_ZOOM modifié par le scroll
        targetFovMultiplier = zooming
                ? Math.max(0.05, BASE_ZOOM - scrollLevel * 0.02)
                : 1.0;

        // Lissage exponentiel
        currentFovMultiplier += (targetFovMultiplier - currentFovMultiplier) * SMOOTHING;

        return originalFov * currentFovMultiplier;
    }

    public void adjustZoom(double delta) {
        scrollLevel = Math.max(-10, Math.min(10, scrollLevel + delta));
    }

    public void setZooming(boolean state) {
        this.zooming = state;
        if (!state) scrollLevel = 0; // Reset scroll au relâcher
    }

    public boolean isZooming() { return zooming; }
}
