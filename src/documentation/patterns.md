# Design Patterns in Library Management System

## Overview
This document describes the design patterns implemented in the Library Management System, their purposes, and implementations.

## 1. Creational Patterns

### Singleton Pattern
**Purpose**: Ensures a single instance of the library throughout the application.

**Implementation**: `Library` class

public class Library {
    private static Library instance;
    
    private Library() { }
    
    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }
}

### Builder Pattern
**Purpose**: Provides flexible book object construction with validation.

**Implementation**: `Book.Builder` class

public static class Builder {
    private String title;
    private String author;
    private String isbn;
    private int pageCount;

    public Builder title(String title) {
        this.title = title;
        return this;
    }

    public Book build() {
        if (title == null || author == null || isbn == null || pageCount <= 0) {
            throw new IllegalStateException("All fields are required");
        }
        return new Book(this);
    }
}

## 2. Structural Patterns

### Decorator Pattern
**Purpose**: Adds statistics tracking functionality to books without modifying their core behavior.

**Implementation**: `BookDecorator` and `StatisticsBookDecorator`

public class StatisticsBookDecorator extends BookDecorator {
    private int borrowCount;
    private LocalDateTime lastBorrowed;

    @Override
    public void setBorrowed(boolean borrowed) {
        if (borrowed && !book.isBorrowed()) {
            borrowCount++;
            lastBorrowed = LocalDateTime.now();
        }
        super.setBorrowed(borrowed);
    }
}

### Facade Pattern
**Purpose**: Simplifies client interaction with the library system.

**Implementation**: `LibraryFacade`

public class LibraryFacade {
    private final Library library;
    private final SearchStrategy titleSearchStrategy;

    public void borrowBook(String isbn) {
        Book book = library.findBookByIsbn(isbn);
        Command borrowCommand = new BorrowBookCommand(book, library);
        library.executeCommand(borrowCommand);
    }
}

## 3. Behavioral Patterns

### Command Pattern
**Purpose**: Encapsulates book operations and provides undo functionality.

**Implementation**: `BorrowBookCommand` and `ReturnBookCommand`

public class BorrowBookCommand implements Command {
    private final Book book;
    private final Library library;

    @Override
    public void execute() {
        if (!book.isBorrowed()) {
            book.setBorrowed(true);
            library.notifyObservers(book);
        }
    }

    @Override
    public void undo() {
        if (book.isBorrowed()) {
            book.setBorrowed(false);
            library.notifyObservers(book);
        }
    }
}

### Observer Pattern
**Purpose**: Monitors and notifies about changes in book status.

**Implementation**: `BookStatusObserver`

public class BookStatusObserver implements Observer {
    @Override
    public void update(Book book) {
        String status = book.isBorrowed() ? "borrowed" : "returned";
        System.out.println("Book status changed: " + book.getTitle() + 
                         " has been " + status);
    }
}

### Strategy Pattern
**Purpose**: Provides flexible book search algorithms.

**Implementation**: `SearchStrategy` interface and its implementations

public interface SearchStrategy {
    List<Book> search(List<Book> books, String criteria);
}

public class TitleSearchStrategy implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String criteria) {
        return books.stream()
            .filter(book -> book.getTitle()
                .toLowerCase()
                .contains(criteria.toLowerCase()))
            .collect(Collectors.toList());
    }
}

## Pattern Interactions

1. **Command + Observer**
   - Commands notify observers when executing operations
   - Enables status tracking and notifications

2. **Decorator + Observer**
   - Decorated books participate in the observer system
   - Statistics tracking integrates with status changes

3. **Facade + Command**
   - Facade creates and executes commands
   - Simplifies client interaction with the command pattern

4. **Singleton + Observer**
   - Library singleton manages observer notifications
   - Centralizes observer management

## Benefits

1. **Maintainability**
   - Clear separation of concerns
   - Encapsulated behavior changes
   - Modular design

2. **Flexibility**
   - Easy to add new search strategies
   - Simple to extend book functionality
   - Pluggable notification system

3. **Reliability**
   - Operation history tracking
   - Undo capability
   - Status monitoring

## Usage Examples

### Creating a Book

Book book = new Book.Builder()
    .title("Clean Code")
    .author("Robert Martin")
    .isbn("978-0132350884")
    .pageCount(464)
    .build();

### Searching Books

List<Book> books = facade.searchByTitle("Clean Code");

### Borrowing a Book

facade.borrowBook("978-0132350884");

### Undoing Operation

facade.undoLastOperation();