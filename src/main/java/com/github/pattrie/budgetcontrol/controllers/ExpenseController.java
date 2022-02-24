package com.github.pattrie.budgetcontrol.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.github.pattrie.budgetcontrol.controllers.converters.ExpenseRequestJsonToExpenseConverter;
import com.github.pattrie.budgetcontrol.controllers.jsons.ExpenseRequestJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.ExpenseResponseJson;
import com.github.pattrie.budgetcontrol.domains.Expense;
import com.github.pattrie.budgetcontrol.usecases.ExpenseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

  @ApiOperation(value = "Create Expense", notes = "Creates a new expense")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Created a new expense successfully")})
  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<ExpenseResponseJson> createExpense(
      @RequestBody @Valid final ExpenseRequestJson expenseRequestJson) {
    log.info("Creation of expense :: {}", expenseRequestJson.getDescription());
    final Expense expense = toExpense.convert(expenseRequestJson);
    return expenseService.create(expense);
  }

  @ApiOperation(value = "Get All Expense", notes = "List all expenses")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Get all expenses successfully")})
  @GetMapping(produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<ExpenseResponseJson> getAllExpenses() {
    log.info("List all expenses.");
    return expenseService.getAll();
  }

  @ApiOperation(value = "Get an Expense by Description", notes = "Get an expense by description")
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Get an expense by description successfully")})
  @GetMapping(value = SEARCH, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<ExpenseResponseJson> getExpenseByDescription(
      @RequestParam("description") String description) {
    log.info("Find expense by description :: {}", description);
    return expenseService.getExpenseByDescription(description);
  }

  @ApiOperation(value = "Get an Expense by ID", notes = "Get an expense by id")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Get an expense by id successfully")})
  @GetMapping(value = ID, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<ExpenseResponseJson> getExpenseBy(@PathVariable final String id) {
    log.info("Find expense with ID :: {}", id);
    return expenseService.getBy(id);
  }

  @ApiOperation(
      value = "Get an Expense by Year And Month",
      notes = "Get an expense by year and month")
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Get an expense by year and month successfully")})
  @GetMapping(value = YEAR + MONTH, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<ExpenseResponseJson>> getExpenseByYearAndMonth(
      @PathVariable("year") final int year, @PathVariable("month") final int month) {
    log.info("Find expense - Period :: {} / {}", month, year);
    return expenseService.getExpenseByYearAndMonth(month, year);
  }

  @ApiOperation(value = "Update an Expense", notes = "Update an expense")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Expense update successfully")})
  @PutMapping(value = ID, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<ExpenseResponseJson> updateExpenseBy(
      @PathVariable final String id,
      @RequestBody @Valid final ExpenseRequestJson expenseRequestJson) {
    log.info("Update expense with ID :: {}", id);
    final Expense expense = toExpense.convert(expenseRequestJson);
    return expenseService.update(id, expense);
  }

  @ApiOperation(value = "Delete an Expense", notes = "Delete an expense")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Delete an expense Successfully")})
  @DeleteMapping(value = ID)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Object> deleteExpenseBy(@PathVariable final String id) {
    log.info("Delete expense with ID :: {}", id);
    return expenseService.delete(id);
  }
}
