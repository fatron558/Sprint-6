package com.sber.addressbook.controller

import com.sber.addressbook.model.Note
import com.sber.addressbook.service.AddressBookService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/app")
class AddressBookController(val addressBookService: AddressBookService) {

    @GetMapping("/list")
    fun getAllNotes(model: Model): String? {
        model.addAttribute("notes", addressBookService.getAllNotes())
        return "allNotes"
    }

    @GetMapping("/add")
    fun add(model: Model): String {
        model.addAttribute("note", Note())
        return "add"
    }

    @PostMapping("/add")
    fun addNote(
        @ModelAttribute("note") note: Note,
    ): String {
        addressBookService.add(note)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/view")
    fun getNote(@PathVariable("id") id: Int, model: Model): String {
        val note = addressBookService.getNote(id)
        model.addAttribute("note", note)
        return "noteInfo"
    }

    @PostMapping("/{id}/edit")
    fun edit(@PathVariable("id") id: Int, model: Model): String {
        model.addAttribute("note", addressBookService.getNote(id))
        model.addAttribute("noteId", id)
        return "edit"
    }

    @PutMapping("/{id}/edit")
    fun editNote(
        @PathVariable("id") id: Int,
        @ModelAttribute("note") note: Note,
    ): String {
        addressBookService.update(id, note)
        return "redirect:/app/list"
    }

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable("id") id: Int): String {
        addressBookService.delete(id)
        return "redirect:/app/list"
    }
}