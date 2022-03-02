package com.github.pattrie.budgetcontrol.configurations.security;

import com.github.pattrie.budgetcontrol.domains.User;
import com.github.pattrie.budgetcontrol.gateways.repositories.UserRepository;
import com.github.pattrie.budgetcontrol.usecases.TokenService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class AuthenticationViaTokenFilter extends OncePerRequestFilter {

  private final TokenService tokenService;

  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = getToken(request);

    boolean validToken = tokenService.isTokenValid(token);

    if (validToken) {
      authenticateClient(token);
    }

    filterChain.doFilter(request, response);
  }

  private String getToken(HttpServletRequest request) {
    final String token = request.getHeader("Authorization");
    if(token == null || !token.startsWith("Bearer ")) {
      return null;
    }
    return token.substring(7);
  }

  private void authenticateClient(String token) {
    String userId = tokenService.getUserId(token);
    final User user = userRepository.findById(userId).get();
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
