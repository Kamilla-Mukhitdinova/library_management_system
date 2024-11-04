package domain.patterns.observer;

import domain.model.Book;

public class BookStatusObserver implements Observer {
    @Override
    public void update(Book book) {
        String status = book.isBorrowed() ? "borrowed" : "returned";
        System.out.println("Book status changed: " + book.getTitle() + " has been " + status);
    }
} 