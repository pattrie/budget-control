package com.github.pattrie.budgetcontrol.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.github.pattrie.budgetcontrol.controllers.converters.RevenueRequestJsonToRevenueConverter;
import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueRequestJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueResponseJson;
import com.github.pattrie.budgetcontrol.domains.Revenue;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/budgets/revenues")
@RequiredArgsConstructor
public class RevenueController {

  public static final String ID = "/{id}";
  public static final String YEAR = "/{year}";
  public static final String MONTH = "/{month}";
  public static final String SEARCH = "/search";

  private final RevenueRequestJsonToRevenueConverter toRevenue;

  private final RevenueService revenueService;

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<RevenueResponseJson> createRevenue(
      @RequestBody @Valid final RevenueRequestJson revenueRequestJson) {
    log.info("Creation of revenue :: {}", revenueRequestJson.getDescription());
    final Revenue revenue = toRevenue.convert(revenueRequestJson);
    return revenueService.create(revenue);
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<RevenueResponseJson> getAllRevenues() {
    log.info("List all revenues.");
    return revenueService.getAll();
  }

  @GetMapping(value = SEARCH, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<RevenueResponseJson> getRevenueByDescription(@RequestParam("description") String description) {
    log.info("Find revenue by description :: {}", description);
    return revenueService.getExpenseByDescription(description);
  }

  @GetMapping(value = ID, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<RevenueResponseJson> getRevenueBy(@PathVariable final String id) {
    log.info("Find revenue with ID :: {}", id);
    return revenueService.getBy(id);
  }

  @GetMapping(value = YEAR + MONTH, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<RevenueResponseJson>> getRevenueByYearAndMonth(
      @PathVariable("year") final int year, @PathVariable("month") final int month) {
    log.info("Find revenue - Period :: {} / {}", month, year);
    return revenueService.getRevenueByYearAndMonth(month, year);
  }

  @PutMapping(value = ID, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<RevenueResponseJson> updateRevenueBy(@PathVariable final String id,
      @RequestBody @Valid final RevenueRequestJson revenueRequestJson) {
    log.info("Update revenue with ID :: {}", id);
    final Revenue revenue = toRevenue.convert(revenueRequestJson);
    return revenueService.update(id, revenue);
  }

  @DeleteMapping(value = ID)
  public ResponseEntity<Object> deleteRevenueBy(@PathVariable final String id) {
    log.info("Delete revenue with ID :: {}", id);
    return revenueService.delete(id);
  }
}