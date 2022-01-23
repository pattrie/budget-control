package com.github.pattrie.budgetcontrol.controllers.jsons;

import javax.validation.constraints.NotBlank;
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
  private String value;
}
