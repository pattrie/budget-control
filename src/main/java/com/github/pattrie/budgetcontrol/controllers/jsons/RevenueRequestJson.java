package com.github.pattrie.budgetcontrol.controllers.jsons;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RevenueRequestJson {

  @NotBlank
  private String description;

  @NotBlank
  @Positive
  private String value;
}
