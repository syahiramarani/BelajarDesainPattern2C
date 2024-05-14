import java.time.LocalDate;
import java.util.*;

// Repositories
class BookRepository implements BookRepo {
    private Map<Integer, Book> books = new HashMap<>();
    private static int counter = 0; // Deklarasi variabel statis di level kelas

    @Override
    public Book findByAuthorIdAndBookName(int authorId, String bookName) {
        for (Book book : books.values()) {
            if (book.getAuthorId() == authorId && book.getName().equals(bookName)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public void save(Book book) {
        int id = generateUniqueId();
        book.setId(id);
        books.put(id, book);
    }

    @Override
    public List<Book> findByBookIds(List<Integer> bookIds) {
        List<Book> foundBooks = new ArrayList<>();
        for (Integer bookId : bookIds) {
            Book book = books.get(bookId);
            if (book != null) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    private int generateUniqueId() {
        return ++counter; // Menggunakan variabel statis counter
    }
}

class AuthorRepository implements AuthorRepo {
    private Map<Integer, Author> authors = new HashMap<>();

    @Override
    public boolean checkAuthorId(int authorId) {
        return authors.containsKey(authorId);
    }

    @Override
    public Author save(Author author) {
        authors.put(author.getAuthorId(), author);
        return author;
    }
}

// Interfaces
interface BookRepo {
    Book findByAuthorIdAndBookName(int authorId, String bookName);

    void save(Book book);

    List<Book> findByBookIds(List<Integer> bookIds);
}

interface AuthorRepo {
    boolean checkAuthorId(int authorId);

    Author save(Author author);
}

// Entities
class Book {
    private int id;
    private int authorId;
    private String name;
    private String publisherName;
    private LocalDate released;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", name='" + name + '\'' +
                ", publisherName='" + publisherName + '\'' +
                ", released=" + released +
                '}';
    }
}

class Author {
    private int authorId;
    private String name;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

// Services
class BookService {
    private final BookRepo bookRepo;
    private final AuthorService authorService;

    public BookService(BookRepo bookRepo, AuthorService authorService) {
        this.bookRepo = bookRepo;
        this.authorService = authorService;
    }

    public void saveBook(int authorId, String bookName, String publisher) throws Exception {
        validateBook(authorId, bookName);
        authorService.saveIfNotExist(authorId);
        String publisherName = getPublisherName(publisher);

        Book book = new Book();
        book.setAuthorId(authorId);
        book.setName(bookName);
        book.setPublisherName(publisherName);
        bookRepo.save(book);
    }

    private String getPublisherName(String publisher) {
        return publisher != null ? publisher : "Anonym";
    }

    private void validateBook(int authorId, String bookName) throws Exception {
        if (bookName == null)
            throw new Exception("Book Name is null");
        Book bookByAuthorIdAndBookName = bookRepo.findByAuthorIdAndBookName(authorId, bookName);
        if (bookByAuthorIdAndBookName != null) {
            throw new Exception("Duplicate Book");
        }
    }

    public Map<Integer, List<Book>> releaseBooksByAuthor(List<Integer> bookIds) {
        List<Book> books = updateReleaseBooks(bookIds);
        return groupBooksByAuthor(books);
    }

    private Map<Integer, List<Book>> groupBooksByAuthor(List<Book> books) {
        Map<Integer, List<Book>> booksByAuthor = new HashMap<>();
        for (Book book : books) {
            int authorId = book.getAuthorId();
            List<Book> bookList = booksByAuthor.getOrDefault(authorId, new ArrayList<>());
            bookList.add(book);
            booksByAuthor.put(authorId, bookList);
        }
        return booksByAuthor;
    }

    private List<Book> updateReleaseBooks(List<Integer> bookIds) {
        List<Book> books = bookRepo.findByBookIds(bookIds);
        for (Book book : books) {
            book.setReleased(LocalDate.now());
        }
        return books;
    }
}

class AuthorService {
    private final AuthorRepo authorRepo;

    public AuthorService(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    public void saveIfNotExist(int authorId) {
        boolean existedAuthor = authorRepo.checkAuthorId(authorId);
        if (!existedAuthor) {
            Author author = new Author();
            author.setName("unknown");
            author.setAuthorId(authorId);
            authorRepo.save(author);
        }
    }
}

// Test
class App {
    public static void main(String[] args) throws Exception {
        BookRepo bookRepo = new BookRepository();
        AuthorRepo authorRepo = new AuthorRepository();
        AuthorService authorService = new AuthorService(authorRepo);
        BookService bookService = new BookService(bookRepo, authorService);

        bookService.saveBook(1, "Book 1", "Publisher 1 ");
        bookService.saveBook(2, "Book 2", null);
        bookService.saveBook(1, "Book 3 ", "Publisher 3 ");

        Map<Integer, List<Book>> booksByAuthor = bookService.releaseBooksByAuthor(Arrays.asList(1, 2, 3));
        System.out.println(booksByAuthor);
    }
}