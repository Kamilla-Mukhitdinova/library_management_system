import application.facade.LibraryFacade;
import domain.model.Book;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final LibraryFacade facade = new LibraryFacade();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeLibrary();

        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> borrowBook();
                    case 3 -> returnBook();
                    case 4 -> searchByTitle();
                    case 5 -> searchByIsbn();
                    case 6 -> searchByAuthor();
                    case 7 -> showAllBooks();
                    case 8 -> undoLastOperation();
                    case 0 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Library Management System ===");
        System.out.println("1. Add new book");
        System.out.println("2. Borrow book");
        System.out.println("3. Return book");
        System.out.println("4. Search by title");
        System.out.println("5. Search by ISBN");
        System.out.println("6. Search by author");
        System.out.println("7. Show all books");
        System.out.println("8. Undo last operation");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    private static void initializeLibrary() {
        facade.addBook("Clean Code", "Robert Martin", "978-0132350884", 464);
        facade.addBook("Design Patterns", "Gang of Four", "978-0201633610", 395);
        facade.addBook("Effective Java", "Joshua Bloch", "978-0134685991", 412);
    }

    private static void addBook() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter page count: ");
        int pageCount = scanner.nextInt();

        facade.addBook(title, author, isbn, pageCount);
        System.out.println("Book added successfully!");
    }

    private static void borrowBook() {
        System.out.print("Enter ISBN of book to borrow: ");
        String isbn = scanner.nextLine();
        facade.borrowBook(isbn);
        System.out.println("Book borrowed successfully!");
    }

    private static void returnBook() {
        System.out.print("Enter ISBN of book to return: ");
        String isbn = scanner.nextLine();
        facade.returnBook(isbn);
        System.out.println("Book returned successfully!");
    }

    private static void searchByTitle() {
        System.out.print("Enter title to search: ");
        String title = scanner.nextLine();
        List<Book> books = facade.searchByTitle(title);
        printBooks(books);
    }

    private static void searchByIsbn() {
        System.out.print("Enter ISBN to search: ");
        String isbn = scanner.nextLine();
        List<Book> books = facade.searchByIsbn(isbn);
        printBooks(books);
    }

    private static void searchByAuthor() {
        System.out.print("Enter author name to search: ");
        String author = scanner.nextLine();
        List<Book> books = facade.searchByAuthor(author);
        printBooks(books);
    }

    private static void showAllBooks() {
        List<Book> books = facade.getAllBooks();
        printBooks(books);
    }

    private static void undoLastOperation() {
        facade.undoLastOperation();
        System.out.println("Last operation undone!");
    }

    private static void printBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found!");
            return;
        }

        System.out.println("\nFound books:");
        for (Book book : books) {
            System.out.printf("Title: %s\nAuthor: %s\nISBN: %s\nPages: %d\nStatus: %s\n\n",
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    book.getPageCount(),
                    book.isBorrowed() ? "Borrowed" : "Available");
        }
    }
}