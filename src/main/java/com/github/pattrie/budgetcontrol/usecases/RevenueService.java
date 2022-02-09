package com.github.pattrie.budgetcontrol.usecases;

import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueResponseJson;
import com.github.pattrie.budgetcontrol.domains.Revenue;
import com.github.pattrie.budgetcontrol.gateways.RevenueGateway;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class RevenueService {

  //TODO P: Improve URI location.
  public static final String LOCATION = "http://localhost:8080/v1/budgets/";
  public static final String WITH_ID = " - with ID :: {}";

  Marker REVENUE_ALREADY_EXISTS = MarkerFactory.getMarker("Revenue already exists");
  Marker REVENUE_NOT_FOUND = MarkerFactory.getMarker("Revenue not found");

  private final RevenueGateway revenueGateway;

  private final ModelMapper mapper = new ModelMapper();

  public ResponseEntity<RevenueResponseJson> create(final Revenue revenue) {

    final Optional<Revenue> optionalRevenue = revenueGateway.findByDescriptionAndValue(revenue);

    if (optionalRevenue.isPresent()) {
      final Revenue revenueFound = optionalRevenue.get();
      log.info(REVENUE_ALREADY_EXISTS, REVENUE_ALREADY_EXISTS + WITH_ID + " :: {}",
          revenueFound.getId(), revenueFound.getDescription());
      return ResponseEntity.ok().body(mapper.map(revenueFound, RevenueResponseJson.class));
    }

    final RevenueResponseJson revenueSaved = mapper.map(revenueGateway.save(revenue),
        RevenueResponseJson.class);
    return ResponseEntity.created(URI.create(LOCATION + revenueSaved.getId())).body(revenueSaved);
  }

  public List<RevenueResponseJson> getAll() {
    return revenueGateway.findAll().stream().map(revenue ->
        mapper.map(revenue, RevenueResponseJson.class)).collect(Collectors.toList());
  }

  public ResponseEntity<RevenueResponseJson> getBy(final String id) {
    final Optional<Revenue> revenue = revenueGateway.findBy(id);
    if (revenue.isPresent()) {
      return ResponseEntity.ok().body(mapper.map(revenue.get(), RevenueResponseJson.class));
    }
    log.info(REVENUE_NOT_FOUND, REVENUE_NOT_FOUND + WITH_ID, id);
    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<RevenueResponseJson> update(final String id, final Revenue revenue) {
    final Optional<Revenue> revenueFound = revenueGateway.findBy(id);
    if (revenueFound.isPresent()) {
      final Revenue revenueToUpdate = revenueFound.get();
      log.info("Current revenue information :: {}", revenueToUpdate);
      revenueToUpdate.setDescription(revenue.getDescription());
      revenueToUpdate.setValue(revenue.getValue());
      final Revenue revenueUpdate = revenueGateway.save(revenueToUpdate);
      return ResponseEntity.ok().body(mapper.map(revenueUpdate, RevenueResponseJson.class));
    }

    log.info(REVENUE_NOT_FOUND, REVENUE_NOT_FOUND + WITH_ID, id);
    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<Object> delete(final String id) {
    final Optional<Revenue> revenue = revenueGateway.findBy(id);

    if (revenue.isPresent()) {
      revenueGateway.delete(revenue.get());
      return ResponseEntity.ok().build();
    }

    log.info(REVENUE_NOT_FOUND, REVENUE_NOT_FOUND + WITH_ID, id);
    return ResponseEntity.noContent().build();
  }

  public List<RevenueResponseJson> getExpenseByDescription(String description) {
    return revenueGateway.findByDescription(description).stream().map(revenue ->
        mapper.map(revenue, RevenueResponseJson.class)).collect(Collectors.toList());
  }

  public ResponseEntity<List<RevenueResponseJson>> getRevenueByYearAndMonth(int month, int year) {
    final LocalDate initialDate = LocalDate.of(year, month, 1);
    final LocalDate finalDate = initialDate.withDayOfMonth(initialDate.lengthOfMonth());

    LocalDateTime yearMonthInitialDate = LocalDateTime.of(initialDate, LocalDateTime.MIN.toLocalTime());
    LocalDateTime yearMonthFinalDate = LocalDateTime.of(finalDate, LocalDateTime.MIN.toLocalTime());

    final List<RevenueResponseJson> revenueResponseJsons =
        revenueGateway.findByYearAndMonth(yearMonthInitialDate, yearMonthFinalDate).stream()
            .map(revenue -> mapper.map(revenue, RevenueResponseJson.class))
            .collect(Collectors.toList());

    if (revenueResponseJsons.isEmpty()) {
      log.info(REVENUE_NOT_FOUND, "List of " + REVENUE_NOT_FOUND + " - Period :: {} / {}", month, year);
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(revenueResponseJsons);
  }
}