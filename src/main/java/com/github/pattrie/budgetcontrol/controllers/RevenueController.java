package com.github.pattrie.budgetcontrol.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.github.pattrie.budgetcontrol.controllers.converters.RevenueRequestJsonToRevenueConverter;
import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueRequestJson;
import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueResponseJson;
import com.github.pattrie.budgetcontrol.domains.Revenue;
import com.github.pattrie.budgetcontrol.usecases.RevenueService;
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
@RequestMapping("/v1/budgets/revenues")
@ApiResponses(value = {@ApiResponse(code = 204, message = "Revenue not found")})
@RequiredArgsConstructor
public class RevenueController {

  public static final String ID = "/{id}";
  public static final String YEAR = "/{year}";
  public static final String MONTH = "/{month}";
  public static final String SEARCH = "/search";

  private final RevenueRequestJsonToRevenueConverter toRevenue;

  private final RevenueService revenueService;

  @ApiOperation(value = "Create Revenue", notes = "Creates a new revenue")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Created a new revenue successfully")})
  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<RevenueResponseJson> createRevenue(
      @RequestBody @Valid final RevenueRequestJson revenueRequestJson) {
    log.info("Creation of revenue :: {}", revenueRequestJson.getDescription());
    final Revenue revenue = toRevenue.convert(revenueRequestJson);
    return revenueService.create(revenue);
  }

  @ApiOperation(value = "Get All Revenue", notes = "List all revenues")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Get all revenues successfully")})
  @GetMapping(produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<RevenueResponseJson> getAllRevenues() {
    log.info("List all revenues.");
    return revenueService.getAll();
  }

  @ApiOperation(value = "Get a Revenue by Description", notes = "Get a revenue by description")
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Get a revenue by description successfully")})
  @GetMapping(value = SEARCH, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<RevenueResponseJson> getRevenueByDescription(
      @RequestParam("description") String description) {
    log.info("Find revenue by description :: {}", description);
    return revenueService.getExpenseByDescription(description);
  }

  @ApiOperation(value = "Get a Revenue by ID", notes = "Get a revenue by id")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Get a revenue by id successfully")})
  @GetMapping(value = ID, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<RevenueResponseJson> getRevenueBy(@PathVariable final String id) {
    log.info("Find revenue with ID :: {}", id);
    return revenueService.getBy(id);
  }

  @ApiOperation(
      value = "Get a Revenue by Year And Month",
      notes = "Get a revenue by year and month")
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Get a revenue by year and month successfully")})
  @GetMapping(value = YEAR + MONTH, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<RevenueResponseJson>> getRevenueByYearAndMonth(
      @PathVariable("year") final int year, @PathVariable("month") final int month) {
    log.info("Find revenue - Period :: {} / {}", month, year);
    return revenueService.getRevenueByYearAndMonth(month, year);
  }

  @ApiOperation(value = "Update a Revenue", notes = "Update a revenue")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Update a expense successfully")})
  @PutMapping(value = ID, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<RevenueResponseJson> updateRevenueBy(
      @PathVariable final String id,
      @RequestBody @Valid final RevenueRequestJson revenueRequestJson) {
    log.info("Update revenue with ID :: {}", id);
    final Revenue revenue = toRevenue.convert(revenueRequestJson);
    return revenueService.update(id, revenue);
  }

  @ApiOperation(value = "Delete a Revenue", notes = "Delete a revenue")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Delete a revenue successfully")})
  @DeleteMapping(value = ID)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Object> deleteRevenueBy(@PathVariable final String id) {
    log.info("Delete revenue with ID :: {}", id);
    return revenueService.delete(id);
  }
}
