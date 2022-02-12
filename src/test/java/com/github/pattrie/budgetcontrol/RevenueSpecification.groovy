package com.github.pattrie.budgetcontrol

import com.github.pattrie.budgetcontrol.gateways.RevenueGateway
import com.github.pattrie.budgetcontrol.usecases.RevenueService
import spock.lang.Specification

class RevenueSpecification extends Specification {

    def revenueGateway = Mock(RevenueGateway)

    def getRevenueService() {
        return new RevenueService(revenueGateway)
    }
}