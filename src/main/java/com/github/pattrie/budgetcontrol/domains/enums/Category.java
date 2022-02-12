package com.github.pattrie.budgetcontrol.domains.enums;

import java.util.Set;
import java.util.stream.Stream;
import lombok.Getter;

public enum Category {
  FOOD("food"),
  HEALTH("health"),
  HOME("home"),
  TRANSPORTATION("transportation"),
  EDUCATION("education"),
  RECREATION("recreation"),
  UNFORESEEN("unforeseen"),
  OTHERS("others");

  @Getter private final String[] synonymCategories;

  Category(String... synonymDescriptions) {
    this.synonymCategories = synonymDescriptions;
  }

  public static Category valueOfBySynonymCategory(String category) {
    return Stream.of(values())
        .filter(item -> Set.of(item.getSynonymCategories()).contains(category == null ? "" : category))
        .findAny().orElse(Category.OTHERS);
  }
}
