package com.sber.addressbook.model

import javax.persistence.*

@Entity
@Table(name = "notes")
class Note(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var phone: String = ""
)