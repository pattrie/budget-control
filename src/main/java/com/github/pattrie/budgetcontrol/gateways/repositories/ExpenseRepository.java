package com.github.pattrie.budgetcontrol.gateways.repositories;

import com.github.pattrie.budgetcontrol.domains.Expense;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

  Optional<Expense> findByDescriptionAndValue(String description, Long value);

}
