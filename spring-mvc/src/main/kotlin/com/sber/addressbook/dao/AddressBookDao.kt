package com.sber.addressbook.dao

import com.sber.addressbook.model.Note
import java.util.concurrent.ConcurrentMap

interface AddressBookDao {
    fun add(note: Note)
    fun getAllNotes(): ConcurrentMap<Int, Note>
    fun getNote(id: Int): Note?
    fun update(id: Int, note: Note)
    fun delete(id: Int)
}