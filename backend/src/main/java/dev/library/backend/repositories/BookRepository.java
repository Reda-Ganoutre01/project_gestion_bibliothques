package dev.library.backend.repositories;

import dev.library.backend.dto.requests.BookRequestDto;
import dev.library.backend.dto.response.BookResponseDto;
import dev.library.backend.models.Book;
import dev.library.backend.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(
            "SELECT b FROM Book b WHERE LOWER(b.title) " +
            "LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(b.author) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(b.category.name) LIKE LOWER(CONCAT('%', :search, '%')) AND b.copies > 0"
    )
    List<Book> searchBooks(@Param("search") String search);

    //get Three Latest Books
    @Query(value="SELECT * FROM books ORDER BY id DESC LIMIT 3", nativeQuery = true)
    List<Book> findThreeLatestBooks();


    // For Top Books Emprunts

    @Query(value = "SELECT b.* FROM books b " +
            "INNER JOIN borrow_records bor ON b.id = bor.book_id " +
            "GROUP BY b.id " +
            "ORDER BY COUNT(bor.book_id) DESC",
            nativeQuery = true)
    List<Book> getTopBooks();


    // Get Books By Category
    @Query(value = "SELECT b.* FROM books b " +
            "INNER JOIN categories cat ON cat.id = b.category_id " +
            "WHERE LOWER(cat.name) LIKE LOWER(CONCAT('%', :categorie, '%'))",
            nativeQuery = true)
    List<Book> getBooksByCategories(@Param("categorie") String categorie);
}
