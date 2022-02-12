package com.github.pattrie.budgetcontrol.gateways;

import com.github.pattrie.budgetcontrol.domains.Expense;
import com.github.pattrie.budgetcontrol.gateways.repositories.ExpenseRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ExpenseGatewayImpl implements ExpenseGateway {

  private final ExpenseRepository repository;

  @Override
  public Expense save(final Expense expense) {
    log.info("Saving expense: {}", expense);
    return repository.save(expense);
  }

  @Override
  public Optional<Expense> findByDescriptionAndValue(final Expense expense) {
    log.info("Find expense by description: {} and value: {}",
        expense.getDescription(),
        expense.getValue());

    return repository.findByDescriptionAndValue(expense.getDescription(), expense.getValue());
  }

  @Override
  public List<Expense> findByDescription(final String description) {
    return repository.findByDescription(description);
  }

  @Override
  public List<Expense> findByYearAndMonth(final LocalDateTime initialDate,
      final LocalDateTime finalDate) {
    return repository.findByDate(initialDate, finalDate);
  }

  @Override
  public List<Expense> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Expense> findBy(String id) {
    return repository.findById(id);
  }

  @Override
  public void delete(final Expense expense) {
    repository.delete(expense);
  }
}
