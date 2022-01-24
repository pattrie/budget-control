package com.github.pattrie.budgetcontrol.usecases;

import com.github.pattrie.budgetcontrol.domains.Budget;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BudgetControlService {

    private final RevenueService revenueService;
    private final ExpenseService expenseService;

    public BudgetControl execute(final Budget budget) {
        if (budget.getValue() > 0) return revenueService;
        else return expenseService;
    }
}
