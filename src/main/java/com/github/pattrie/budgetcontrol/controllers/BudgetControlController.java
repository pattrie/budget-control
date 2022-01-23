package com.github.pattrie.budgetcontrol.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.github.pattrie.budgetcontrol.controllers.converters.RevenueRequestJsonToRevenueConverter;
import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueRequestJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueResponseJson;
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

  private final RevenueRequestJsonToRevenueConverter converter;

  private final RevenueService revenueService;

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<RevenueResponseJson> create(
      @RequestBody @Valid final RevenueRequestJson revenueRequestJson) {
    log.info("Creation of Revenue :: {}", revenueRequestJson.getDescription());
    return revenueService.create(converter.convert(revenueRequestJson));
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<RevenueResponseJson> getAll() {
    log.info("List all revenues.");
    return revenueService.getAll();
  }

  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<RevenueResponseJson> getBy(@PathVariable final String id) {
    log.info("Find revenue with ID :: {}", id);
    return revenueService.getBy(id);
  }

  @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<RevenueResponseJson> updateBy(@PathVariable final String id,
      @RequestBody @Valid final RevenueRequestJson revenueRequestJson) {
    log.info("Update revenue with ID :: {}", id);
    return revenueService.update(id, converter.convert(revenueRequestJson));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Object> deleteBy(@PathVariable final String id) {
    log.info("Delete revenue with ID :: {}", id);
    return revenueService.delete(id);
  }
}
