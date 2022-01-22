package com.github.pattrie.budgetcontrol.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.github.pattrie.budgetcontrol.controllers.converters.RevenueJsonToRevenueConverter;
import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueRequestJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueResponseJson;
import com.github.pattrie.budgetcontrol.usecases.RevenueService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/budgets")
@Slf4j
public class BudgetControlController {

  private final RevenueJsonToRevenueConverter converter;

  private final RevenueService revenueService;

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public RevenueResponseJson create(
      @RequestBody @Valid final RevenueRequestJson revenueRequestJson) {

    log.info("Creation of Revenue: {}", revenueRequestJson.getValue());

    return revenueService.create(converter.convert(revenueRequestJson));
  }
}
