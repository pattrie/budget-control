package com.github.pattrie.budgetcontrol.usecases

import com.github.pattrie.budgetcontrol.MockRevenue

import com.github.pattrie.budgetcontrol.RevenueSpecification
import com.github.pattrie.budgetcontrol.domains.Revenue
import org.springframework.http.HttpStatus

class RevenueServiceSpec extends RevenueSpecification {

    def useCase = getRevenueService()

    def "should create a new Revenue"() {
        given: "A new Revenue"
        def revenue = MockRevenue.VALID()

        and: "The RevenueGateway cannot find a Revenue"
        1 * revenueGateway.findByDescriptionAndValue(_ as Revenue) >> Optional.empty()

        and: "The RevenueGateway saves the new Revenue"
        1 * revenueGateway.save(revenue) >> revenue

        when: "I call the useCase"
        def responseEntity = useCase.create(revenue)

        then: "ResponseEntity status must be CREATED"
        responseEntity.statusCode == HttpStatus.CREATED
    }

    def "shouldn't create a Revenue existent"() {
        given: "A Revenue"
        def revenue = MockRevenue.VALID()

        and: "The RevenueGateway finds this Revenue"
        1 * revenueGateway.findByDescriptionAndValue(_ as Revenue) >> Optional.of(revenue)

        and: "The RevenueGateway doesn't save the Revenue"
        0 * revenueGateway.save(revenue)

        when: "I call the useCase"
        def responseEntity = useCase.create(revenue)

        then: "ResponseEntity status must be OK"
        responseEntity.statusCode == HttpStatus.OK
    }
}