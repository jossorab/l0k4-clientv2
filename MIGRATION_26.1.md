# Migration vers Minecraft 26.1 (Tiny Takeover)

## Changements majeurs à savoir

### Yarn → Mojang Official Mappings
26.1 est la **première version désobfusquée nativement** par Mojang.
Yarn est abandonné à partir de cette version.

**Impact sur le build.gradle :**
```groovy
// AVANT (1.21.x)
mappings "net.fabricmc:yarn:1.21.1+build.3:v2"
modImplementation "net.fabricmc.fabric-api:fabric-api:..."

// APRÈS (26.1)
// Pas de ligne mappings du tout !
implementation "net.fabricmc.fabric-api:fabric-api:0.115.0+26.1"
```

### Java 25 requis
Mettre à jour JAVA_HOME vers JDK 25.

### Loom 1.15 : plus de remapJar
```groovy
// AVANT
remapJar { ... }
// APRÈS
jar { ... }
```

### Renommages d'API Fabric (Yarn → Mojang)
| Ancienne (Yarn)             | Nouvelle (Mojang)              |
|-----------------------------|-------------------------------|
| `ItemGroupEvents`           | `CreativeModeTabEvents`        |
| `ClientPlayerEntity`        | `LocalPlayer`                  |
| `MinecraftClient`           | `Minecraft`                    |
| `DrawContext`               | `GuiGraphics`                  |
| `BlockPos`                  | `BlockPos` (inchangé)          |
| `HudRenderCallback`         | `HudRenderCallback` (inchangé) |

> ⚠️ Les noms Mojang sont identiques à ceux utilisés dans les projets Forge/NeoForge.
> Cela facilite le portage cross-loader.

### Code dans ce projet
Les sources Java utilisent les **noms Yarn** (1.21.x).
Pour les compiler sur 26.1, utilise le script de migration automatique Fabric :
```bash
./gradlew migrateMappings --mappings "mojang+yarn"
```
Puis revérifie manuellement les Mixins (les signatures de méthodes peuvent changer).
