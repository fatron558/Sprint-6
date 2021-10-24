package com.sber.addressbook.dao

import com.sber.addressbook.model.Note
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Repository
class AddressBookDaoImpl : AddressBookDao {
    val dataBase = ConcurrentHashMap<Int, Note>()
    var id = AtomicInteger(0)

    override fun add(note: Note) {
        dataBase[id.incrementAndGet()] = note
    }

    override fun getAllNotes() = dataBase

    override fun getNote(id: Int): Note? = dataBase[id]

    override fun update(id: Int, note: Note) {
        dataBase[id] = note
    }

    override fun delete(id: Int) {
        dataBase.remove(id)
    }
}