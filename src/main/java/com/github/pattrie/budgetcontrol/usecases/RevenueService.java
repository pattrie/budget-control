package com.github.pattrie.budgetcontrol.usecases;

import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueResponseJson;
import com.github.pattrie.budgetcontrol.domains.Revenue;
import com.github.pattrie.budgetcontrol.gateways.RevenueGateway;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevenueService {

  private final RevenueGateway revenueGateway;

  private final ModelMapper mapper = new ModelMapper();

  public RevenueResponseJson create(final Revenue revenue) {

    final Revenue save = revenueGateway.save(revenue);

    return mapper.map(save, RevenueResponseJson.class);
  }
}
