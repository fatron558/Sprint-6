package com.sber.addressbook.filter

import java.time.LocalDateTime
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/app/*", "/api/*"])
class CookieFilter : HttpFilter() {

    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, filterChain: FilterChain?) {
        val cookies = request!!.cookies
        if (cookies != null) {
            val flag = cookies.none { it.name.equals("auth") && it.value < LocalDateTime.now().toString() }
            if (!flag) {
                filterChain!!.doFilter(request, response)
            } else {
                response!!.sendRedirect("/login")
            }
        } else {
            response!!.sendRedirect("/login")
        }
    }
}