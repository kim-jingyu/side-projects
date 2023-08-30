package com.library.domain.user.loanhistory

import com.library.domain.user.User
import jakarta.persistence.*

@Entity
class UserLoanHistory(
    @ManyToOne
    val user: User,

    val bookName: String,

    @Enumerated(EnumType.STRING)
    var status: UserLoanStatus = UserLoanStatus.LOANED,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {

    fun doReturn() {
        this.status = UserLoanStatus.RETURNED
    }

    companion object{
        fun fixture(
            user: User,
            bookName: String = "이상한 나라의 앨리스",
            status: UserLoanStatus = UserLoanStatus.LOANED,
        ): UserLoanHistory {
            return UserLoanHistory(
                user = user,
                bookName = bookName,
                status = status,
            )
        }
    }
}