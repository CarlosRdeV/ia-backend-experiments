# 🧠 IA Backend Experiments

Repositorio experimental para construir e integrar servicios de IA usando **Spring Boot** y la **API de OpenAI**.

Este proyecto se basa en una arquitectura sólida, moderna y extensible que sirve como punto de partida para ideas futuras, demostraciones… o simplemente para impresionar reclutadores con una falsa sensación de control.

---

## 🚀 Características

- 🔐 Configuración segura de API Keys mediante `.env`
- 📦 Backend modular con estructura limpia (`controller`, `service`, `dto`, `config`)
- 🌐 Consumo de la API de OpenAI con WebClient y manejo de errores 429 (aka *“el modelo llora”*)
- 📄 Swagger UI disponible para probar endpoints
- 🐳 Dockerfile listo para producción (o al menos para presumir en LinkedIn)

---

## ⚙️ Tecnologías

- Java 17
- Spring Boot 3.4
- WebFlux (WebClient)
- OpenAI API (`gpt-3.5-turbo`)
- Lombok *(porque escribir getters es de gente sin autoestima)*
- Swagger / OpenAPI
- Docker

---

## 🛠️ Configuración del entorno

### 1. Clona el proyecto

```bash
git clone https://github.com/CarlosRdeV/ia-backend-experiments.git
cd ia-backend-experiments
```

### 2. Crea tu archivo `.env`

```bash
cp .env.example .env
```

### 3. Configura tus variables

```env
OPENAI_API_KEY=sk-...
OPENAI_MODEL=gpt-3.5-turbo
OPENAI_URL=https://api.openai.com/v1/chat/completions
```

### 4. Ejecuta el proyecto

```bash
sh run-dev.sh
```

> Asegúrate de tener configurado `JAVA_HOME` y que el archivo `.env` no esté roto emocionalmente.

---

## 📬 Endpoint disponible

### `POST /api/ia/complete`

Envía un prompt a OpenAI.  
La respuesta **no es inmediata**, porque implementamos una cola para simular procesamiento asíncrono (y porque el modelo tiene emociones, aparentemente).

#### Solicitud:

```json
{
  "prompt": "Dame una idea de startup en 2025"
}
```

#### Respuesta:

```json
{
  "response": "🕐 Prompt recibido. Procesando en background..."
}
```

📜 **La respuesta real se mostrará en los logs**, por ejemplo:

```
✅ Respuesta OpenAI: ¡Hola! ¿En qué puedo ayudarte hoy?
```

---

## ⚙️ ¿Por qué no se retorna la respuesta directamente?

Para evitar sobrecargar el endpoint en caso de múltiples peticiones concurrentes y simular un flujo realista de procesamiento.  
También porque OpenAI no aprueba mis decisiones de diseño aparentemente. 🥲

---

## 📚 Estructura del proyecto

```
├── controller
│   └── IaController.java
├── service
│   └── ia
│       ├── OpenAiService.java
│       ├── OpenAiRequestBuilder.java
│       └── OpenAiResponseParser.java
├── dto
│   ├── PromptRequest.java
│   └── IaResponse.java
└── config
    └── SecurityConfig.java
```

---

## 🧼 Notas de higiene

- `.env` está en `.gitignore`. Si lo subes por error… ya te advertí.
- Usa `.env.example` para que otros no pierdan la fe ni sus tokens.

---

## 🔪 ¿Por qué?

Porque no todo proyecto de IA necesita estar montado sobre mil microservicios.  
Y a veces solo quieres saber si **puedes conectarte a OpenAI sin que te respondan con un 429 y una lágrima virtual**.

---

## ☠️ Licencia

Este proyecto **no tiene licencia**.  
Úsalo bajo tu propio riesgo. Si te explota el CPU, no fue culpa mía.  
Pero si impresiona a alguien... sí lo hice yo.

## 🔧 Cómo iniciar localmente

```bash
docker-compose up --build