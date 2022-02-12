package com.github.pattrie.budgetcontrol.controllers.jsons;

import com.github.pattrie.budgetcontrol.domains.ExpenseCategory;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetResponseJson {

  private Long totalRevenue;

  private Long totalExpense;

  private Long finalBalance;

  private List<ExpenseCategory> expenseCategory;

}
