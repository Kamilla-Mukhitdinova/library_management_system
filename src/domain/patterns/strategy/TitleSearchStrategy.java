package domain.patterns.strategy;

import domain.model.Book;
import java.util.List;
import java.util.stream.Collectors;

public class TitleSearchStrategy implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String criteria) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(criteria.toLowerCase()))
                .collect(Collectors.toList());
    }
} 