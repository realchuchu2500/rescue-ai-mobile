# RESCUE AI MOBILE

**CREADO POR JESÚS LORENZO**

## Descripción del Proyecto

RESCUE AI MOBILE es una aplicación Android completamente OFFLINE diseñada para asistir a equipos de búsqueda y rescate y voluntarios en la detección de posibles sobrevivientes atrapados bajo escombros. La aplicación utiliza únicamente el hardware del teléfono (cámara y micrófono) para analizar el entorno en tiempo real, fusionando datos de visión artificial y análisis acústico para generar un Índice de Evidencia de Vida (IEV). Su objetivo es ayudar a priorizar zonas de búsqueda sin reemplazar la evaluación humana.

## Características Principales

*   **Operación Offline**: No requiere conexión a internet, servicios en la nube, APIs externas, login o servidores remotos. Todo el procesamiento se realiza en el dispositivo.
*   **Visión Artificial**: Captura y analiza video en tiempo real para detectar personas, partes del cuerpo (cabeza, manos, brazos, piernas, pies), objetos relevantes (zapatos, cascos, mochilas, teléfonos, ropa visible) y extremidades parcialmente visibles. Muestra cuadros delimitadores y porcentaje de confianza.
*   **Análisis Acústico**: Realiza calibración automática del ruido ambiente y elimina ruido constante. Detecta sonidos compatibles con presencia humana como voz, gritos, golpes repetitivos, respiración, silbidos y tos, mostrando un nivel de confianza.
*   **Motor de Decisión**: Fusiona los resultados de visión y audio para generar un Índice de Evidencia de Vida (IEV), indicando niveles de evidencia (baja, media, zona prioritaria para inspección) sin confirmar la presencia humana.
*   **Interfaz de Usuario Optimizada**: Pantalla única con vista de cámara, indicador de análisis y un panel inferior que muestra la evidencia visual, acústica y el IEV. Diseñada para uso con una mano, alto contraste y botones grandes.
*   **Rendimiento y Eficiencia**: Prioriza la velocidad, confiabilidad, bajo consumo de batería y procesamiento en tiempo real. Optimizado para memoria, CPU y procesamiento multi-hilo para evitar bloqueos de interfaz.
*   **Arquitectura Limpia**: Implementa Clean Architecture para una alta modularidad, mantenibilidad y escalabilidad.

## Stack Tecnológico

*   **Lenguaje de Programación**: Kotlin
*   **Framework de UI**: Jetpack Compose
*   **Visión Artificial**: TensorFlow Lite, CameraX, OpenCV
*   **Análisis Acústico**: AudioRecord, librerías DSP nativas

## Requisitos del Sistema

*   Android 10 (API 29) o superior.
*   Android Studio (versión recomendada: Flamingo o superior).
*   Hardware del dispositivo: Cámara, Micrófono, CPU, GPU (opcional), NPU (opcional), Almacenamiento local.

## Estructura del Proyecto

El proyecto sigue una Clean Architecture con la siguiente estructura de directorios principal:

```
project_rescue_ai/
├── app/                      # Módulo principal de la aplicación
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/rescue_ai/mobile/
│   │   │   │   ├── ui/                 # Capa de Presentación (Jetpack Compose, ViewModels)
│   │   │   │   ├── domain/             # Capa de Dominio (Casos de Uso, Entidades, Repositorios)
│   │   │   │   │   ├── fusion/         # Lógica del Motor de Decisión
│   │   │   │   │   └── model/
│   │   │   │   ├── data/               # Capa de Datos (Implementaciones de Repositorios, Fuentes de Datos)
│   │   │   │   │   ├── camera/         # Implementación de CameraX
│   │   │   │   │   ├── vision/         # Lógica de Visión Artificial (TFLite, OpenCV)
│   │   │   │   │   ├── audio/          # Lógica de Audio (AudioRecord, DSP)
│   │   │   │   │   └── inference/      # Gestión de Modelos de Inferencia
│   │   │   │   ├── core/               # Clases base y utilidades compartidas
│   │   │   │   └── utils/              # Funciones de utilidad
│   │   │   ├── res/                    # Recursos de la aplicación (layouts, valores, drawables)
│   │   │   ├── assets/                 # Modelos TFLite y archivos de etiquetas
│   │   │   └── AndroidManifest.xml     # Manifiesto de la aplicación
│   │   └── build.gradle.kts            # Configuración de Gradle para el módulo 'app'
├── gradle/                   # Archivos de configuración de Gradle
├── .gitignore                # Archivo para ignorar archivos en Git
├── build.gradle.kts          # Configuración de Gradle a nivel de proyecto
├── settings.gradle.kts       # Configuración de los módulos del proyecto
└── README.md                 # Este archivo
```

