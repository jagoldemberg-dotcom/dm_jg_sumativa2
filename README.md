# Sumativa 3 - Semana 8
**Preparación de una aplicación móvil Android/Kotlin para su distribución**

Proyecto base: `dm_jg_sumativa2-main` (Compose + Navigation).  
Objetivo: integrar **FrontEnd + BackEnd (Firebase)**, agregar **Escribir / Hablar / BuscarDispositivo (geolocalización + Fragment + ViewGroup)**, **ContentProvider**, **Widget**, **tests** y preparar **APK firmado** para publicar en una plataforma gratuita.

## Funcionalidades implementadas
- **Login / Registro / Recuperar contraseña** (Firebase Auth).
- **Home Menú** con accesibilidad (botones grandes).
- **Escribir**:
  - Ingreso de texto grande.
  - Botón **Hablar** (TextToSpeech).
  - Historial local (Room) que además se expone con **ContentProvider**.
  - Guarda frase en Firestore (si Firebase está configurado).
- **Hablar**:
  - Dictado con `RecognizerIntent`.
  - Guarda frase local (Room) y remota (Firestore).
- **Buscar Dispositivo**:
  - Se abre en una **Activity con ViewGroup + Fragment** (`DeviceSearchActivity` + `DeviceSearchFragment`).
  - Geolocalización (FusedLocationProviderClient) para registrar y filtrar dispositivos cercanos (filtro local por distancia).
- **Widget**:
  - `LastPhraseWidget` muestra la última frase guardada.

## Requisitos técnicos (dónde mirar)
- Firebase: `data/firebase/*`
- Room (persistencia local): `data/local/*`
- ContentProvider: `provider/PhraseContentProvider.kt`
- Widget: `widget/LastPhraseWidget.kt` + `res/xml/last_phrase_widget_info.xml`
- Fragment + ViewGroup: `device/*` + `res/layout/activity_device_search.xml`
- Views/Screens: `ui/screens/*`
- Navegación: `navigation/NavGraph.kt`
- Tests: `app/src/test/java/.../ValidatorsTest.kt`

## Configuración Firebase (obligatoria)
1. Crear proyecto en Firebase.
2. Agregar app Android con package: **com.example.semana1pv**
3. Descargar `google-services.json` y reemplazar el archivo:
   - `app/google-services.json`
4. En Firebase habilitar:
   - Authentication -> **Email/Password**
   - Firestore Database (modo test o reglas controladas)

> Nota: este repo trae un `google-services.json` de **placeholder** solo para que el proyecto compile.
> Debes reemplazarlo por el real antes de probar Auth/Firestore.

## Generar APK firmado
Android Studio:
1. **Build > Generate Signed Bundle / APK...**
2. Elegir **APK**
3. Crear o seleccionar **Keystore**
4. Seleccionar `release`
5. Generar y ubicar el APK en: `app/release/app-release.apk`

## Publicar APK (simulando Play Store)
Opciones gratuitas típicas:
- **GitHub Releases** (recomendado): subir `app-release.apk` y compartir el link.
- **Google Drive / OneDrive**: subir archivo y compartir con permiso de descarga.

## Permisos relevantes
En `AndroidManifest.xml`:
- INTERNET
- ACCESS_FINE_LOCATION / ACCESS_COARSE_LOCATION
- RECORD_AUDIO
- ACCESS_NETWORK_STATE

