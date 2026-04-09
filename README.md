# lL0k4 Client — Fabric Mod pour Minecraft 26.1

Client utilitaire complet pour Minecraft Java Edition 26.1 (Tiny Takeover).

## 📦 Modules inclus

| Module | Catégorie | Description |
|--------|-----------|-------------|
| FPS | HUD | Compteur FPS coloré (vert/orange/rouge) |
| CPS | HUD | Clics/seconde gauche & droite |
| Coordonnées | HUD | XYZ + équivalent Nether/Overworld |
| Direction | HUD | Cardinal + yaw/pitch |
| Armor Status | HUD | Durabilité armure avec barres visuelles |
| Toggle Sprint | Gameplay | Sprint permanent automatique |
| Toggle Sneak | Gameplay | Sneak permanent (touche Shift) |
| Fullbright | Gameplay | Luminosité maximale partout |
| Zoom | Gameplay | Zoom progressif (maintenez C + scroll) |
| Hitbox | Rendu | Hitboxes permanentes + look vector, RGBA configurable |
| Old Animations | Animations | Style 1.7 (block-hit, eating) |

## 🚀 Build en une commande

**Prérequis : Java 25**

```bash
./setup.sh
```

Le JAR sera généré dans `build/libs/`.

**Ou manuellement :**
```bash
./gradlew build
# → build/libs/ll0k4client-1.0.0.jar
```

## 🎮 Installation

1. Installe [Fabric Loader 0.18.4](https://fabricmc.net/use/) pour Minecraft 26.1
2. Copie le JAR dans ton dossier `.minecraft/mods/`
3. Lance Minecraft

## ⌨️ Touches par défaut

| Touche | Action |
|--------|--------|
| `RSHIFT` | Ouvre le menu lL0k4 |
| `C` (maintenu) | Zoom |
| `LSHIFT` (si Toggle Sneak actif) | Toggle sneak on/off |

## 🛠️ Stack technique

- **Minecraft** 26.1 (Tiny Takeover)
- **Fabric Loader** 0.18.4
- **Fabric API** 0.115.0+26.1
- **Loom** 1.15 (sans remapping — Mojang Official Mappings)
- **Java** 25
- **Gradle** 9.4.0
