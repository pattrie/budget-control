package com.github.pattrie.budgetcontrol

import com.github.pattrie.budgetcontrol.domains.Expense
import com.github.pattrie.budgetcontrol.domains.enums.Category

import java.time.LocalDateTime

class MockExpense {

    static Expense VALID() {
        return Expense.builder()
                .id("id")
                .date(LocalDateTime.now())
                .description("Expenses")
                .value(Long.parseLong('500000'))
                .category(Category.EDUCATION)
                .build()
    }
}