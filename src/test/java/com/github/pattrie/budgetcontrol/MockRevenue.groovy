package com.github.pattrie.budgetcontrol

import com.github.pattrie.budgetcontrol.domains.Revenue

import java.time.LocalDateTime

class MockRevenue {

    static Revenue VALID() {
        return Revenue.builder()
                .id("id")
                .date(LocalDateTime.now())
                .description("Salary")
                .value(Long.parseLong('500000'))
                .build()
    }
}