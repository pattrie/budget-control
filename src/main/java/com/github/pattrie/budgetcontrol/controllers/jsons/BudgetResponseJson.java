package com.github.pattrie.budgetcontrol.controllers.jsons;

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

}
