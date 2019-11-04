package com.web.config;

import com.web.WebMain;
import com.web.auth.CustomAccessDeniedHandler;
import com.web.auth.CustomAuthenticationSuccessHandler;
import com.web.auth.CustomUserDetailsService;
import com.web.auth.CustomWebAuthenticationDetailsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Autowired
  private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(customUserDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    if (WebMain.IS_DEV_ENV) {
      http.cors();
    }

    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/login", "/signup", "/confirm-account", "/favicon.ico",
            "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/css/**", "/vendors/**",
            "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
        .antMatchers("/forgot-password", "/reset-password").permitAll()
        .antMatchers("/", "/downloadFile/", "/api/**", "/api/**/**").hasAnyRole("USER", "ADMIN")
        .anyRequest()
        .authenticated().and()
        .formLogin()
        .authenticationDetailsSource(authenticationDetailsSource)
        .loginPage("/login")
        .usernameParameter("email")
        .passwordParameter("password")
        .defaultSuccessUrl("/index",true)
//        .successHandler(customAuthenticationSuccessHandler())
        .failureUrl("/login?error=true")
        .and().logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login")
        .invalidateHttpSession(false)
        .deleteCookies("JSESSIONID")
        .and().exceptionHandling()
        .accessDeniedPage("/access-denied")
        .accessDeniedHandler(customAccessDeniedHandler());

  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
        .ignoring()
        .antMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/vendors/**");
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    if (WebMain.IS_DEV_ENV) {
      CorsConfiguration corsConfiguration = new CorsConfiguration();
      corsConfiguration.addAllowedOrigin("http://localhost:3000");
      corsConfiguration.addAllowedHeader("*");
      corsConfiguration.addAllowedMethod("*");
      corsConfiguration.setAllowCredentials(true);
      source.registerCorsConfiguration("/**", corsConfiguration);
    }
    return source;
  }

  @Bean
  public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
    return new CustomAuthenticationSuccessHandler();
  }

  @Bean
  public AccessDeniedHandler customAccessDeniedHandler() {
    return new CustomAccessDeniedHandler();
  }

}
