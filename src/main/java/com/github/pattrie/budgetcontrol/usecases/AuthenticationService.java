package com.github.pattrie.budgetcontrol.usecases;

import com.github.pattrie.budgetcontrol.domains.User;
import com.github.pattrie.budgetcontrol.gateways.repositories.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final Optional<User> user = userRepository.findByEmail(username);
    if (user.isPresent()) {
      return user.get();
    }
    throw new UsernameNotFoundException("Invalid information.");
  }
}
