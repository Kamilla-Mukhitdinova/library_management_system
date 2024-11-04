package application.facade;


import domain.model.Book;
import domain.patterns.strategy.SearchStrategy;
import domain.patterns.strategy.TitleSearchStrategy;
import domain.patterns.strategy.IsbnSearchStrategy;
import domain.patterns.strategy.AuthorSearchStrategy;
import domain.patterns.observer.BookStatusObserver;
import domain.patterns.command.Command;
import domain.patterns.command.BorrowBookCommand;
import domain.patterns.command.ReturnBookCommand;
import infrastructure.persistence.Library;


import java.util.List;

public class LibraryFacade {
    private final Library library;
    private final SearchStrategy titleSearchStrategy;
    private final SearchStrategy isbnSearchStrategy;
    private final SearchStrategy authorSearchStrategy;

    public LibraryFacade() {
        this.library = Library.getInstance();
        this.titleSearchStrategy = new TitleSearchStrategy();
        this.isbnSearchStrategy = new IsbnSearchStrategy();
        this.authorSearchStrategy = new AuthorSearchStrategy();
        this.library.addObserver(new BookStatusObserver());
    }

    public void addBook(String title, String author, String isbn, int pageCount) {
        Book book = new Book.Builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .pageCount(pageCount)
                .build();
        library.addBook(book);
    }

    public void borrowBook(String isbn) {
        Book book = library.findBookByIsbn(isbn);
        Command borrowCommand = new BorrowBookCommand(book, library);
        library.executeCommand(borrowCommand);
    }

    public void returnBook(String isbn) {
        Book book = library.findBookByIsbn(isbn);
        Command returnCommand = new ReturnBookCommand(book, library);
        library.executeCommand(returnCommand);
    }

    public void undoLastOperation() {
        library.undoLastCommand();
    }

    public List<Book> searchByTitle(String title) {
        return library.searchBooks(titleSearchStrategy, title);
    }

    public List<Book> searchByIsbn(String isbn) {
        return library.searchBooks(isbnSearchStrategy, isbn);
    }

    public List<Book> getAllBooks() {
        return library.getAllBooks();
    }

    public List<Book> searchByAuthor(String author) {
        return library.searchBooks(authorSearchStrategy, author);
    }

    public String getBookStatistics(String isbn) {
        return library.getBookStatistics(isbn);
    }
} 