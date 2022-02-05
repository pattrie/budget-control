package com.github.pattrie.budgetcontrol.controllers.converters;

import com.github.pattrie.budgetcontrol.controllers.jsons.RevenueRequestJson;
import com.github.pattrie.budgetcontrol.domains.Revenue;
import java.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RevenueRequestJsonToRevenueConverter implements Converter<RevenueRequestJson, Revenue> {

  @Override
  public Revenue convert(final RevenueRequestJson revenueRequestJson) {
    return Revenue.builder()
        .description(revenueRequestJson.getDescription())
        .date(LocalDateTime.now())
        .value(Long.parseLong(revenueRequestJson.getValue()))
        .build();
  }
}
