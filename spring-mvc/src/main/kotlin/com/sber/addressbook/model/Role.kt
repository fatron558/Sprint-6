package com.sber.addressbook.model

import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

@Entity
@Table(name = "roles")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(unique = true)
    var roleName: String,
    @ManyToMany(mappedBy = "roles")
    var user: MutableSet<User> = HashSet(),
) : GrantedAuthority {
    override fun getAuthority(): String {
        return roleName
    }
}