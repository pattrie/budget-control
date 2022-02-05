package com.github.pattrie.budgetcontrol.gateways;

import com.github.pattrie.budgetcontrol.domains.Revenue;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;

@Primary
public interface RevenueGateway {

  Revenue save(final Revenue revenue);

  Optional<Revenue> findByDescriptionAndValue(final Revenue revenue);

  List<Revenue> findAll();

  Optional<Revenue> findBy(final String id);

  void delete(final Revenue revenue);
}
