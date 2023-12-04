package com.library.repository.book

import com.library.domain.book.QBook.book
import com.library.dto.book.BookStatResponse
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class BookQuerydslRepository(
    private val queryFactory: JPAQueryFactory
) {

    fun getStatistics() : List<BookStatResponse> {
        return queryFactory.select(
            Projections.constructor(
                BookStatResponse::class.java,
                book.type,
                book.id.count(),
            ))
            .from(book)
            .groupBy(book.type)
            .fetch()
    }
}