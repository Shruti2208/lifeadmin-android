# LifeAdmin

LifeAdmin is a simple Android task management application
built with Kotlin and Jetpack Compose.

The project demonstrates MVVM and Clean Architecture
principles, with a focus on separation of concerns,
reactive state management, and maintainable code.

## Features

- Create tasks with titles and descriptions
- View all tasks
- Mark tasks as completed or incomplete
- Delete tasks
- Validate task titles

## Tech Stack

- Kotlin
- Jetpack Compose
- MVVM
- Clean Architecture
- Kotlin Coroutines
- StateFlow
- Repository Pattern

## Architecture

The application follows three layers:

### Presentation
Contains the Compose UI and ViewModel.
Handles UI state and user interactions.

### Domain
Contains business models, repository interfaces,
and use cases.

### Data
Contains the in-memory repository implementation.

## Data Storage

Tasks are currently stored in memory.

Data is not persisted when the application process ends.

## Future Improvements

- Room database persistence
- Task categories and due dates
- Unit tests
- Improved UI/UX