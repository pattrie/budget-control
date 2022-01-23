package com.github.pattrie.budgetcontrol.gateways;

import com.github.pattrie.budgetcontrol.domains.Revenue;
import com.github.pattrie.budgetcontrol.gateways.repositories.RevenueRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RevenueGatewayImpl implements RevenueGateway {

  private final RevenueRepository repository;

  @Override
  public Revenue save(final Revenue revenue) {
    log.info("Saving revenue: {}", revenue);
    return repository.save(revenue);
  }

  @Override
  public Optional<Revenue> findByDescriptionAndValue(final Revenue revenue) {
    return repository.findByDescriptionAndValue(revenue.getDescription(), revenue.getValue());
  }

  @Override
  public List<Revenue> findAll() {
    return repository.findAll();
  }
}
