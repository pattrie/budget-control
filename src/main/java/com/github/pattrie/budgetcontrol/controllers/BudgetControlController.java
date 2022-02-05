package com.github.pattrie.budgetcontrol.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.github.pattrie.budgetcontrol.controllers.converters.ExpenseRequestJsonToExpenseConverter;
import com.github.pattrie.budgetcontrol.controllers.converters.RevenueRequestJsonToRevenueConverter;
import com.github.pattrie.budgetcontrol.controllers.jsons.ExpenseRequestJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.ExpenseResponseJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueRequestJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueResponseJson;
import com.github.pattrie.budgetcontrol.domains.Expense;
import com.github.pattrie.budgetcontrol.domains.Revenue;
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

  private final RevenueRequestJsonToRevenueConverter toRevenue;

  private final ExpenseRequestJsonToExpenseConverter toExpense;

  private final RevenueService revenueService;

  private final ExpenseService expenseService;

  @PostMapping(value = "/revenues", consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<RevenueResponseJson> createRevenue(
      @RequestBody @Valid final RevenueRequestJson revenueRequestJson) {
    log.info("Creation of Budget :: {}", revenueRequestJson.getDescription());
    final Revenue revenue = toRevenue.convert(revenueRequestJson);
    return revenueService.create(revenue);
  }

  @PostMapping(value = "/expenses", consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<ExpenseResponseJson> createExpense(
      @RequestBody @Valid final ExpenseRequestJson expenseRequestJson) {
    log.info("Creation of Budget :: {}", expenseRequestJson.getDescription());
    final Expense expense = toExpense.convert(expenseRequestJson);
    return expenseService.create(expense);
  }

  @GetMapping(value = "/revenues", produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<RevenueResponseJson> getAllRevenues() {
    log.info("List all revenues.");
    return revenueService.getAll();
  }

  @GetMapping(value = "/expenses", produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<ExpenseResponseJson> getAllExpenses() {
    log.info("List all expenses.");
    return expenseService.getAll();
  }

  @GetMapping(value = "/revenues/{id}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<RevenueResponseJson> getRevenueBy(@PathVariable final String id) {
    log.info("Find revenue with ID :: {}", id);
    return revenueService.getBy(id);
  }

  @GetMapping(value = "/expenses/{id}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<ExpenseResponseJson> getExpenseBy(@PathVariable final String id) {
    log.info("Find expense with ID :: {}", id);
    return expenseService.getBy(id);
  }

  @PutMapping(value = "/revenues/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<RevenueResponseJson> updateRevenueBy(@PathVariable final String id,
      @RequestBody @Valid final RevenueRequestJson revenueRequestJson) {
    log.info("Update budget with ID :: {}", id);
    final Revenue revenue = toRevenue.convert(revenueRequestJson);
    return revenueService.update(id, revenue);
  }

  @PutMapping(value = "/expenses/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<ExpenseResponseJson> updateExpenseBy(@PathVariable final String id,
      @RequestBody @Valid final ExpenseRequestJson expenseRequestJson) {
    log.info("Update budget with ID :: {}", id);
    final Expense expense = toExpense.convert(expenseRequestJson);
    return expenseService.update(id, expense);
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
