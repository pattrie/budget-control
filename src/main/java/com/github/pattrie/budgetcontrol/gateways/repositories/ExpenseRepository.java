package com.github.pattrie.budgetcontrol.gateways.repositories;

import com.github.pattrie.budgetcontrol.domains.Expense;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

  Optional<Expense> findByDescriptionAndValue(String description, Long value);

  List<Expense> findByDescription(String description);

  @Query("{'date': {$gte: ?0, $lte:?1 }}")
  List<Expense> findByDate(LocalDateTime initialDate, LocalDateTime finalDate);

}
