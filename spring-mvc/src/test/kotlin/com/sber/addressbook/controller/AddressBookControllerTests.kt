package com.sber.addressbook.controller

import com.sber.addressbook.model.Note
import com.sber.addressbook.service.AddressBookService
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddressBookControllerTests {

    private var mockMvc: MockMvc? = null

    @Autowired
    private val context: WebApplicationContext? = null

    @Autowired
     lateinit var addressBookService: AddressBookService

    @BeforeAll
    fun setUpAll() {
        addressBookService.save(Note(firstName ="John", lastName = "Smith", address = "Los-Angeles", phone = "09734859"))
        addressBookService.save(Note(firstName = "Jackie", lastName = "Chan", address = "China", phone = "875356897"))
        addressBookService.save(Note(firstName = "Karl", lastName = "Karlov", address = "Moscow", phone = "43634"))
    }

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context!!)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    @WithMockUser(
        username = "app",
        roles = ["APP"]
    )
    @Test
    fun testGetAllNotes() {
        mockMvc!!.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(view().name("allNotes"))
            .andExpect(model().attributeExists("notes"))
            .andExpect(content().string(containsString("John")))
    }

    @WithMockUser(
        username = "app",
        roles = ["APP"]
    )
    @Test
    fun testGetNote() {
        mockMvc!!.perform(get("/app/3/view"))
            .andExpect(status().isOk)
            .andExpect(view().name("noteInfo"))
            .andExpect(model().attributeExists("note"))
            .andExpect(content().string(containsString("Karl")))
    }

    @WithMockUser(
        username = "app",
        roles = ["APP"]
    )
    @Test
    fun testAdd() {
        mockMvc!!.perform(post("/app/add")
            .param("firstName", "Dominik")
            .param("lastName", "Torreto")
            .param("address", "Dominicans")
            .param("phone", "246247745")
        )
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
            .andExpect(view().name("redirect:/app/list"))

        mockMvc!!.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("Dominik")))
    }

    @WithMockUser(
        username = "app",
        roles = ["APP"]
    )
    @Test
    fun testEdit() {
        mockMvc!!.perform(put("/app/2/edit")
            .param("firstName", "Dominik")
            .param("lastName", "Torreto")
            .param("address", "Dominicans")
            .param("phone", "246247745")
        )
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
            .andExpect(view().name("redirect:/app/list"))

        mockMvc!!.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("Dominik")))
            .andExpect(content().string(not(containsString("Chan"))))
    }

    @WithMockUser(
        username = "app",
        roles = ["APP"]
    )
    @Test
    fun testDeleteError() {
        mockMvc!!.perform(delete("/app/1/delete"))
            .andExpect(status().is4xxClientError)
    }
    @WithMockUser(
        username = "admin",
        roles = ["ADMIN"]
    )
    @Test
    fun testDelete() {
        mockMvc!!.perform(delete("/app/1/delete"))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
            .andExpect(view().name("redirect:/app/list"))

        mockMvc!!.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(content().string(not(containsString("John"))))
    }
}
