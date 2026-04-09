#!/bin/bash
# ============================================================
#  lL0k4 Client — Setup & Build pour Minecraft 26.1
#  Usage : ./setup.sh
# ============================================================

set -e

echo ""
echo "  ██╗     ██╗      ██████╗ ██╗  ██╗██╗  ██╗"
echo "  ██║     ██║     ██╔═████╗██║ ██╔╝██║  ██║"
echo "  ██║     ██║     ██║██╔██║█████╔╝ ███████║"
echo "  ██║     ██║     ████╔╝██║██╔═██╗ ╚════██║"
echo "  ███████╗███████╗╚██████╔╝██║  ██╗      ██║"
echo "  ╚══════╝╚══════╝ ╚═════╝ ╚═╝  ╚═╝      ╚═╝"
echo "         Client Mod — Minecraft 26.1"
echo ""

# --- Vérification Java 25 ---
JAVA_VER=$(java -version 2>&1 | head -1 | sed 's/.*version "\([0-9]*\).*/\1/')
echo "→ Java détecté : version $JAVA_VER"
if [ "$JAVA_VER" -lt 25 ] 2>/dev/null; then
  echo "✗ Java 25 requis ! Télécharge-le sur : https://adoptium.net/"
  exit 1
fi
echo "✓ Java OK"

# --- Bootstrap Gradle Wrapper si le .jar est manquant ---
if [ ! -f "gradle/wrapper/gradle-wrapper.jar" ]; then
  echo "→ Téléchargement du Gradle Wrapper..."
  mkdir -p gradle/wrapper
  curl -fsSL \
    "https://repo1.maven.org/maven2/org/gradle/gradle-wrapper/9.4.0/gradle-wrapper-9.4.0.jar" \
    -o gradle/wrapper/gradle-wrapper.jar
  echo "✓ Gradle Wrapper téléchargé"
fi

# --- Build ---
echo ""
echo "→ Compilation du mod..."
chmod +x gradlew
./gradlew build --no-daemon

echo ""
echo "✓ Build terminé !"
echo ""
echo "  ► JAR disponible dans : build/libs/"
ls -lh build/libs/*.jar 2>/dev/null || true
echo ""
echo "  Copie dans le dossier mods Minecraft :"
echo "  cp build/libs/ll0k4client-*.jar ~/.minecraft/mods/"
echo ""
