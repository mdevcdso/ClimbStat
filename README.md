# ClimbStat

Application Android (+ API) de suivi de performances en escalade, inspirée d'Arkose+.
L'utilisateur peut rechercher une salle, consulter les blocs, enregistrer ses tops / flashs et visualiser ses statistiques par salle et par difficulté.

## Fonctionnalités
- Création de compte et connexion (JWT)
- Recherche et consultation des salles d'escalade
- Consultation des blocs d'une salle (photo, difficulté, types, description)
- Enregistrement d'un top / flash avec nombre d'essais + commentaire
- Profil utilisateur : nombre de sessions, tops par niveau (tier), salles visitées
- Écran statistiques par salle : total de tops, taux de flash, répartition par tier

## Architecture & choix techniques

### Android (`ClimbStat/`)
- **Kotlin + Jetpack Compose** (Material 3)
- **Architecture MVVM** en couches : `data` (DataSource / DTO / Repository impl), `domain` (modèles, interfaces repository, use cases, UiState), `presentation` (ViewModels, navigation, écrans Compose)
- **StateFlow** + `sealed class UiState` (`Loading` / `Success` / `Error` / `Empty`) pour piloter chaque écran
- **Navigation Compose** avec bottom navigation (Salles, Topos, Profil)
- **Injection manuelle** centralisée dans `MainActivity.injectDependencies()` qui construit un `AppViewModel` contenant tous les ViewModels de l'app
- **Retrofit + Gson** pour l'accès API, **Coil** pour le chargement d'images
- **TokenManagerUtils** (SharedPreferences) pour le JWT, lu dynamiquement par les repositories

### API (`ClimbStatApi/`)
- **NestJS 11** (modules : auth, users, gyms, boulders, topos)
- **MongoDB + Mongoose** pour la persistance
- **JWT + bcrypt** pour l'authentification, guard `JwtAuthGuard` sur les routes protégées
- **class-validator** pour la validation des DTO
- **Swagger** exposé pour documenter les endpoints
- **Multer + Cloudinary** pour l'upload d'images de blocs

## Prérequis
- Docker + Docker Compose
- Android Studio avec un SDK Android 34+
- Node.js 20+ (uniquement pour lancer l'API en dehors de Docker)

## Lancement de l'API

```bash
cd ClimbStatApi
cp .env.example .env   # si le fichier existe, sinon voir variables ci-dessous
docker compose up -d --build
```

L'API écoute sur http://localhost:3001 (redirigé vers le port 3000 du container). MongoDB est exposé sur 27017.

Variables d'environnement attendues (définies dans docker-compose.yml) :

- MONGO_URI
- JWT_SECRET
- JWT_EXPIRES
- APP_URL
- CLOUDINARY_* (pour l'upload d'images)

Documentation Swagger : http://localhost:3001/api.

## Lancement de l'application Android

- Démarrer l'API comme ci-dessus.
- Ouvrir le dossier ClimbStat/ dans Android Studio.
- Vérifier dans app/src/main/java/com/example/climbstat/Constants.kt :
BASE_URL = "http://10.0.2.2:3001" (émulateur Android → host)
IMAGE_BASE_URL = "10.0.2.2"
- Lancer l'app sur un émulateur Android. Créer un compte via l'écran d'inscription, puis se connecter.
- Sur un device physique, remplacer 10.0.2.2 par l'IP locale de la machine qui fait tourner l'API.

## Tests unitaires
### Android

```bash
cd ClimbStat
./gradlew :app:testDebugUnitTest
```
Couvre en particulier ProfileViewModelTest (chargement du profil, agrégation des tops par tier, sessions distinctes, salles visitées, déconnexion).

### API

```bash
cd ClimbStatApi
npm install
npm test
```

## Structure du dépôt

- **ClimbStat/**         # App Android (Kotlin / Compose / MVVM)
- **ClimbStatApi/**      # API NestJS + MongoDB (Docker Compose)
- **README.md**

## Figma
https://www.figma.com/board/NWyZoH84kMvvxnr4HscUYz/base-de-donn%C3%A9e--Community-?node-id=0-1&t=xCqxCHvOKUMiuLt1-1
