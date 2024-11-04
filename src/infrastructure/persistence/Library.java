package infrastructure.persistence;

import domain.model.Book;
import domain.patterns.command.Command;
import domain.patterns.decorator.StatisticsBookDecorator;
import domain.patterns.observer.Observer;
import domain.patterns.strategy.SearchStrategy;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.time.Duration;
import java.util.stream.Collectors;

public class Library {
    private static Library instance;
    private final List<StatisticsBookDecorator> books;
    private final List<Observer> observers;
    private final Stack<Command> commandHistory;

    private Library() {
        this.books = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.commandHistory = new Stack<>();
    }

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    public void addBook(Book book) {
        books.add(new StatisticsBookDecorator(book));
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Book book) {
        observers.forEach(observer -> observer.update(book));
    }

    public void executeCommand(Command command) {
        command.execute();
        commandHistory.push(command);
    }

    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command command = commandHistory.pop();
            command.undo();
        } else {
            throw new IllegalStateException("No commands to undo");
        }
    }

    public List<Book> searchBooks(SearchStrategy strategy, String criteria) {
        return strategy.search(books.stream()
                .map(decorator -> decorator.getBook())
                .collect(Collectors.toList()), criteria);
    }

    public List<Book> getAllBooks() {
        return books.stream()
                .map(StatisticsBookDecorator::getBook)
                .collect(Collectors.toList());
    }

    public Book findBookByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .map(decorator -> decorator.getBook())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }

    public String getBookStatistics(String isbn) {
        StatisticsBookDecorator decorator = books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        return String.format(
            "Book: %s\nBorrow count: %d\nLast borrowed: %s\nLast returned: %s\nLast borrow duration: %s",
            decorator.getTitle(),
            decorator.getBorrowCount(),
            decorator.getLastBorrowed(),
            decorator.getLastReturned(),
            formatDuration(decorator.getLastBorrowDuration())
        );
    }

    private String formatDuration(Duration duration) {
        if (duration.equals(Duration.ZERO)) {
            return "N/A";
        }
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        return String.format("%d days, %d hours, %d minutes", days, hours, minutes);
    }
} 