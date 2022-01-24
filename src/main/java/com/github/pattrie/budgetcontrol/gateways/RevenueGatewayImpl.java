package com.github.pattrie.budgetcontrol.gateways;

import com.github.pattrie.budgetcontrol.domains.Budget;
import com.github.pattrie.budgetcontrol.domains.Revenue;
import com.github.pattrie.budgetcontrol.gateways.repositories.RevenueRepository;
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
public class RevenueGatewayImpl implements BudgetGateway {

  private final RevenueRepository repository;

  private final ModelMapper mapper = new ModelMapper();

  @Override
  public Budget save(final Budget budget) {
    log.info("Saving revenue: {}", budget);
    return repository.save(mapper.map(budget, Revenue.class));
  }

  @Override
  public Optional<Budget> findByDescriptionAndValue(final Budget budget) {
    log.info("Find revenue by description: {} and value: {}", budget.getDescription(),
        budget.getValue());

    return repository.findByDescriptionAndValue(budget.getDescription(), budget.getValue());
  }

  @Override
  public List<Budget> findAll() {
    return repository.findAll().stream().map(revenue ->
        mapper.map(revenue, Budget.class)).collect(Collectors.toList());
  }

  @Override
  public Optional<Budget> findBy(String id) {
    final Optional<Revenue> revenue = repository.findById(id);
    return Optional.of(mapper.map(revenue.get(), Budget.class));
  }

  @Override
  public void delete(final Budget budget) {
    repository.delete(mapper.map(budget, Revenue.class));
  }
}
