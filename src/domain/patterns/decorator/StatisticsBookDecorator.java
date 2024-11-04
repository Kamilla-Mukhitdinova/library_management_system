package domain.patterns.decorator;

import domain.model.Book;
import java.time.LocalDateTime;
import java.time.Duration;

public class StatisticsBookDecorator extends BookDecorator {
    private int borrowCount;
    private LocalDateTime lastBorrowed;
    private LocalDateTime lastReturned;

    public StatisticsBookDecorator(Book book) {
        super(book);
        this.borrowCount = 0;
    }

    @Override
    public void setBorrowed(boolean borrowed) {
        if (borrowed && !book.isBorrowed()) {
            borrowCount++;
            lastBorrowed = LocalDateTime.now();
        } else if (!borrowed && book.isBorrowed()) {
            lastReturned = LocalDateTime.now();
        }
        super.setBorrowed(borrowed);
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public LocalDateTime getLastBorrowed() {
        return lastBorrowed;
    }

    public LocalDateTime getLastReturned() {
        return lastReturned;
    }

    public Duration getLastBorrowDuration() {
        if (lastBorrowed == null || lastReturned == null) {
            return Duration.ZERO;
        }
        return Duration.between(lastBorrowed, lastReturned);
    }
} 