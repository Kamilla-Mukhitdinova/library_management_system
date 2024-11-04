# Library Management System Architecture

## System Overview
The Library Management System is built using a layered architecture that separates concerns into distinct layers:

## Architectural Layers

### 1. Application Layer (`src/application/`)
- Contains the system's entry points and facades
- Manages high-level flow control
- Implements the Facade pattern through `LibraryFacade`

### 2. Domain Layer (`src/domain/`)
- Contains core business logic
- Implements domain models and business rules
- Houses design pattern implementations
- Key components:
  - Book model
  - Pattern implementations (Command, Observer, Strategy, etc.)

### 3. Infrastructure Layer (`src/infrastructure/`)
- Manages system resources and persistence
- Implements the Library singleton
- Handles data storage and retrieval

## Key Components

### LibraryFacade
- Provides unified interface for client operations
- Manages communication between layers
- Simplifies client interaction with the system

### Library Singleton
- Central management of book collection
- Handles command history
- Manages observers and notifications

### Book Entity
- Core domain model
- Uses Builder pattern for construction
- Implements BookInterface for consistency

## Design Decisions
1. Layered Architecture chosen for:
   - Clear separation of concerns
   - Maintainable codebase
   - Testable components
   - Easy to extend functionality

2. Pattern Usage:
   - Facade for simplified access
   - Command for operation history
   - Observer for status notifications
   - Strategy for flexible search
