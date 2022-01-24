package com.github.pattrie.budgetcontrol.gateways;

import com.github.pattrie.budgetcontrol.domains.Budget;
import com.github.pattrie.budgetcontrol.domains.Expense;
import com.github.pattrie.budgetcontrol.gateways.repositories.ExpenseRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ExpenseGatewayImpl implements BudgetGateway {

  private final ExpenseRepository repository;

  private final ModelMapper mapper = new ModelMapper();

  @Override
  public Budget save(final Budget budget) {
    log.info("Saving revenue: {}", budget);
    return repository.save(mapper.map(budget, Expense.class));
  }

  @Override
  public Optional<Budget> findByDescriptionAndValue(final Budget budget) {
    log.info(
        "Find revenue by description: {} and value: {}",
        budget.getDescription(),
        budget.getValue());

    return repository.findByDescriptionAndValue(budget.getDescription(), budget.getValue());
  }

  @Override
  public List<Budget> findAll() {
    return repository.findAll().stream()
        .map(expense -> mapper.map(expense, Budget.class))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Budget> findBy(String id) {
    final Optional<Expense> expense = repository.findById(id);
    return Optional.of(mapper.map(expense.get(), Budget.class));
  }

  @Override
  public void delete(final Budget budget) {
    repository.delete(mapper.map(budget, Expense.class));
  }
}
