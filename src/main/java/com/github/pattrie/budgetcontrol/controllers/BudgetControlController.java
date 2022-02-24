package com.github.pattrie.budgetcontrol.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.github.pattrie.budgetcontrol.controllers.jsons.BudgetResponseJson;
import com.github.pattrie.budgetcontrol.usecases.BudgetControlService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/budgets")
@RequiredArgsConstructor
public class BudgetControlController {

  public static final String YEAR = "/{year}";
  public static final String MONTH = "/{month}";
  public static final String RESUME = "/summary";

  private final BudgetControlService budgetService;

  @ApiOperation(value = "Budget Summary", notes = "Get the budget summary")
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Get the budget summary successfully")})
  @GetMapping(value = RESUME + YEAR + MONTH, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BudgetResponseJson> getSummary(
      @PathVariable("year") final int year, @PathVariable("month") final int month) {
    log.info("Get summary - Period :: {} / {}", month, year);
    return budgetService.getResume(month, year);
  }
}
