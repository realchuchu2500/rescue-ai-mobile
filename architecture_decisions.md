# Decisiones de Arquitectura y Stack Tecnológico para RESCUE AI MOBILE

## 1. Stack Tecnológico

Considerando los requisitos de **aplicación completamente OFFLINE**, **procesamiento en tiempo real**, **bajo consumo de batería**, **velocidad** y **confiabilidad**, se ha seleccionado el siguiente stack tecnológico:

*   **Lenguaje de Programación**: Kotlin
    *   **Justificación**: Kotlin es el lenguaje preferido para el desarrollo nativo de Android, ofreciendo concisión, seguridad y un excelente rendimiento. Permite un control más granular sobre el hardware y el rendimiento en comparación con frameworks multiplataforma como Flutter, lo cual es crucial para una aplicación de alta prioridad y rendimiento como RESCUE AI MOBILE.

*   **Framework de UI**: Android Nativo (Jetpack Compose)
    *   **Justificación**: Jetpack Compose es el toolkit moderno de Android para construir interfaces de usuario nativas. Ofrece un rendimiento superior y una mejor integración con las APIs del sistema en comparación con las vistas XML tradicionales, lo que es vital para una interfaz de usuario fluida y reactiva, especialmente bajo carga de procesamiento intensivo.

*   **Visión Artificial**: TensorFlow Lite con CameraX y OpenCV
    *   **Justificación**: 
        *   **TensorFlow Lite**: Es la solución estándar de la industria para la inferencia de modelos de Machine Learning en dispositivos móviles. Proporciona modelos pre-entrenados optimizados para la ejecución en CPU, GPU y NPU (si está disponible), cumpliendo con el requisito de procesamiento local y en tiempo real. Permite la detección de objetos (personas, partes del cuerpo, objetos relevantes) con alta eficiencia.
        *   **CameraX**: Simplifica el acceso a la cámara del dispositivo, proporcionando una API consistente y optimizada para la captura de video en tiempo real, esencial para el Módulo de Visión Artificial.
        *   **OpenCV**: Se utilizará para tareas de pre-procesamiento y post-procesamiento de imágenes si es necesario, así como para algoritmos de visión artificial complementarios que no estén cubiertos por TensorFlow Lite o para optimizaciones específicas.

*   **Análisis Acústico**: AudioRecord con FFmpeg (opcional) y librerías DSP nativas
    *   **Justificación**: 
        *   **AudioRecord**: Proporciona acceso de bajo nivel al hardware del micrófono, permitiendo la captura de audio en tiempo real con control preciso sobre los parámetros de grabación. Esto es fundamental para la calibración de ruido ambiente y la detección de sonidos específicos.
        *   **Librerías DSP nativas**: Se implementarán algoritmos de procesamiento de señal digital (DSP) directamente en Kotlin/Java o a través de NDK (C/C++) para la eliminación de ruido constante y la detección de patrones de sonido (voz, gritos, golpes, respiración, silbidos, tos). Esto asegura el máximo rendimiento y el mínimo consumo de batería.
        *   **FFmpeg (opcional)**: Podría considerarse para tareas muy específicas de decodificación o procesamiento de audio si las librerías nativas no son suficientes, pero se priorizará el desarrollo nativo para mantener la huella de la aplicación lo más pequeña y eficiente posible.

## 2. Arquitectura del Proyecto

Se implementará una **Clean Architecture** para asegurar la modularidad, la mantenibilidad, la escalabilidad y la facilidad de prueba del proyecto. La estructura se dividirá en las siguientes capas y módulos:

*   **Capa de Presentación (UI)**:
    *   `ui`: Contiene la interfaz de usuario (Jetpack Compose), ViewModels y lógica de presentación. Será responsable de mostrar la vista de la cámara, los indicadores de análisis y el Índice de Evidencia de Vida (IEV).

*   **Capa de Dominio (Business Logic)**:
    *   `domain`: Contiene las entidades, casos de uso (use cases) e interfaces de repositorio. Es el corazón de la aplicación y encapsula las reglas de negocio.
    *   `fusion`: Módulo específico dentro del dominio para el Motor de Decisión, encargado de fusionar los resultados de visión y audio para generar el IEV.

*   **Capa de Datos (Data)**:
    *   `data`: Contiene las implementaciones de los repositorios definidos en el dominio, así como las fuentes de datos (cámara, micrófono, modelos ML).
    *   `camera`: Implementación de la fuente de datos de la cámara utilizando CameraX.
    *   `vision`: Implementación de la lógica de inferencia de visión artificial (TensorFlow Lite, OpenCV).
    *   `audio`: Implementación de la lógica de captura y procesamiento de audio (AudioRecord, DSP).
    *   `inference`: Módulo general para la gestión de modelos de inferencia de ML (TFLite).

*   **Módulos Transversales y de Utilidad**:
    *   `core`: Contiene clases base, extensiones, constantes y utilidades generales que son compartidas por todas las capas.
    *   `utils`: Funciones de utilidad diversas, como formateadores, helpers, etc.

## 3. Estructura de Directorios (Ejemplo)

```
project_rescue_ai/
├── app/
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
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle.kts
│   └── build.gradle.kts
├── gradle/
├── .gitignore
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

Esta estructura proporciona una base sólida para el desarrollo, permitiendo que cada equipo (CTO, Arquitecto, Ingenieros) trabaje en sus respectivas áreas con una clara separación de responsabilidades.
