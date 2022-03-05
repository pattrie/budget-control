package com.github.pattrie.budgetcontrol.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.github.pattrie.budgetcontrol.controllers.jsons.UserRequestJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.UserResponseJson;
import com.github.pattrie.budgetcontrol.domains.User;
import com.github.pattrie.budgetcontrol.usecases.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/access")
@RequiredArgsConstructor
public class AccessController {

  private final UserService userService;

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<UserResponseJson> createLogin(
      @RequestBody @Valid final UserRequestJson userRequestJson) {
    log.info("Creation of login :: {}", userRequestJson.getEmail());
    final User user =
        User.builder()
            .name(userRequestJson.getName())
            .email(userRequestJson.getEmail())
            .password(new BCryptPasswordEncoder().encode(userRequestJson.getPassword()))
            .build();
    return userService.create(user);
  }
}
