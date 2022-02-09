package com.github.pattrie.budgetcontrol.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.github.pattrie.budgetcontrol.controllers.converters.ExpenseRequestJsonToExpenseConverter;
import com.github.pattrie.budgetcontrol.controllers.converters.RevenueRequestJsonToRevenueConverter;
import com.github.pattrie.budgetcontrol.controllers.jsons.BudgetResponseJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.ExpenseRequestJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.ExpenseResponseJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueRequestJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueResponseJson;
import com.github.pattrie.budgetcontrol.domains.Expense;
import com.github.pattrie.budgetcontrol.domains.Revenue;
import com.github.pattrie.budgetcontrol.usecases.ExpenseService;
import com.github.pattrie.budgetcontrol.usecases.RevenueService;
import com.github.pattrie.budgetcontrol.usecases.BudgetControlService;
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
@RequestMapping("/v1/budgets")
@RequiredArgsConstructor
public class BudgetControlController {

  public static final String ID = "/{id}";
  public static final String YEAR = "/{year}";
  public static final String MONTH = "/{month}";
  public static final String REVENUES = "/revenues";
  public static final String EXPENSES = "/expenses";
  public static final String SEARCH = "/search";
  public static final String RESUME = "/resume";

  private final RevenueRequestJsonToRevenueConverter toRevenue;
  private final ExpenseRequestJsonToExpenseConverter toExpense;

  private final RevenueService revenueService;
  private final ExpenseService expenseService;
  private final BudgetControlService budgetService;

  @PostMapping(value = REVENUES, consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<RevenueResponseJson> createRevenue(
      @RequestBody @Valid final RevenueRequestJson revenueRequestJson) {
    log.info("Creation of Budget :: {}", revenueRequestJson.getDescription());
    final Revenue revenue = toRevenue.convert(revenueRequestJson);
    return revenueService.create(revenue);
  }

  @PostMapping(value = EXPENSES, consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<ExpenseResponseJson> createExpense(
      @RequestBody @Valid final ExpenseRequestJson expenseRequestJson) {
    log.info("Creation of Budget :: {}", expenseRequestJson.getDescription());
    final Expense expense = toExpense.convert(expenseRequestJson);
    return expenseService.create(expense);
  }

  @GetMapping(value = REVENUES, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<RevenueResponseJson> getAllRevenues() {
    log.info("List all revenues.");
    return revenueService.getAll();
  }

  @GetMapping(value = EXPENSES, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<ExpenseResponseJson> getAllExpenses() {
    log.info("List all expenses.");
    return expenseService.getAll();
  }

  @GetMapping(value = REVENUES + SEARCH, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<RevenueResponseJson> getRevenueByDescription(@RequestParam("description") String description) {
    log.info("Find revenue by description :: {}", description);
    return revenueService.getExpenseByDescription(description);
  }

  @GetMapping(value = EXPENSES + SEARCH, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<ExpenseResponseJson> getExpenseByDescription(@RequestParam("description") String description) {
    log.info("Find expense by description :: {}", description);
    return expenseService.getExpenseByDescription(description);
  }

  @GetMapping(value = REVENUES + ID, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<RevenueResponseJson> getRevenueBy(@PathVariable final String id) {
    log.info("Find revenue with ID :: {}", id);
    return revenueService.getBy(id);
  }

  @GetMapping(value = EXPENSES + ID, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<ExpenseResponseJson> getExpenseBy(@PathVariable final String id) {
    log.info("Find expense with ID :: {}", id);
    return expenseService.getBy(id);
  }

  @GetMapping(value = EXPENSES + YEAR + MONTH, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ExpenseResponseJson>> getExpenseByYearAndMonth(
      @PathVariable("year") final int year, @PathVariable("month") final int month) {
    log.info("Find expense - Period :: {} / {}", month, year);
    return expenseService.getExpenseByYearAndMonth(month, year);
  }

  @GetMapping(value = REVENUES + YEAR + MONTH, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<RevenueResponseJson>> getRevenueByYearAndMonth(
      @PathVariable("year") final int year, @PathVariable("month") final int month) {
    log.info("Find revenue - Period :: {} / {}", month, year);
    return revenueService.getRevenueByYearAndMonth(month, year);
  }

  @PutMapping(value = REVENUES + ID, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<RevenueResponseJson> updateRevenueBy(@PathVariable final String id,
      @RequestBody @Valid final RevenueRequestJson revenueRequestJson) {
    log.info("Update revenue with ID :: {}", id);
    final Revenue revenue = toRevenue.convert(revenueRequestJson);
    return revenueService.update(id, revenue);
  }

  @PutMapping(value = EXPENSES + ID, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<ExpenseResponseJson> updateExpenseBy(@PathVariable final String id,
      @RequestBody @Valid final ExpenseRequestJson expenseRequestJson) {
    log.info("Update expense with ID :: {}", id);
    final Expense expense = toExpense.convert(expenseRequestJson);
    return expenseService.update(id, expense);
  }

  @DeleteMapping(value = REVENUES + ID)
  public ResponseEntity<Object> deleteRevenueBy(@PathVariable final String id) {
    log.info("Delete revenue with ID :: {}", id);
    return revenueService.delete(id);
  }

  @DeleteMapping(value = EXPENSES + ID)
  public ResponseEntity<Object> deleteExpenseBy(@PathVariable final String id) {
    log.info("Delete expense with ID :: {}", id);
    return expenseService.delete(id);
  }

  @GetMapping(value = RESUME + YEAR + MONTH, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<BudgetResponseJson> getResume(
      @PathVariable("year") final int year, @PathVariable("month") final int month) {
    log.info("Get resume - Period :: {} / {}", month, year);
    return budgetService.getResume(month, year);
  }
}
