package com.github.pattrie.budgetcontrol.controllers.converters;

import com.github.pattrie.budgetcontrol.controllers.jsons.BudgetRequestJson;
import com.github.pattrie.budgetcontrol.domains.Budget;
import java.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BudgetRequestJsonToBudgetConverter implements Converter<BudgetRequestJson, Budget> {

  @Override
  public Budget convert(final BudgetRequestJson budgetRequestJson) {
    return Budget.builder()
        .description(budgetRequestJson.getDescription())
        .date(LocalDateTime.now())
        .value(Long.parseLong(budgetRequestJson.getValue()))
        .build();
  }
}
