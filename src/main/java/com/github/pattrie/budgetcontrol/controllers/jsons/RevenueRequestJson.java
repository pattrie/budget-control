package com.github.pattrie.budgetcontrol.controllers.jsons;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RevenueRequestJson {

  @NotBlank
  private String description;

  @NotBlank
  private String value;

}
