package com.sber.addressbook.dao

import com.sber.addressbook.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserDataRepository : JpaRepository<User, Long> {
    fun findByLogin(login: String) : User
}