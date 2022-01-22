package com.github.pattrie.budgetcontrol.controllers.jsons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RevenueResponseJson {

  private String id;

  private String description;

  private String value;

  private String date;
}
