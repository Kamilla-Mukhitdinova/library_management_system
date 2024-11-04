package domain.model;

import domain.patterns.decorator.BookInterface;

public class Book implements BookInterface {
    private final String title;
    private final String author;
    private final String isbn;
    private final int pageCount;
    private boolean isBorrowed;

    private Book(Builder builder) {
        this.title = builder.title;
        this.author = builder.author;
        this.isbn = builder.isbn;
        this.pageCount = builder.pageCount;
        this.isBorrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPageCount() {
        return pageCount;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public static class Builder {
        private String title;
        private String author;
        private String isbn;
        private int pageCount;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder pageCount(int pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public Book build() {
            if (title == null || author == null || isbn == null || pageCount <= 0) {
                throw new IllegalStateException("All fields are required and pageCount must be positive");
            }
            return new Book(this);
        }
    }
} 