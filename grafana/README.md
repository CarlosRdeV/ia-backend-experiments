# 📊 Dashboard de Observabilidad - OpenAI + Spring Boot

Este dashboard fue creado para monitorear la interacción entre una app backend y la API de OpenAI. Incluye métricas personalizadas, salud del sistema, alertas y... drama visual.

## 🔧 Métricas destacadas

- `openai_prompts_processed_total`
- `openai_prompts_failed_total`
- `openai_latency_ms`
- Estado de servicio (`up`)

## 🔔 Alertas configuradas

| Nombre alerta         | Descripción                              |
|-----------------------|------------------------------------------|
| 😵 ¿OpenAI sigue vivo? | Verifica si OpenAI responde a los health |
| 🚨 No abuses el API    | Detecta uso excesivo (tipo spam)         |
| 😒 Prompts fallidos    | Cuenta cuando todo empieza a fallar      |

## 📁 Estructura

- `dashboard-openai-observability.json`: Dashboard para importar en Grafana Cloud.
- `alert-rules.md`: Descripción de reglas de alerta (o copia de las queries si las exportas).

## 🚀 Cómo importar

1. Ve a Grafana → Dashboards → Import.
2. Carga el JSON.
3. Asocia con tu fuente de datos Prometheus.

---

Hecho con ❤️, ironía y un poquito de sufrimiento.
