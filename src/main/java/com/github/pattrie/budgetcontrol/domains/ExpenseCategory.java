package com.github.pattrie.budgetcontrol.domains;

import com.github.pattrie.budgetcontrol.domains.enums.Category;
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
public class ExpenseCategory {

  private Category category;

  private Float total;

}