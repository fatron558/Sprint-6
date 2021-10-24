package com.sber.addressbook.servlet

import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/login")
class AuthenticationServlet: HttpServlet() {
    private val users = mapOf(Pair("admin", "admin"), Pair("user", "user"))

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        req!!.getRequestDispatcher("/loginpage").forward(req, resp)
        return
    }

    override fun doPost(request: HttpServletRequest?, response: HttpServletResponse?) {
        val login : String = request!!.getParameter("login")
        val password : String = request.getParameter("password")

        if(users.containsKey(login) && password.equals(users[login])){
            val cookie = Cookie("auth", LocalDateTime.now().toString())
            response!!.addCookie(cookie)
            response.sendRedirect("/app/add")
            return
        }
        else {
            response!!.sendRedirect("/login")
            return
        }
    }
}