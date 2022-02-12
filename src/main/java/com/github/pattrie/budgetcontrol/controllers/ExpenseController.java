package com.github.pattrie.budgetcontrol.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.github.pattrie.budgetcontrol.controllers.converters.ExpenseRequestJsonToExpenseConverter;
import com.github.pattrie.budgetcontrol.controllers.jsons.ExpenseRequestJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.ExpenseResponseJson;
import com.github.pattrie.budgetcontrol.domains.Expense;
import com.github.pattrie.budgetcontrol.usecases.ExpenseService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/budgets/expenses")
@RequiredArgsConstructor
public class ExpenseController {

  public static final String ID = "/{id}";
  public static final String YEAR = "/{year}";
  public static final String MONTH = "/{month}";
  public static final String SEARCH = "/search";

  private final ExpenseRequestJsonToExpenseConverter toExpense;

  private final ExpenseService expenseService;

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<ExpenseResponseJson> createExpense(
      @RequestBody @Valid final ExpenseRequestJson expenseRequestJson) {
    log.info("Creation of expense :: {}", expenseRequestJson.getDescription());
    final Expense expense = toExpense.convert(expenseRequestJson);
    return expenseService.create(expense);
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<ExpenseResponseJson> getAllExpenses() {
    log.info("List all expenses.");
    return expenseService.getAll();
  }

  @GetMapping(value = SEARCH, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<ExpenseResponseJson> getExpenseByDescription(@RequestParam("description") String description) {
    log.info("Find expense by description :: {}", description);
    return expenseService.getExpenseByDescription(description);
  }

  @GetMapping(value = ID, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<ExpenseResponseJson> getExpenseBy(@PathVariable final String id) {
    log.info("Find expense with ID :: {}", id);
    return expenseService.getBy(id);
  }

  @GetMapping(value = YEAR + MONTH, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ExpenseResponseJson>> getExpenseByYearAndMonth(
      @PathVariable("year") final int year, @PathVariable("month") final int month) {
    log.info("Find expense - Period :: {} / {}", month, year);
    return expenseService.getExpenseByYearAndMonth(month, year);
  }

  @PutMapping(value = ID, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<ExpenseResponseJson> updateExpenseBy(@PathVariable final String id,
      @RequestBody @Valid final ExpenseRequestJson expenseRequestJson) {
    log.info("Update expense with ID :: {}", id);
    final Expense expense = toExpense.convert(expenseRequestJson);
    return expenseService.update(id, expense);
  }

  @DeleteMapping(value = ID)
  public ResponseEntity<Object> deleteExpenseBy(@PathVariable final String id) {
    log.info("Delete expense with ID :: {}", id);
    return expenseService.delete(id);
  }
}