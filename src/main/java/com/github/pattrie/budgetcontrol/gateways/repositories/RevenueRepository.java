package com.github.pattrie.budgetcontrol.gateways.repositories;

import com.github.pattrie.budgetcontrol.domains.Revenue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueRepository extends MongoRepository<Revenue, String> {



}
