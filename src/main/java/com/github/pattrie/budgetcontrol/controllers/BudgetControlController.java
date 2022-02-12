package com.github.pattrie.budgetcontrol.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.github.pattrie.budgetcontrol.controllers.jsons.BudgetResponseJson;
import com.github.pattrie.budgetcontrol.usecases.BudgetControlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/budgets")
@RequiredArgsConstructor
public class BudgetControlController {

  public static final String YEAR = "/{year}";
  public static final String MONTH = "/{month}";
  public static final String RESUME = "/resume";

  private final BudgetControlService budgetService;

  @GetMapping(value = RESUME + YEAR + MONTH, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<BudgetResponseJson> getResume(
      @PathVariable("year") final int year, @PathVariable("month") final int month) {
    log.info("Get resume - Period :: {} / {}", month, year);
    return budgetService.getResume(month, year);
  }
}
