package domain.patterns.observer;

import domain.model.Book;

public interface Observer {
    void update(Book book);
} 