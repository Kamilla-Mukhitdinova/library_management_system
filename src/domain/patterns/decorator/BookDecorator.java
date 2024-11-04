package domain.patterns.decorator;

import domain.model.Book;
public abstract class BookDecorator implements BookInterface {
    protected final Book book;
    
    public BookDecorator(Book book) {
        this.book = book;
    }

    @Override
    public String getTitle() {
        return book.getTitle();
    }

    @Override
    public String getAuthor() {
        return book.getAuthor();
    }

    @Override
    public String getIsbn() {
        return book.getIsbn();
    }

    @Override
    public int getPageCount() {
        return book.getPageCount();
    }

    @Override
    public boolean isBorrowed() {
        return book.isBorrowed();
    }

    @Override
    public void setBorrowed(boolean borrowed) {
        book.setBorrowed(borrowed);
    }

    public Book getBook() {
        return book;
    }
} 