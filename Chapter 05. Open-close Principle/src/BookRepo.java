import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Antarmuka BookRepo
interface BookRepo {
    List<Book> getBooksByCategory(String category);
    List<Book> getBooksByAuthor(String author);
    List<Book> getBooksByReleaseDate(String releaseDate);
    List<Book> getAllBooks();
}

// Kelas Book
class Book {
    private String kategori;
    private String penulis;
    private double harga;
    private String tanggalRilis;

    // Konstruktor, getter, dan setter
    public Book(String kategori, String penulis, double harga, String tanggalRilis) {
        this.kategori = kategori;
        this.penulis = penulis;
        this.harga = harga;
        this.tanggalRilis = tanggalRilis;
    }

    public String getKategori() {
        return kategori;
    }

    public String getPenulis() {
        return penulis;
    }

    public double getHarga() {
        return harga;
    }

    public String getTanggalRilis() {
        return tanggalRilis;
    }
}

// Kelas BookReq
class BookReq {
    private String jenisPengelompokan;
    private String nilaiPengelompokan;

    public BookReq(String jenisPengelompokan, String nilaiPengelompokan) {
        this.jenisPengelompokan = jenisPengelompokan;
        this.nilaiPengelompokan = nilaiPengelompokan;
    }

    public String getJenisPengelompokan() {
        return jenisPengelompokan;
    }

    public String getNilaiPengelompokan() {
        return nilaiPengelompokan;
    }
}

// Antarmuka BookGroupStrategy
interface BookGroupStrategy {
    BookSummary getRingkasanBuku(BookReq req) throws Exception;
}

// Kelas BookSummary
class BookSummary {
    private String namaKelompok;
    private int totalBuku;
    private double totalHargaBuku;

    public BookSummary(String namaKelompok, int totalBuku, double totalHargaBuku) {
        this.namaKelompok = namaKelompok;
        this.totalBuku = totalBuku;
        this.totalHargaBuku = totalHargaBuku;
    }

    public String getNamaKelompok() {
        return namaKelompok;
    }

    public int getTotalBuku() {
        return totalBuku;
    }

    public double getTotalHargaBuku() {
        return totalHargaBuku;
    }
}

// Strategi Konkret
class BookSummaryByCategory implements BookGroupStrategy {
    private final BookRepo bookRepo;

    public BookSummaryByCategory(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public BookSummary getRingkasanBuku(BookReq req) {
        List<Book> buku = bookRepo.getBooksByCategory(req.getNilaiPengelompokan());
        double totalHarga = buku.stream().mapToDouble(Book::getHarga).sum();
        return new BookSummary("Kategori", buku.size(), totalHarga);
    }
}

class BookSummaryByReleasedDate implements BookGroupStrategy {
    private final BookRepo bookRepo;

    public BookSummaryByReleasedDate(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public BookSummary getRingkasanBuku(BookReq req) {
        List<Book> buku = bookRepo.getBooksByReleaseDate(req.getNilaiPengelompokan());
        double totalHarga = buku.stream().mapToDouble(Book::getHarga).sum();
        return new BookSummary("Tanggal Rilis", buku.size(), totalHarga);
    }
}

class BookSummaryByAuthor implements BookGroupStrategy {
    private final BookRepo bookRepo;

    public BookSummaryByAuthor(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public BookSummary getRingkasanBuku(BookReq req) {
        List<Book> buku = bookRepo.getBooksByAuthor(req.getNilaiPengelompokan());
        double totalHarga = buku.stream().mapToDouble(Book::getHarga).sum();
        return new BookSummary("Penulis", buku.size(), totalHarga);
    }
}

// Kelas BookRepoImpl
class BookRepoImpl implements BookRepo {
    private List<Book> buku;

    public BookRepoImpl() {
        // Inisialisasi dengan beberapa data dummy
        buku = new ArrayList<>();
        buku.add(new Book("Fiksi", "Penulis A", 10.99, "2023-01-01"));
        buku.add(new Book("Non-Fiksi", "Penulis B", 15.99, "2022-05-10"));
        buku.add(new Book("Fiksi", "Penulis C", 8.99, "2021-12-20"));
        buku.add(new Book("Sains", "Penulis A", 12.99, "2023-02-15"));
        buku.add(new Book("Non-Fiksi", "Penulis D", 7.99, "2020-07-25"));
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        return buku.stream()
                .filter(book -> book.getKategori().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return buku.stream()
                .filter(book -> book.getPenulis().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooksByReleaseDate(String releaseDate) {
        return buku.stream()
                .filter(book -> book.getTanggalRilis().equalsIgnoreCase(releaseDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getAllBooks() {
        return buku;
    }
}

// Kelas BookGroupFactory
class BookGroupFactory {
    private final BookRepo bookRepo;

    public BookGroupFactory(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public BookGroupStrategy buildStrategy(String grouping) throws Exception {
        if ("kategori".equalsIgnoreCase(grouping)) {
            return new BookSummaryByCategory(bookRepo);
        } else if ("tanggalRilis".equalsIgnoreCase(grouping)) {
            return new BookSummaryByReleasedDate(bookRepo);
        } else if ("penulis".equalsIgnoreCase(grouping)) {
            return new BookSummaryByAuthor(bookRepo);
        } else {
            throw new Exception("Tidak ada pengelompokan yang ditemukan");
        }
    }
}

// Kelas BookSummaryService
class BookSummaryService {
    private final BookRepo bookRepo;

    public BookSummaryService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public void printSummary(BookReq req) throws Exception {
        BookGroupFactory bookGroupFactory = new BookGroupFactory(bookRepo);
        BookGroupStrategy strategy = bookGroupFactory.buildStrategy(req.getJenisPengelompokan());

        BookSummary books = strategy.getRingkasanBuku(req);
        System.out.println("Nama kelompok = " + books.getNamaKelompok());
        System.out.println("Total buku = " + books.getTotalBuku());
        System.out.println("Total harga = " + books.getTotalHargaBuku());
    }
}

// Kelas Utama untuk Demonstrasi Penggunaan
class Mainn {
    public static void main(String[] args) {
        try {
            BookRepo bookRepo = new BookRepoImpl(); // Implementasi BookRepo
            BookSummaryService summaryService = new BookSummaryService(bookRepo);

            // Gunakan nilai pengelompokan yang sesuai dengan data yang ada
            BookReq reqKategori = new BookReq("kategori", "Fiksi");
            summaryService.printSummary(reqKategori);

            BookReq reqTanggalRilis = new BookReq("tanggalRilis", "2023-01-01");
            summaryService.printSummary(reqTanggalRilis);

            BookReq reqPenulis = new BookReq("penulis", "Penulis A");
            summaryService.printSummary(reqPenulis);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}