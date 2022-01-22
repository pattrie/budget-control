package com.github.pattrie.budgetcontrol.gateways;

import com.github.pattrie.budgetcontrol.domains.Revenue;

public interface RevenueGateway {

  Revenue save(final Revenue revenue);
}
