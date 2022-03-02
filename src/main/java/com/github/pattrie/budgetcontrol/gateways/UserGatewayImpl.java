package com.github.pattrie.budgetcontrol.gateways;

import com.github.pattrie.budgetcontrol.domains.User;
import com.github.pattrie.budgetcontrol.gateways.repositories.UserRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserGatewayImpl implements UserGateway {

  private final UserRepository userRepository;

  @Override
  public User save(User user) {
    log.info("Saving user: {}", user.getName());
    return userRepository.save(user);
  }

  @Override
  public Optional<User> findByEmail(User user) {
    log.info("Searching email: {}", user.getEmail());
    return userRepository.findByEmail(user.getEmail());
  }
}
