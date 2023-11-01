package com.ssafy.practice.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final AccessDeniedHandler customAccessDeniedHandler;


    @Bean
    public PasswordEncoder passwordEncoder(){
        //내부적으로 안정성이 높은 DelegatingPasswordEncoder 인스턴스를 생성해서 PasswordEncoder 빈을 생성한다.
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * DelegatingPasswordEncoder 인스턴스를 사용해서 비밀번호를 암호화하는 방법
     * InMemoryUserDetailsManager를 생성하는 또 다른 방법
     *
     * @param auth
     * @throws Exception
     */
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        //UserDetails 객체를 생성하여 사용자 정보를 담아준다.
        UserDetails user = User.withUsername("user")
                //자바 8의 메서드 레퍼런스를 사용
                //PasswordEncoder의 encode() 메서드를 인자로 전달하면서 passwordEncoder를 등록
                .passwordEncoder(passwordEncoder()::encode)
                .password("p@ssw0rd")
                .roles("USER")
                .build();

        //UserDetails 객체를 생성하여 어드민 정보를 담아준다.
        UserDetails admin = User.withUsername("admin")
                .passwordEncoder(passwordEncoder()::encode)
                .password("pa$$w0rd")
                .roles("ADMIN")
                .build();


        //인메모리에서 UserDetails 객체를 관리하는 매니저를 만든다.
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();


        //생성한 유저정보를 InMemoryUserDetailsManager에게 등록한다.
        userDetailsManager.createUser(user);
        userDetailsManager.createUser(admin);

        return userDetailsManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/login").permitAll()
                //delete 엔드포인트를 admin 권한을 가진 유저만 사용할 수 있도록 정의
                .antMatchers("/delete/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .and()
                //예외 상황이 발생할 경우 다음 Exception을 던진다.
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/images/**", "/css/**", "/h2-console/**");
    }



}
