package domain.patterns.decorator;

public interface BookInterface {
    String getTitle();
    String getAuthor();
    String getIsbn();
    int getPageCount();
    boolean isBorrowed();
    void setBorrowed(boolean borrowed);
} 