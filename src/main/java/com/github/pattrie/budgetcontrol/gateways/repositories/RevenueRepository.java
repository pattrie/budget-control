package com.github.pattrie.budgetcontrol.gateways.repositories;

import com.github.pattrie.budgetcontrol.domains.Budget;
import com.github.pattrie.budgetcontrol.domains.Revenue;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueRepository extends MongoRepository<Revenue, String> {

  Optional<Budget> findByDescriptionAndValue(String description, Long value);

}
