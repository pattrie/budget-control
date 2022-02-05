package com.github.pattrie.budgetcontrol.domains;

import com.github.pattrie.budgetcontrol.domains.enums.Category;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Expense {

  private String id;

  private String description;

  private Long value;

  private LocalDateTime date;

  private Category category;
}
