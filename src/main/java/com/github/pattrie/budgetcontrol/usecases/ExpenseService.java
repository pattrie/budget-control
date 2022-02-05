package com.github.pattrie.budgetcontrol.usecases;

import com.github.pattrie.budgetcontrol.controllers.jsons.ExpenseResponseJson;
import com.github.pattrie.budgetcontrol.domains.Expense;
import com.github.pattrie.budgetcontrol.gateways.ExpenseGatewayImpl;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseService {

  //TODO P: Improve URI location.
  public static final String LOCATION = "http://localhost:8080/v1/budgets/";
  public static final String WITH_ID = " - with ID :: {}";

  Marker EXPENSE_ALREADY_EXISTS = MarkerFactory.getMarker("Expense already exists");
  Marker EXPENSE_NOT_FOUND = MarkerFactory.getMarker("Expense not found");

  private final ExpenseGatewayImpl expenseGateway;

  private final ModelMapper mapper = new ModelMapper();

  public ResponseEntity<ExpenseResponseJson> create(final Expense expense) {

    final Optional<Expense> optionalExpense = expenseGateway.findByDescriptionAndValue(expense);

    if (optionalExpense.isPresent()) {
      final Expense expenseFound = optionalExpense.get();
      log.info(EXPENSE_ALREADY_EXISTS, EXPENSE_ALREADY_EXISTS + WITH_ID + " :: {}",
          expenseFound.getId(), expenseFound.getDescription());
      return ResponseEntity.ok().body(mapper.map(expenseFound, ExpenseResponseJson.class));
    }

    final ExpenseResponseJson revenueSaved = mapper.map(expenseGateway.save(expense),
        ExpenseResponseJson.class);
    return ResponseEntity.created(URI.create(LOCATION + revenueSaved.getId())).body(revenueSaved);
  }

  public List<ExpenseResponseJson> getAll() {
    return expenseGateway.findAll().stream().map(expense ->
        mapper.map(expense, ExpenseResponseJson.class)).collect(Collectors.toList());
  }

  public ResponseEntity<ExpenseResponseJson> getBy(final String id) {
    final Optional<Expense> revenue = expenseGateway.findBy(id);
    if (revenue.isPresent()) {
      return ResponseEntity.ok().body(mapper.map(revenue.get(), ExpenseResponseJson.class));
    }
    log.info(EXPENSE_NOT_FOUND, EXPENSE_NOT_FOUND + WITH_ID, id);
    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<ExpenseResponseJson> update(final String id, final Expense expense) {
    final Optional<Expense> expenseFound = expenseGateway.findBy(id);
    if (expenseFound.isPresent()) {
      final Expense expenseToUpdate = expenseFound.get();
      log.info("Current expense information :: {}", expenseToUpdate);
      expenseToUpdate.setDescription(expense.getDescription());
      expenseToUpdate.setValue(expense.getValue());
      expenseToUpdate.setCategory(expense.getCategory());
      final Expense expenseUpdate = expenseGateway.save(expenseToUpdate);
      return ResponseEntity.ok().body(mapper.map(expenseUpdate, ExpenseResponseJson.class));
    }

    log.info(EXPENSE_NOT_FOUND, EXPENSE_NOT_FOUND + WITH_ID, id);
    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<Object> delete(final String id) {
    final Optional<Expense> expense = expenseGateway.findBy(id);

    if (expense.isPresent()) {
      expenseGateway.delete(expense.get());
      return ResponseEntity.ok().build();
    }

    log.info(EXPENSE_NOT_FOUND, EXPENSE_NOT_FOUND + WITH_ID, id);
    return ResponseEntity.noContent().build();
  }
}