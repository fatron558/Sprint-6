package com.sber.addressbook.controller

import com.sber.addressbook.model.Note
import com.sber.addressbook.service.AddressBookService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AddressBookRestController(val addressBookService: AddressBookService) {

    @GetMapping("/list")
    fun getAllNotes(): Map<Int, Note> = addressBookService.getAllNotes()

    @GetMapping("/{id}/view")
    fun getNote(@PathVariable("id") id: Int): Note? = addressBookService.getNote(id)

    @PostMapping("/add")
    fun addNote(@RequestBody note: Note) = addressBookService.add(note)

    @PutMapping("/{id}/edit")
    fun editNote(@PathVariable("id") id: Int, @RequestBody note: Note) = addressBookService.update(id, note)

    @DeleteMapping("/{id}/delete")
    fun deleteNote(@PathVariable("id") id: Int) = addressBookService.delete(id)
}