## Dependencias

Las dependencias del proyecto se gestionan a través de Gradle y se encuentran en `app/build.gradle.kts`. Las principales dependencias incluyen:

*   **Kotlin Core KTX**: Extensiones de Kotlin para Android.
*   **Jetpack Compose**: Para la construcción de la interfaz de usuario.
*   **CameraX**: Para la integración de la cámara.
*   **TensorFlow Lite**: Para la inferencia de modelos de Machine Learning en el dispositivo.
*   **TensorFlow Lite GPU**: Soporte para aceleración por GPU en TFLite.
*   **TensorFlow Lite Support**: Librerías de soporte para TFLite.

## Instrucciones de Compilación

Para compilar y ejecutar el proyecto, siga los siguientes pasos:

1.  **Clonar el Repositorio (si aplica)**: Si el proyecto está en un repositorio Git, clónelo a su máquina local.
    ```bash
    git clone <URL_DEL_REPOSITORIO>
    cd project_rescue_ai
    ```
2.  **Abrir en Android Studio**: Abra el directorio `project_rescue_ai` en Android Studio.
3.  **Sincronizar Gradle**: Android Studio debería sincronizar automáticamente el proyecto con Gradle. Si no es así, haga clic en 
el botón "Sync Project with Gradle Files" en la barra de herramientas.
4.  **Descargar Modelos TFLite**: Asegúrese de que el modelo `ssd_mobilenet_v1.tflite` esté ubicado en la carpeta `app/src/main/assets/`. Este modelo no se incluye en el repositorio por su tamaño y debe ser descargado por separado. Puede encontrar modelos pre-entrenados en el [repositorio de TensorFlow Lite models](https://www.tensorflow.org/lite/models).
5.  **Ejecutar la Aplicación**: Conecte un dispositivo Android (con Android 10+ y depuración USB habilitada) o inicie un emulador. Haga clic en el botón "Run 'app'" (el icono de triángulo verde) en la barra de herramientas de Android Studio.

## Instrucciones para Generar APK

Para generar un archivo APK (Android Package Kit) instalable:

1.  En Android Studio, vaya a `Build > Build Bundles(s) / APK(s) > Build APK(s)`.
2.  Android Studio compilará el proyecto y generará un APK de depuración. Una notificación aparecerá en la esquina inferior derecha de Android Studio con un enlace "locate" para encontrar el APK.
3.  Para generar un APK firmado para producción, siga los pasos de `Build > Generate Signed Bundle / APK...` y siga las instrucciones del asistente.

## Manejo de Errores y Permisos

La aplicación solicitará los permisos de `CAMERA` y `RECORD_AUDIO` en tiempo de ejecución. Asegúrese de conceder estos permisos para que la aplicación funcione correctamente. Se ha implementado un manejo básico de errores para evitar cierres inesperados, y los errores se registrarán localmente para facilitar la depuración.

## Calidad del Código

El proyecto sigue los principios SOLID, promueve el código limpio y documentado, evita la duplicación y minimiza las dependencias innecesarias para asegurar una alta calidad y mantenibilidad.

## Contribuciones

Las contribuciones son bienvenidas. Por favor, siga las guías de estilo de código de Kotlin y las prácticas de Clean Architecture.

## Licencia

Este proyecto se distribuye bajo la licencia MIT. Consulte el archivo `LICENSE` para más detalles. (Nota: el archivo LICENSE no se ha generado en este ejemplo, pero debería incluirse en un proyecto real).
