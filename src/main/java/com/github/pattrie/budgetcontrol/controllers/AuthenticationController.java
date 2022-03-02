package com.github.pattrie.budgetcontrol.controllers;

import com.github.pattrie.budgetcontrol.controllers.jsons.UserRequestJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.TokenResponseJson;
import com.github.pattrie.budgetcontrol.usecases.TokenService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationManager authManager;

  private final TokenService tokenService;

  @PostMapping
  public ResponseEntity<TokenResponseJson> authenticate(@RequestBody @Valid UserRequestJson login) {
        final UsernamePasswordAuthenticationToken loginData = login.converter();

    try {
      final Authentication authentication = authManager.authenticate(loginData);
      final String token = tokenService.generateToken(authentication);

      return ResponseEntity.ok(new TokenResponseJson(token, "Bearer"));
    } catch (AuthenticationException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
