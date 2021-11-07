package com.sber.addressbook.service

import com.sber.addressbook.dao.UserDataRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    val userDataRepository: UserDataRepository
) : UserDetailsService {
    override fun loadUserByUsername(login: String?): UserDetails {
        return userDataRepository.findByLogin(login!!)
    }
}