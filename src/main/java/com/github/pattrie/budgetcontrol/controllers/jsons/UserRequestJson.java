package com.github.pattrie.budgetcontrol.controllers.jsons;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class UserRequestJson {

  private String name;

  @NotBlank
  private String email;

  @NotBlank
  private String password;

  public UsernamePasswordAuthenticationToken converter() {
    return new UsernamePasswordAuthenticationToken(this.email, this.password);
  }
}
