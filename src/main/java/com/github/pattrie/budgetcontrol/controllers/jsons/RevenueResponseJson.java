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
public class RevenueResponseJson {

  private String id;

  private String description;

  private String value;

  private String date;
}
