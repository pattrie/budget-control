package com.github.pattrie.budgetcontrol.domains;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Document
public class Profile implements GrantedAuthority {

  private Long id;
  private String name;

  @Override
  public String getAuthority() {
    return this.name;
  }
}
