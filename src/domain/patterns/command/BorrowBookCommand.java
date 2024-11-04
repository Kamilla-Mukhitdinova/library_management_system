package domain.patterns.command;

import domain.model.Book;
import infrastructure.persistence.Library;

public class BorrowBookCommand implements Command {
    private final Book book;
    private final Library library;

    public BorrowBookCommand(Book book, Library library) {
        this.book = book;
        this.library = library;
    }

    @Override
    public void execute() {
        if (!book.isBorrowed()) {
            book.setBorrowed(true);
            library.notifyObservers(book);
        } else {
            throw new IllegalStateException("Book is already borrowed");
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