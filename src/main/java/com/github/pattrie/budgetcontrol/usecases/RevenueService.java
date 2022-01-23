package com.github.pattrie.budgetcontrol.usecases;

import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueResponseJson;
import com.github.pattrie.budgetcontrol.domains.Revenue;
import com.github.pattrie.budgetcontrol.gateways.RevenueGateway;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RevenueService {

  private static final Marker REVENUE_ALREADY_EXISTS = MarkerFactory.getMarker(
      "Revenue already exists");

  private final RevenueGateway revenueGateway;

  private final ModelMapper mapper = new ModelMapper();

  public RevenueResponseJson create(final Revenue revenue) {

    final Optional<Revenue> optionalRevenue = revenueGateway.findByDescriptionAndValue(revenue);

    if (optionalRevenue.isPresent()) {

      final Revenue revenueFound = optionalRevenue.get();
      log.info(REVENUE_ALREADY_EXISTS, REVENUE_ALREADY_EXISTS + " - ID: {} :: {}",
          revenueFound.getId(), revenueFound.getDescription());

      return mapper.map(revenueFound, RevenueResponseJson.class);
    }

    return mapper.map(revenueGateway.save(revenue), RevenueResponseJson.class);
  }
}
