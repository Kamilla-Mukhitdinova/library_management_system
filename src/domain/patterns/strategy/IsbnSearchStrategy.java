package domain.patterns.strategy;

import domain.model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class IsbnSearchStrategy implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String criteria) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(criteria))
                .collect(Collectors.toList());
    }
} 