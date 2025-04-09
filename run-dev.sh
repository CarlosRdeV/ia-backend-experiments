#!/bin/bash

# ✅ Cargar variables del .env si existe
if [ -f .env ]; then
  export $(grep -v '^#' .env | xargs)
else
  echo "❌ Archivo .env no encontrado. Abortando."
  exit 1
fi

# 🧠 Setear JAVA_HOME si no está ya seteado
if [ -n "$JAVA_HOME" ]; then
  export PATH="$JAVA_HOME/bin:$PATH"
fi

# 🧪 Verificación rápida
echo "🔐 API Key... ${OPENAI_API_KEY:0:10}********"
echo "🤖 Modelo... $OPENAI_MODEL"
echo "🌐 URL...... $OPENAI_URL"
echo "📦 JAVA_HOME: $JAVA_HOME"
echo "🚀 Arrancando backend..."

# 🧨 Run the app
./mvnw spring-boot:run
