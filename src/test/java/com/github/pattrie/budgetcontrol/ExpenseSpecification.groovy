package com.github.pattrie.budgetcontrol

import com.github.pattrie.budgetcontrol.gateways.ExpenseGateway
import com.github.pattrie.budgetcontrol.usecases.ExpenseService
import spock.lang.Specification

class ExpenseSpecification extends Specification {

    def expenseGateway = Mock(ExpenseGateway)

    def getExpenseService() {
        return new ExpenseService(expenseGateway)
    }

}
