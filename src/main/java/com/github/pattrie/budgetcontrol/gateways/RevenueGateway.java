package com.github.pattrie.budgetcontrol.gateways;

import com.github.pattrie.budgetcontrol.domains.Revenue;
import java.util.Optional;

public interface RevenueGateway {

  Revenue save(final Revenue revenue);

  Optional<Revenue> findByDescriptionAndValue(final Revenue revenue);
}
