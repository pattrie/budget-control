package com.github.pattrie.budgetcontrol.usecases;

import com.github.pattrie.budgetcontrol.controllers.jsons.UserResponseJson;
import com.github.pattrie.budgetcontrol.domains.User;
import com.github.pattrie.budgetcontrol.gateways.UserGateway;
import java.net.URI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserGateway userGateway;

  public ResponseEntity<UserResponseJson> create(final User user) {

    final Optional<User> email = userGateway.findByEmail(user);
    if (email.isPresent()) {
      return ResponseEntity.badRequest().build();
    }

    final User userSaved = userGateway.save(user);

    final UserResponseJson userResponseJson =
        UserResponseJson.builder()
            .id(userSaved.getId())
            .name(userSaved.getName())
            .email(userSaved.getEmail())
            .build();

    return ResponseEntity.created(URI.create("http://localhost:8080/login" + userResponseJson.getId()))
        .body(userResponseJson);
  }
}
