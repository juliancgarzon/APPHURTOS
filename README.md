# App de Reporte de Hurtos

## Descripción

La aplicación de reporte de hurtos es una solución diseñada para que los usuarios puedan reportar incidentes relacionados con hurtos en tiempo real. Funciona como una herramienta colaborativa que permite visualizar incidentes en un mapa interactivo, recibir alertas basadas en la geolocalización y generar reportes categorizados de manera sencilla.

## Características principales

- **Geolocalización en tiempo real:** Los usuarios pueden reportar incidentes directamente desde su ubicación.
- **Visualización de incidentes:** Muestra los reportes en un mapa interactivo con detalles relevantes.
- **Categorías de incidentes:** Organización de reportes por tipo (ej. robo a vehículo, hurto a persona, etc.).
- **Sistema de notificaciones:** Envía alertas push basadas en la proximidad de incidentes recientes.
- **Filtros avanzados:** Los usuarios pueden buscar incidentes según categoría, fecha o ubicación.

## Tecnologías utilizadas

- **Frontend:** Android Studio, Kotlin
- **Backend:** Node.js, Express
- **Geolocalización:** API de Google Maps
- **Base de datos:** PostgreSQL
- **Notificaciones:** Firebase Cloud Messaging (FCM)

## Instalación

1. Clona el repositorio:
   ```bash
   git clone [https://github.com/tuusuario/app-hurtos.git](https://github.com/juliancgarzon/APPHURTOS.git)
   ```
2. Abre el proyecto en Android Studio.
3. Configura las claves de la API de Google Maps en el archivo `google_maps_api.xml`.
4. Configura Firebase:
   - Descarga el archivo `google-services.json` desde Firebase y colócalo en el directorio `app/`.
5. Configura el backend:
   - Instala las dependencias con `npm install` en el directorio del backend.
   - Configura las variables de entorno para la conexión con PostgreSQL.
6. Ejecuta la aplicación en un emulador o dispositivo físico.

## Uso

1. **Registro y login:** Los usuarios deben registrarse con un correo electrónico y una contraseña.
2. **Reportar un incidente:**
   - Selecciona "Reportar" en la pantalla principal.
   - Completa el formulario con la descripción, categoría y ubicación del incidente.
   - Envía el reporte.
3. **Ver incidentes:**
   - Accede al mapa interactivo para visualizar los incidentes reportados por otros usuarios.
   - Utiliza los filtros para buscar reportes específicos.
4. **Recibir alertas:** Activa las notificaciones push para ser notificado de incidentes cercanos.

## Estructura del proyecto

```
app-hurtos/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/juliancgarzon/apphurtos/
│   │   │   │       ├── activities/
│   │   │   │       ├── adapters/
│   │   │   │       ├── models/
│   │   │   │       ├── utils/
│   │   │   └── res/
│   │   │       ├── layout/
│   │   │       ├── values/
│   │   │       ├── drawable/
├── backend/
│   ├── index.js
│   ├── routes/
│   ├── controllers/
│   ├── models/
│   ├── config/
│   └── package.json
├── google-services.json
├── build.gradle
├── README.md
```

## Historias de usuario

### Primera entrega:

- Configuración del proyecto en Android Studio.
- Implementación de la geolocalización.
- Creación de la interfaz para reportar incidentes.
- Desarrollo del backend para almacenar reportes.
- Estructura de la base de datos.

### Segunda entrega:

- Alertas basadas en geolocalización.
- Visualización de incidentes en un mapa interactivo.
- Filtrado avanzado de reportes.
- Integración de categorías de incidentes.

### Tercera entrega:

- Optimización del rendimiento.
- Pruebas de usabilidad.
- Notificaciones push.
- Refinamiento de la interfaz de usuario.
- Pruebas de seguridad.

## Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.

## Contribuciones

Las contribuciones son bienvenidas. Si tienes ideas o mejoras, abre un issue o envía un pull request.

## Contacto

Si tienes preguntas o comentarios sobre el proyecto, no dudes en contactarme:

- **Correo:** [juliangarzon@live.com](mailto\:juliangarzon@live.com)
- **GitHub:** [github.com/](https://github.com/tuusuario)juliancgarzon

