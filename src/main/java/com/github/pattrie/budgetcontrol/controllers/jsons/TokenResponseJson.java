package com.github.pattrie.budgetcontrol.controllers.jsons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class TokenResponseJson {

  private String token;
  private String type;
}
