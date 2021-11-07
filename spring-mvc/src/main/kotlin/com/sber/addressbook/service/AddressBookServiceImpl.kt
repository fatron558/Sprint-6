package com.sber.addressbook.service

import com.sber.addressbook.dao.AddressBookRepository
import com.sber.addressbook.model.Note
import org.springframework.stereotype.Service

@Service
class AddressBookServiceImpl(val addressBookRepository: AddressBookRepository) : AddressBookService {

    override fun save(note: Note) {
        addressBookRepository.save(note)
    }

    override fun getAllNotes(): MutableIterable<Note> = addressBookRepository.findAll()

    override fun getNote(id: Long): Note? = addressBookRepository.findById(id).get()

    override fun delete(id: Long) = addressBookRepository.deleteById(id)
}