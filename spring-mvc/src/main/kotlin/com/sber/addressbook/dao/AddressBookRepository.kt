package com.sber.addressbook.dao

import com.sber.addressbook.model.Note
import org.springframework.data.repository.CrudRepository

interface AddressBookRepository : CrudRepository<Note, Long>