package com.sber.addressbook.service

import com.sber.addressbook.dao.AddressBookDao
import com.sber.addressbook.model.Note
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentMap

@Service
class AddressBookServiceImpl(val addressBookDao: AddressBookDao) : AddressBookService {

    override fun add(note: Note) = addressBookDao.add(note)

    override fun getAllNotes(): ConcurrentMap<Int, Note> = addressBookDao.getAllNotes()

    override fun getNote(id: Int): Note? = addressBookDao.getNote(id)

    override fun update(id: Int, note: Note) = addressBookDao.update(id, note)

    override fun delete(id: Int) = addressBookDao.delete(id)
}