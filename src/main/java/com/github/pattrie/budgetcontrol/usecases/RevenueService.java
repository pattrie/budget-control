package com.github.pattrie.budgetcontrol.usecases;

import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueResponseJson;
import com.github.pattrie.budgetcontrol.domains.Revenue;
import com.github.pattrie.budgetcontrol.gateways.RevenueGateway;
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
public class RevenueService {

  //TODO P: Improve URI location.
  private static final String LOCATION = "http://localhost:8080/v1/budgets/";

  private static final Marker REVENUE_ALREADY_EXISTS = MarkerFactory.getMarker(
      "Revenue already exists");

  private final RevenueGateway revenueGateway;

  private final ModelMapper mapper = new ModelMapper();

  public ResponseEntity<RevenueResponseJson> create(final Revenue revenue) {

    final Optional<Revenue> optionalRevenue = revenueGateway.findByDescriptionAndValue(revenue);

    if (optionalRevenue.isPresent()) {

      final Revenue revenueFound = optionalRevenue.get();
      log.info(REVENUE_ALREADY_EXISTS, REVENUE_ALREADY_EXISTS + " - ID: {} :: {}",
          revenueFound.getId(), revenueFound.getDescription());

      return ResponseEntity.ok().body(mapper.map(revenueFound, RevenueResponseJson.class));
    }

    final RevenueResponseJson revenueSaved = mapper.map(revenueGateway.save(revenue),
        RevenueResponseJson.class);

    return ResponseEntity.created(URI.create(LOCATION + revenueSaved.getId())).body(revenueSaved);
  }

  public List<RevenueResponseJson> gettAll() {
    return revenueGateway.findAll().stream().map(revenue ->
        mapper.map(revenue, RevenueResponseJson.class)).collect(Collectors.toList());
  }

  public ResponseEntity<RevenueResponseJson> getBy(final String id) {
    final Optional<Revenue> revenue = revenueGateway.findBy(id);
    if (revenue.isPresent()) {
      final RevenueResponseJson revenueResponseJson = mapper.map(revenue.get(), RevenueResponseJson.class);
      return ResponseEntity.ok().body(revenueResponseJson);
    }
    return ResponseEntity.noContent().build();
  }
}
