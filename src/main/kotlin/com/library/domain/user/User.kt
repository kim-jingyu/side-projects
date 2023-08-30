package com.library.domain.user

import com.library.domain.book.Book
import com.library.domain.user.loanhistory.UserLoanHistory
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    var name: String,
    val age: Int?,

    @Enumerated(EnumType.STRING)
    val userStatus: UserStatus,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름은 비어있을 수 없습니다.")
        }
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun loanBook(book: Book) {
        this.userLoanHistories.add(UserLoanHistory(this, book.name))
    }

    fun returnBook(bookName: String) {
        this.userLoanHistories.first {
            history -> history.bookName == bookName
        }.doReturn()
    }

    companion object{
        fun fixture(
            name: String = "유저1",
            age: Int? = 12,
            userStatus: UserStatus = UserStatus.ACTIVE,
        ): User {
            return User(
                name = name,
                age = age,
                userStatus = userStatus,
            )
        }
    }
}