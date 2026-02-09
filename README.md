# Sumativa 2 - Semana 5 (Integrando Kotlin )


## Requisitos cubiertos (resumen)
- SplashScreen
- Login (correo + contrasena) con validacion contra usuarios registrados
- Registro de usuario con:
  - Inputs (text fields)
  - Boton
  - Links (navegacion)
  - ComboBox (Region)
  - Check list (ayudas visuales)
  - Radio buttons (modo de lectura)
  - Tabla y Grilla para mostrar usuarios registrados
  - Maximo 5 usuarios almacenados en memoria
- Recuperar contrasena (simulado) con ComboBox y Radio buttons
- Home (grilla de acciones rapidas) + verificacion simple de conectividad

## Como ejecutar
1. Abrir la carpeta del proyecto en Android Studio.
2. Sincronizar Gradle.
3. Ejecutar en emulador o dispositivo (minSdk 24).

## Notas
- El almacen de usuarios es en memoria (`UserStore`), no persiste al cerrar la app.
- Las integraciones con redes sociales y recuperacion son simuladas.


## Kotlin 
- `UserStore` mantiene un `Array<User?>` de tamaño 5 como fuente de verdad (usuarios + contraseñas).
- La UI usa una lista observable para refrescar Tabla/Grilla.
- Validaciones simples en `Validators.kt`.
