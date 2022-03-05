package com.github.pattrie.budgetcontrol.configurations.security;

import com.github.pattrie.budgetcontrol.gateways.repositories.UserRepository;
import com.github.pattrie.budgetcontrol.usecases.AuthenticationService;
import com.github.pattrie.budgetcontrol.usecases.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final AuthenticationService authenticationService;

  private final TokenService tokenService;

  private final UserRepository userRepository;

  @Override
  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/v1/budgets")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/v1/budgets/**")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/auth")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/access")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(
            new AuthenticationViaTokenFilter(tokenService, userRepository),
            UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers(
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui/index.html**",
            "/webjars/**",
            "/actuator/health");
  }
}
