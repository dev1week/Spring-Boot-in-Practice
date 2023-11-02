package com.ssafy.practice.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    private final AccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        ////Authentication ManagerBuilder를 사용해서 LDAP 인증을 적용
        auth.ldapAuthentication()
                //ldif 파일에 추가한 값과 같아야한다. ou는 사용자 계정이 people 조직에 속함을 의미한다.
                .userDnPatterns("uid={0},ou=people")
                .contextSource()
                //인증시 시큐리티가 호출하는 LDAP 서버를 url과 함께 지정한다.
                .url("ldap://localhost:8389/dc=manning,dc=com")
                .and()
                //비밀번호 확인작업 등록 ldap 인증에서는 비밀번호를 ldap 서버에 제공하고 ldap 서버가 판단한다.
                .passwordCompare()
                //암호화에 사용할 인코더 등록
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                //비밀번호로 사용하는 속성명을 지정한다,. userPassword라는 이름으로 ldif 파일에 지정했으므로 맞춰준다. 
                .passwordAttribute("userPassword");
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
