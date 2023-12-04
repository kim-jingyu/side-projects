package com.library.domain.user

interface UserRepositoryCustom {
    fun findAllWithHistories() : List<User>
}