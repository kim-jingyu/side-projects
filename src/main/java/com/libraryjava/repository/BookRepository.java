package com.libraryjava.repository;

import com.libraryjava.domain.book.Book;
import com.libraryjava.dto.book.response.BookStatResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByName(String name);

    @Query("SELECT NEW com.libraryjava.dto.book.response.BookStatResponse(b.type, COUNT(b.id)) FROM Book b GROUP BY b.type")
    List<BookStatResponse> getStats();
}
