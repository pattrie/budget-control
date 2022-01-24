package com.github.pattrie.budgetcontrol.domains;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

  private String id;

  private String description;

  private Long value;

  private LocalDateTime date;

}
