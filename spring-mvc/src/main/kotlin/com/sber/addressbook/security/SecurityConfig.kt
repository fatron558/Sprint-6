package com.sber.addressbook.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val loginSuccessHandler: LoginSuccessHandler,
) : WebSecurityConfigurerAdapter() {

    @Autowired
    fun configureGlobalSecurity(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.formLogin()
            .successHandler(loginSuccessHandler)
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .usernameParameter("login")
            .passwordParameter("password")
            .permitAll()
        http.logout()
            .permitAll()
            .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login?logout")
            .and().csrf().disable()
        http
            .authorizeRequests()
            .antMatchers("/login").anonymous()
            .antMatchers("/api/**").hasAnyRole("API", "ADMIN")
            .antMatchers("/app/**").hasAnyRole("APP", "ADMIN")
            .anyRequest().authenticated()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

}
