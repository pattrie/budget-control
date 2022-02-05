package com.github.pattrie.budgetcontrol.controllers.jsons;

import javax.validation.constraints.Negative;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequestJson {

  @NotBlank
  private String description;

  private String category;

  @NotBlank
  @Negative
  private String value;
}
