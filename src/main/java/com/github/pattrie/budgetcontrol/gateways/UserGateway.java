package com.github.pattrie.budgetcontrol.gateways;

import com.github.pattrie.budgetcontrol.domains.User;
import java.util.Optional;
import org.springframework.context.annotation.Primary;

@Primary
public interface UserGateway {

  User save(final User user);

  Optional<User> findByEmail(final User user);
}
