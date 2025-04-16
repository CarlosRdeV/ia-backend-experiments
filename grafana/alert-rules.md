# 🚨 Reglas de Alerta - Observabilidad OpenAI

Estas alertas fueron diseñadas para monitorear el estado de interacción entre un backend experimental y la API de OpenAI, con un toque de drama necesario para que Grafana no se sienta ignorado.

---

## ☠️ ¿OpenAI sigue vivo?

- **Descripción:** Verifica si el servicio OpenAI responde correctamente.
- **Expresión (PromQL):**

  up{job="prometheus.scrape.springboot", model="gpt-3.5-turbo"} == 0

- **Severidad:** crítica existencial
- **Acción sugerida:** Aceptar que los servidores tienen días malos también.

---

## 😡 Prompts que decepcionan a todos

- **Descripción:** Avisa si hay errores al llamar a OpenAI.
- **Expresión (PromQL):**

  increase(openai_prompts_failed_total[5m]) > 0

- **Severidad:** frustración leve a moderada
- **Consejo:** Revise su lógica o abrace el error como parte del proceso.

---

## 🤔 ¿Seguimos vivos?

- **Descripción:** Check general del backend local.
- **Expresión (PromQL):**

  up{instance="localhost:8080"} == 0

- **Severidad:** "¿Apagaste el servidor o...?"
- **Nota:** A menudo causada por olvidar correr la app. Otra vez.

---

## 🔥 No abuses el API

- **Descripción:** Detecta actividad sospechosamente intensa contra la API de OpenAI.
- **Expresión (PromQL):**

  increase(openai_prompts_processed_total[1m]) > 3

- **Severidad:** Tu billetera está temblando
- **Consejo:** ¡Caching es tu amigo! Detén la locura.

---

## 💡 Tips

- Puedes ajustar el rango de tiempo `[5m]`, `[1m]` según lo sensible que quieras las alertas.
- No olvides configurar notificaciones por email, Slack o humo digital.

---

**Exportado desde Grafana Cloud con dolor, sudor y un poquito de magia.**
