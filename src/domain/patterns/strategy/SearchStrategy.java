package domain.patterns.strategy;

import domain.model.Book;

import java.util.List;

public interface SearchStrategy {
    List<Book> search(List<Book> books, String criteria);
} 