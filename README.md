# 🧠 IA Backend Experiments

Repositorio experimental para construir e integrar servicios de IA usando **Spring Boot** y la **API de OpenAI**.  
Ahora con trazabilidad, métricas, alertas y una obsesión ligeramente tóxica por el monitoreo.

Este proyecto comenzó como un demo simple... y ahora corre en contenedores, se autoobserva, y si lo dejaras, probablemente te enviaría un email a las 3 a.m. diciendo “algo está mal”.

---

## 🚀 Características principales

| Funcionalidad         | Descripción                                                                 |
|-----------------------|-----------------------------------------------------------------------------|
| 🧠 OpenAI + Spring     | Llamadas a OpenAI vía WebClient, con reintentos y control de errores.       |
| 🧾 Logs persistentes   | Todos los prompts y respuestas se guardan con `traceId` incluido.           |
| 🔍 Trazabilidad        | MDC propagado manualmente entre hilos, gracias a una cola asíncrona.        |
| 📊 Métricas            | Procesados, fallidos y latencia de OpenAI, vía Micrometer + Prometheus.     |
| 📡 Observabilidad      | `/actuator/prometheus`, `/actuator/health` y más desde Spring Actuator.     |
| ⚠️ Alertas integradas  | Configuradas en Grafana Cloud (y lloran con estilo).                        |
| 🧰 Docker Ready        | Dockerfile + docker-compose incluido. Sí, con Alloy también.                |
| 🔄 Background Queue    | Una cola con ejecución controlada simula procesamiento asincrónico real.   |

---

## ⚙️ Tecnologías

- Java 21
- Spring Boot 3.2+
- WebFlux (`WebClient`)
- H2 Database
- Micrometer + Prometheus
- Grafana Cloud + Alloy
- Logback (JSON logs)
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

### 4. Ejecutar con Docker


```bash
./mvnw clean package -DskipTests
docker-compose up --build
```

> Asegúrate de tener configurado `JAVA_HOME` y que el archivo `.env` no esté roto emocionalmente.

> Asegúrate de tener configurado Alloy para recolección de métricas.
Ver config.alloy incluido en el repositorio.
---

## 📬 API

### `POST /api/ia/complete`

Envía un prompt a OpenAI.  

#### Solicitud:

```json
{
  "prompt": "Dame una idea de startup en 2025"
}
```

#### Respuesta inmediata:

```json
{
  "response": "🕐 Prompt recibido. Procesando en background..."
}
```

📜 **La respuesta real llega después, y queda registrada en la base de datos (y en los logs estructurados con traceId)**

## ⚙️ ¿Por qué no se retorna la respuesta directamente?

Para evitar sobrecargar el endpoint en caso de múltiples peticiones concurrentes y simular un flujo realista de procesamiento.  
También porque OpenAI no aprueba mis decisiones de diseño aparentemente. 🥲

---

### `POST /api/prompts`

Consulta paginada de prompts procesados.

Parámetros disponibles:

- page (por defecto 0)
- size (por defecto 10)
- traceId (opcional)

#### Solicitud:

```
  /prompts?page=0&size=5&traceId=abc-123
```
---

## 📊 Observabilidad
Métricas disponibles en `/actuator/prometheus:`

- openai_prompts_processed_total
- openai_prompts_failed_total
- openai_latency_millis
- http_server_requests_seconds

Integrado con:

- Grafana Cloud para visualización
- Alloy para scrape local y forwarding a Prometheus remoto

## 📚 📁 Archivos clave

```
├── src/
│   ├── controller/             # Endpoint REST principal
│   ├── service/                # Lógica del negocio (incluye cola asincrónica)
│   ├── model/                  # Entidad PromptLog
│   ├── config/                 # Seguridad, métricas
│   └── util/                   # MDC + Runnable Wrapper
├── Dockerfile
├── docker-compose.yml
├── config.alloy               # Config de Alloy para enviar métricas a Grafana Cloud
├── alert-rules.md             # Reglas de alerta
└── README.md ← este mismo

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

Solo no me culpes si se convierte en IA autoconsciente y decide juzgar tus prompts.

---

## 🔧 Cómo iniciar localmente

```bash
docker-compose up --build
```

---

## 🤙 Contacto

¿Te gustó? ¿Te sirvió?
Menciónalo en tu post de LinkedIn y parecerás más pro.
Yo ya lo hice.

---

