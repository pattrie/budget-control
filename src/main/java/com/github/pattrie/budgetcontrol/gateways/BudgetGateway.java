package com.github.pattrie.budgetcontrol.gateways;

import com.github.pattrie.budgetcontrol.domains.Budget;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;

@Primary
public interface BudgetGateway {

  Budget save(final Budget budget);

  Optional<Budget> findByDescriptionAndValue(final Budget budget);

  List<Budget> findAll();

  Optional<Budget> findBy(final String id);

  void delete(final Budget budgetToDelete);
}
