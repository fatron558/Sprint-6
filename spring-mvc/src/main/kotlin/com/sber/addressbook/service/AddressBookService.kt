package com.sber.addressbook.service

import com.sber.addressbook.model.Note
import org.springframework.security.access.prepost.PreAuthorize

interface AddressBookService {
    fun save(note: Note)
    fun getAllNotes(): MutableIterable<Note>
    fun getNote(id: Long): Note?
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(id: Long)
}