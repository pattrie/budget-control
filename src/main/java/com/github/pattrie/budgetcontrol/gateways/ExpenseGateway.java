package com.github.pattrie.budgetcontrol.gateways;

import com.github.pattrie.budgetcontrol.domains.Expense;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;

@Primary
public interface ExpenseGateway {

  Expense save(final Expense expense);

  Optional<Expense> findByDescriptionAndValue(final Expense expense);

  List<Expense> findAll();

  Optional<Expense> findBy(final String id);

  void delete(final Expense expense);
}
