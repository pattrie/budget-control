package com.github.pattrie.budgetcontrol.gateways.repositories;

import com.github.pattrie.budgetcontrol.domains.Revenue;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueRepository extends MongoRepository<Revenue, String> {

  Optional<Revenue> findByDescriptionAndValue(String description, Long value);

  Optional<Revenue> findByDescription(String description);

  @Query("{'date': {$gte: ?0, $lte:?1 }}")
  List<Revenue> findByDate(LocalDateTime initialDate, LocalDateTime finalDate);
}
