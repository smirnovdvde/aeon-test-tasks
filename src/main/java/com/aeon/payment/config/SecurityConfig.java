package com.aeon.payment.config;

import com.aeon.payment.security.ApiInvoicePasswordEncoderFactories;
import com.aeon.payment.service.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JpaUserDetailsService userDetailsService;
    private final PersistentTokenRepository persistentTokenRepository;

    public SecurityConfig(JpaUserDetailsService userDetailsService, PersistentTokenRepository persistentTokenRepository) {
        super();
        this.userDetailsService = userDetailsService;
        this.persistentTokenRepository = persistentTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeRequests(
                authorize ->{
                    authorize
                            .antMatchers("/h2-console/**").permitAll();

                }
        ).authorizeRequests()
                .anyRequest().authenticated().and().httpBasic()
                .and().csrf().disable()
        .authorizeRequests().and().rememberMe().tokenRepository(persistentTokenRepository);

        //h2 console
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return ApiInvoicePasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
