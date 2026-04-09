package com.ll0k4.client.modules.render;

import com.ll0k4.client.core.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class HitboxModule extends Module {

    // ── Filtres d'affichage ──
    private boolean showPlayers     = true;
    private boolean showMobs        = true;
    private boolean showProjectiles = true;
    private boolean showLookVector  = true;

    // ── Couleurs RGBA (packed int 0xAARRGGBB) ──
    private float[] playerColor     = {0.3f, 0.8f, 1.0f, 0.8f}; // Bleu cyan
    private float[] mobColor        = {1.0f, 0.3f, 0.3f, 0.8f}; // Rouge
    private float[] projectileColor = {1.0f, 1.0f, 0.0f, 0.8f}; // Jaune
    private float[] lookVecColor    = {1.0f, 0.5f, 0.0f, 1.0f}; // Orange

    public HitboxModule() {
        super("Hitbox", "Affichage permanent des hitboxes avec personnalisation", Category.RENDER);
    }

    /**
     * Appelé depuis WorldRendererMixin pour chaque entité visible.
     */
    public void renderHitbox(MatrixStack matrices,
                             VertexConsumerProvider.Immediate vertexConsumers,
                             Entity entity, float tickDelta) {

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        if (entity == mc.player) return; // Ne pas afficher sa propre hitbox

        float[] color = getColorFor(entity);
        if (color == null) return; // Filtre désactivé pour ce type

        // Position interpolée
        double ex = lerp(entity.lastRenderX, entity.getX(), tickDelta);
        double ey = lerp(entity.lastRenderY, entity.getY(), tickDelta);
        double ez = lerp(entity.lastRenderZ, entity.getZ(), tickDelta);

        Vec3d cam = mc.gameRenderer.getCamera().getPos();
        double dx = ex - cam.x;
        double dy = ey - cam.y;
        double dz = ez - cam.z;

        matrices.push();
        matrices.translate(dx, dy, dz);

        Box box = entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ());
        VertexConsumer lines = vertexConsumers.getBuffer(RenderLayer.getLines());
        Matrix4f mat = matrices.peek().getPositionMatrix();

        drawBox(lines, mat, box, color[0], color[1], color[2], color[3]);

        // Vecteur de regard
        if (showLookVector && entity instanceof LivingEntity living) {
            Vec3d look = living.getRotationVec(tickDelta).multiply(2.0);
            drawLine(lines, mat,
                    (float)(box.minX + box.maxX) * 0.5f,
                    (float) box.maxY,
                    (float)(box.minZ + box.maxZ) * 0.5f,
                    (float)(look.x + (box.minX + box.maxX) * 0.5),
                    (float)(look.y + box.maxY),
                    (float)(look.z + (box.minZ + box.maxZ) * 0.5),
                    lookVecColor[0], lookVecColor[1], lookVecColor[2], lookVecColor[3]
            );
        }

        matrices.pop();
        vertexConsumers.draw(RenderLayer.getLines());
    }

    private float[] getColorFor(Entity entity) {
        if (entity instanceof PlayerEntity && showPlayers)           return playerColor;
        if (entity instanceof ProjectileEntity && showProjectiles)   return projectileColor;
        if (entity instanceof MobEntity && showMobs)                 return mobColor;
        return null;
    }

    // ── Rendu bas niveau ──

    private void drawBox(VertexConsumer consumer, Matrix4f mat, Box box,
                         float r, float g, float b, float a) {
        float x0 = (float) box.minX, y0 = (float) box.minY, z0 = (float) box.minZ;
        float x1 = (float) box.maxX, y1 = (float) box.maxY, z1 = (float) box.maxZ;

        // 12 arêtes du parallélépipède
        // Bas
        drawLine(consumer, mat, x0,y0,z0, x1,y0,z0, r,g,b,a);
        drawLine(consumer, mat, x1,y0,z0, x1,y0,z1, r,g,b,a);
        drawLine(consumer, mat, x1,y0,z1, x0,y0,z1, r,g,b,a);
        drawLine(consumer, mat, x0,y0,z1, x0,y0,z0, r,g,b,a);
        // Haut
        drawLine(consumer, mat, x0,y1,z0, x1,y1,z0, r,g,b,a);
        drawLine(consumer, mat, x1,y1,z0, x1,y1,z1, r,g,b,a);
        drawLine(consumer, mat, x1,y1,z1, x0,y1,z1, r,g,b,a);
        drawLine(consumer, mat, x0,y1,z1, x0,y1,z0, r,g,b,a);
        // Colonnes
        drawLine(consumer, mat, x0,y0,z0, x0,y1,z0, r,g,b,a);
        drawLine(consumer, mat, x1,y0,z0, x1,y1,z0, r,g,b,a);
        drawLine(consumer, mat, x1,y0,z1, x1,y1,z1, r,g,b,a);
        drawLine(consumer, mat, x0,y0,z1, x0,y1,z1, r,g,b,a);
    }

    private void drawLine(VertexConsumer consumer, Matrix4f mat,
                          float x0, float y0, float z0,
                          float x1, float y1, float z1,
                          float r,  float g,  float b,  float a) {
        float nx = x1 - x0, ny = y1 - y0, nz = z1 - z0;
        float len = (float) Math.sqrt(nx*nx + ny*ny + nz*nz);
        if (len == 0) return;
        nx /= len; ny /= len; nz /= len;

        consumer.vertex(mat, x0, y0, z0).color(r, g, b, a).normal(nx, ny, nz);
        consumer.vertex(mat, x1, y1, z1).color(r, g, b, a).normal(nx, ny, nz);
    }

    private double lerp(double a, double b, float t) { return a + (b - a) * t; }

    // ── Getters / Setters ──
    public boolean isShowPlayers()      { return showPlayers; }
    public boolean isShowMobs()         { return showMobs; }
    public boolean isShowProjectiles()  { return showProjectiles; }
    public boolean isShowLookVector()   { return showLookVector; }

    public void setShowPlayers(boolean v)     { showPlayers = v; }
    public void setShowMobs(boolean v)        { showMobs = v; }
    public void setShowProjectiles(boolean v) { showProjectiles = v; }
    public void setShowLookVector(boolean v)  { showLookVector = v; }

    public float[] getPlayerColor()     { return playerColor; }
    public float[] getMobColor()        { return mobColor; }
    public float[] getProjectileColor() { return projectileColor; }
    public float[] getLookVecColor()    { return lookVecColor; }

    public void setPlayerColor(float r, float g, float b, float a)     { playerColor = new float[]{r,g,b,a}; }
    public void setMobColor(float r, float g, float b, float a)        { mobColor = new float[]{r,g,b,a}; }
    public void setProjectileColor(float r, float g, float b, float a) { projectileColor = new float[]{r,g,b,a}; }
    public void setLookVecColor(float r, float g, float b, float a)    { lookVecColor = new float[]{r,g,b,a}; }
}
