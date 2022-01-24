package com.github.pattrie.budgetcontrol.usecases;

import com.github.pattrie.budgetcontrol.controllers.jsons.BudgetResponseJson;
import com.github.pattrie.budgetcontrol.domains.Budget;
import com.github.pattrie.budgetcontrol.gateways.RevenueGatewayImpl;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RevenueService implements BudgetControl {

  private final RevenueGatewayImpl budgetGateway;

  private final ModelMapper mapper = new ModelMapper();

  public ResponseEntity<BudgetResponseJson> create(final Budget budget) {

    final Optional<Budget> optionalRevenue = budgetGateway.findByDescriptionAndValue(budget);

    if (optionalRevenue.isPresent()) {
      final Budget budgetFound = optionalRevenue.get();
      log.info(BUDGET_ALREADY_EXISTS, BUDGET_ALREADY_EXISTS + " - Revenue with ID: {} :: {}",
          budgetFound.getId(), budgetFound.getDescription());
      return ResponseEntity.ok().body(mapper.map(budgetFound, BudgetResponseJson.class));
    }

    final BudgetResponseJson revenueSaved = mapper.map(budgetGateway.save(budget),
        BudgetResponseJson.class);
    return ResponseEntity.created(URI.create(LOCATION + revenueSaved.getId())).body(revenueSaved);
  }

  public List<BudgetResponseJson> getAll() {
    return budgetGateway.findAll().stream().map(revenue ->
        mapper.map(revenue, BudgetResponseJson.class)).collect(Collectors.toList());
  }

  public ResponseEntity<BudgetResponseJson> getBy(final String id) {
    final Optional<Budget> revenue = budgetGateway.findBy(id);
    if (revenue.isPresent()) {
      return ResponseEntity.ok().body(mapper.map(revenue.get(), BudgetResponseJson.class));
    }
    log.info(BUDGET_NOT_FOUND, BUDGET_NOT_FOUND + " - Revenue with ID :: {}", id);
    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<BudgetResponseJson> update(final String id, final Budget budget) {
    final Optional<Budget> revenueFound = budgetGateway.findBy(id);
    if (revenueFound.isPresent()) {
      final Budget budgetToUpdate = revenueFound.get();
      log.info("Current revenue information :: {}", budgetToUpdate);
      budgetToUpdate.setDescription(budget.getDescription());
      budgetToUpdate.setValue(budget.getValue());
      final Budget budgetUpdate = budgetGateway.save(budgetToUpdate);
      return ResponseEntity.ok().body(mapper.map(budgetUpdate, BudgetResponseJson.class));
    }

    log.info(BUDGET_NOT_FOUND, BUDGET_NOT_FOUND + " - Revenue with ID :: {}", id);
    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<Object> delete(final String id) {
    final Optional<Budget> revenue = budgetGateway.findBy(id);

    if (revenue.isPresent()) {
      budgetGateway.delete(revenue.get());
      return ResponseEntity.ok().build();
    }

    log.info(BUDGET_NOT_FOUND, BUDGET_NOT_FOUND + " - Revenue with ID :: {}", id);
    return ResponseEntity.noContent().build();
  }
}