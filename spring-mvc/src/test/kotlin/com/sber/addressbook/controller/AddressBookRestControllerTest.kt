package com.sber.addressbook.controller

import com.sber.addressbook.model.Note
import com.sber.addressbook.service.AddressBookService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.util.concurrent.ConcurrentMap
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddressBookRestControllerTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var addressBookService: AddressBookService

    private val headers = HttpHeaders()

    private fun url(s: String): String {
        return "http://localhost:${port}/api/${s}"
    }

    private fun getCookie(): String? {
        val userData: MultiValueMap<String, String> = LinkedMultiValueMap()
        userData.set("login", "admin")
        userData.set("password", "admin")
        return restTemplate
            .postForEntity("http://localhost:${port}/login", HttpEntity(userData, HttpHeaders()), String::class.java)
            .headers["Set-Cookie"]!![0]
    }

    @BeforeAll
    fun setUp() {
        headers.add("Cookie", getCookie())
        addressBookService.add(Note("John", "Smith", "Los-Angeles", "09734859"))
        addressBookService.add(Note("Jackie", "Chan", "China", "875356897"))
        addressBookService.add(Note("Karl", "Karlov", "Moscow", "43634"))
    }

    @Test
    fun testAuthenticate() {
        val response = restTemplate.exchange(
            url("list"),
            HttpMethod.GET,
            null,
            String::class.java)
        println(response)
        assertTrue(response.body!!.contains("Enter Login"))
    }

    @Test
    fun testGetAllNotes() {
        val response = restTemplate.exchange(
            url("list"),
            HttpMethod.GET,
            HttpEntity(null, headers),
            object : ParameterizedTypeReference<ConcurrentMap<Int, Note>>() {})
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("John", response.body!![1]!!.firstName)
        assertEquals(addressBookService.getAllNotes().size, response.body!!.size)
    }

    @Test
    fun testGetNote() {
        val response = restTemplate.exchange(
            url("/3/view"),
            HttpMethod.GET,
            HttpEntity(null, headers),
            object : ParameterizedTypeReference<Note>() {})
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("Karl", response.body!!.firstName)
    }

    @Test
    fun testAdd() {
        val newAddress = Note("Dominik", "Torreto", "Dominicans", "246247745")
        val response = restTemplate.exchange(
            url("/add"),
            HttpMethod.POST,
            HttpEntity<Note>(newAddress, headers),
            Any::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertFalse(addressBookService.getAllNotes().values.none { it.equals(newAddress) })
    }

    @Test
    fun testEdit() {
        val newAddress = Note("Dominik", "Torreto", "Dominicans", "246247745")
        val response = restTemplate.exchange(
            url("/2/edit"),
            HttpMethod.PUT,
            HttpEntity<Note>(newAddress, headers),
            Any::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertTrue(addressBookService.getAllNotes().values.none { it.firstName.equals("Chan") })
    }

    @Test
    fun testDelete() {
        val response = restTemplate.exchange(
            url("/1/delete"),
            HttpMethod.DELETE,
            HttpEntity<Note>(null, headers),
            Any::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNull(addressBookService.getAllNotes()[1])
    }
}