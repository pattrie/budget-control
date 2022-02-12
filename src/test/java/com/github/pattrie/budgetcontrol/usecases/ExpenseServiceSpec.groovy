package com.github.pattrie.budgetcontrol.usecases

import com.github.pattrie.budgetcontrol.ExpenseSpecification
import com.github.pattrie.budgetcontrol.MockExpense
import com.github.pattrie.budgetcontrol.domains.Expense
import org.springframework.http.HttpStatus

class ExpenseServiceSpec extends ExpenseSpecification {

    def useCase = getExpenseService()

    def "should create a new Expense"() {
        given: "A new Expense"
        def expense = MockExpense.VALID()

        and: "The ExpenseGateway cannot find a Expense"
        1 * expenseGateway.findByDescriptionAndValue(_ as Expense) >> Optional.empty()

        and: "The ExpenseGateway saves the new Expense"
        1 * expenseGateway.save(expense) >> expense

        when: "I call the useCase"
        def responseEntity = useCase.create(expense)

        then: "ResponseEntity status must be CREATED"
        responseEntity.statusCode == HttpStatus.CREATED
    }

    def "shouldn't create a Expense existent"() {
        given: "A Expense"
        def expense = MockExpense.VALID()

        and: "The ExpenseGateway finds this Expense"
        1 * expenseGateway.findByDescriptionAndValue(_ as Expense) >> Optional.of(expense)

        and: "The ExpenseGateway doesn't save the Expense"
        0 * expenseGateway.save(expense)

        when: "I call the useCase"
        def responseEntity = useCase.create(expense)

        then: "ResponseEntity status must be OK"
        responseEntity.statusCode == HttpStatus.OK
    }
}