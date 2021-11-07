package com.sber.addressbook.controller

import com.sber.addressbook.model.Note
import com.sber.addressbook.service.AddressBookService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AddressBookRestController(val addressBookService: AddressBookService) {

    @GetMapping("/list")
    fun getAllNotes(): MutableIterable<Note> = addressBookService.getAllNotes()

    @GetMapping("/{id}/view")
    fun getNote(@PathVariable("id") id: Long): Note? = addressBookService.getNote(id)

    @PostMapping("/add")
    fun addNote(@RequestBody note: Note) = addressBookService.save(note)

    @PutMapping("/{id}/edit")
    fun editNote(@PathVariable("id") id: Long, @RequestBody note: Note) = addressBookService.save(note)

    @DeleteMapping("/{id}/delete")
    fun deleteNote(@PathVariable("id") id: Long) = addressBookService.delete(id)
}