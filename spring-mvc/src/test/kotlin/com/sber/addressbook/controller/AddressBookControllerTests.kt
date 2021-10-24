package com.sber.addressbook.controller

import com.sber.addressbook.model.Note
import com.sber.addressbook.service.AddressBookService
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddressBookControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var addressBookService: AddressBookService

    @BeforeAll
    fun setUp() {
        addressBookService.add(Note("John", "Smith", "Los-Angeles", "09734859"))
        addressBookService.add(Note("Jackie", "Chan", "China", "875356897"))
        addressBookService.add(Note("Karl", "Karlov", "Moscow", "43634"))
    }

    @Test
    fun testGetAllNotes() {
        mockMvc.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(view().name("allNotes"))
            .andExpect(model().attributeExists("notes"))
            .andExpect(content().string(containsString("John")))
    }

    @Test
    fun testGetNote() {
        mockMvc.perform(get("/app/3/view"))
            .andExpect(status().isOk)
            .andExpect(view().name("noteInfo"))
            .andExpect(model().attributeExists("note"))
            .andExpect(content().string(containsString("Karl")))
    }

    @Test
    fun testAdd() {
        mockMvc.perform(post("/app/add")
            .param("firstName", "Dominik")
            .param("lastName", "Torreto")
            .param("address", "Dominicans")
            .param("phone", "246247745")
        )
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
            .andExpect(view().name("redirect:/app/list"))

        mockMvc.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("Dominik")))
    }

    @Test
    fun testEdit() {
        mockMvc.perform(put("/app/2/edit")
            .param("firstName", "Dominik")
            .param("lastName", "Torreto")
            .param("address", "Dominicans")
            .param("phone", "246247745")
        )
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
            .andExpect(view().name("redirect:/app/list"))

        mockMvc.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("Dominik")))
            .andExpect(content().string(not(containsString("Chan"))))
    }

    @Test
    fun testDelete() {
        mockMvc.perform(delete("/app/1/delete"))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
            .andExpect(view().name("redirect:/app/list"))

        mockMvc.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(content().string(not(containsString("Smith"))))
    }

}
