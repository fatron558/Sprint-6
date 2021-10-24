package com.sber.addressbook.filter

import java.util.logging.Logger
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/app/*", "/api/*"])
class LoggerFilter : HttpFilter() {

    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, filterChain: FilterChain?) {
        var LOG = Logger.getLogger(LoggerFilter::class.java.name)
        LOG.info("RequestURL = ${request!!.requestURL}, RequestMethod = ${request.method}")
        try {
            filterChain!!.doFilter(request, response)
        } finally {
            LOG.info("Response status = ${response!!.status}")
        }
    }
}