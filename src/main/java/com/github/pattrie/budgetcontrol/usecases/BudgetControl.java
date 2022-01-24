package com.github.pattrie.budgetcontrol.usecases;

import com.github.pattrie.budgetcontrol.controllers.jsons.BudgetResponseJson;
import com.github.pattrie.budgetcontrol.domains.Budget;
import java.util.List;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.http.ResponseEntity;

public interface BudgetControl {

  //TODO P: Improve URI location.
  String LOCATION = "http://localhost:8080/v1/budgets/";

  Marker BUDGET_ALREADY_EXISTS = MarkerFactory.getMarker("Budget already exists");

  Marker BUDGET_NOT_FOUND = MarkerFactory.getMarker("Budget not found");

  ResponseEntity<BudgetResponseJson> create(final Budget budget);

  List<BudgetResponseJson> getAll();

  ResponseEntity<BudgetResponseJson> getBy(final String id);

  ResponseEntity<BudgetResponseJson> update(final String id, final Budget budget);

  ResponseEntity<Object> delete(final String id);
}
