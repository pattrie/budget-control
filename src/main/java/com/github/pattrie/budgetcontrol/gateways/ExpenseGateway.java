package com.github.pattrie.budgetcontrol.gateways;

import com.github.pattrie.budgetcontrol.domains.Expense;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;

@Primary
public interface ExpenseGateway {

  Expense save(final Expense expense);

  Optional<Expense> findByDescriptionAndValue(final Expense expense);

  List<Expense> findByDescription(final String description);

  List<Expense> findAll();

  Optional<Expense> findBy(final String id);

  List<Expense> findByYearAndMonth(final LocalDateTime initialDate, final LocalDateTime finalDate);

  void delete(final Expense expense);
}
