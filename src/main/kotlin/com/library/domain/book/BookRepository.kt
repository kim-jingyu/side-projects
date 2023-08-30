package com.library.domain.book

import com.library.dto.book.BookStatResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface BookRepository : JpaRepository<Book, Long> {
    fun findByName(bookName: String): Book?

    @Query("SELECT NEW com.library.dto.book.BookStatResponse(b.type, COUNT(b.id)) FROM Book b GROUP BY b.type")
    fun getStatistics() : List<BookStatResponse>
}