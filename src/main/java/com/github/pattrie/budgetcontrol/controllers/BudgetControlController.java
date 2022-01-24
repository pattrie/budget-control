package com.github.pattrie.budgetcontrol.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.github.pattrie.budgetcontrol.controllers.converters.BudgetRequestJsonToBudgetConverter;
import com.github.pattrie.budgetcontrol.controllers.jsons.BudgetRequestJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.BudgetResponseJson;
import com.github.pattrie.budgetcontrol.domains.Budget;
import com.github.pattrie.budgetcontrol.usecases.BudgetControl;
import com.github.pattrie.budgetcontrol.usecases.BudgetControlService;
import com.github.pattrie.budgetcontrol.usecases.ExpenseService;
import com.github.pattrie.budgetcontrol.usecases.RevenueService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/budgets")
@Slf4j
public class BudgetControlController {

  private final BudgetRequestJsonToBudgetConverter converter;

  private final RevenueService revenueService;

  private final ExpenseService expenseService;

  private final BudgetControlService budgetControlService;

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<BudgetResponseJson> create(
      @RequestBody @Valid final BudgetRequestJson budgetRequestJson) {
    log.info("Creation of Budget :: {}", budgetRequestJson.getDescription());

    final Budget budget = converter.convert(budgetRequestJson);
    final BudgetControl budgetCreate = budgetControlService.execute(budget);

    return budgetCreate.create(budget);
  }

  @GetMapping(value = "/revenues", produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<BudgetResponseJson> getAllRevenues() {
    log.info("List all revenues.");
    return revenueService.getAll();
  }

  @GetMapping(value = "/expenses", produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<BudgetResponseJson> getAllExpenses() {
    log.info("List all expenses.");
    return expenseService.getAll();
  }

  @GetMapping(value = "/revenues/{id}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<BudgetResponseJson> getRevenueBy(@PathVariable final String id) {
    log.info("Find revenue with ID :: {}", id);
    return revenueService.getBy(id);
  }

  @GetMapping(value = "/expenses/{id}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<BudgetResponseJson> getExpenseBy(@PathVariable final String id) {
    log.info("Find expense with ID :: {}", id);
    return expenseService.getBy(id);
  }

  @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<BudgetResponseJson> updateBy(@PathVariable final String id,
      @RequestBody @Valid final BudgetRequestJson budgetRequestJson) {
    log.info("Update budget with ID :: {}", id);

    final Budget budget = converter.convert(budgetRequestJson);

    final BudgetControl budgetCreate = budgetControlService.execute(budget);

    return budgetCreate.update(id, budget);
  }

  @DeleteMapping(value = "/revenues/{id}")
  public ResponseEntity<Object> deleteRevenueBy(@PathVariable final String id) {
    log.info("Delete revenue with ID :: {}", id);
    return revenueService.delete(id);
  }

  @DeleteMapping(value = "/expenses/{id}")
  public ResponseEntity<Object> deleteExpenseBy(@PathVariable final String id) {
    log.info("Delete expense with ID :: {}", id);
    return expenseService.delete(id);
  }
}
