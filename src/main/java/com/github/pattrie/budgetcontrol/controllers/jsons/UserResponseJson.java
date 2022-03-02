package com.github.pattrie.budgetcontrol.controllers.jsons;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseJson {

  private String id;

  private String name;

  private String email;
}
