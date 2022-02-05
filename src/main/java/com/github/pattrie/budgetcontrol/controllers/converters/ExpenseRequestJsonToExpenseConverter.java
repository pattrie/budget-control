package com.github.pattrie.budgetcontrol.controllers.converters;

import com.github.pattrie.budgetcontrol.controllers.jsons.ExpenseRequestJson;
import com.github.pattrie.budgetcontrol.domains.Expense;
import com.github.pattrie.budgetcontrol.domains.enums.Category;
import java.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ExpenseRequestJsonToExpenseConverter implements Converter<ExpenseRequestJson, Expense> {

  @Override
  public Expense convert(final ExpenseRequestJson expenseRequestJson) {
    return Expense.builder()
        .description(expenseRequestJson.getDescription())
        .date(LocalDateTime.now())
        .value(Long.parseLong(expenseRequestJson.getValue()))
        .category(Category.valueOfBySynonymCategory(expenseRequestJson.getCategory()))
        .build();
  }
}
