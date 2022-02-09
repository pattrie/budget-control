package com.github.pattrie.budgetcontrol.usecases;

import com.github.pattrie.budgetcontrol.controllers.jsons.BudgetResponseJson;
import com.github.pattrie.budgetcontrol.domains.Expense;
import com.github.pattrie.budgetcontrol.domains.Revenue;
import com.github.pattrie.budgetcontrol.gateways.ExpenseGateway;
import com.github.pattrie.budgetcontrol.gateways.RevenueGateway;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetControlService {

  private final RevenueGateway revenueGateway;
  private final ExpenseGateway expenseGateway;

  public ResponseEntity<BudgetResponseJson> getResume(int month, int year) {

    final LocalDate initialDate = LocalDate.of(year, month, 1);
    final LocalDate finalDate = initialDate.withDayOfMonth(initialDate.lengthOfMonth());

    LocalDateTime yearMonthInitialDate = LocalDateTime.of(initialDate, LocalDateTime.MIN.toLocalTime());
    LocalDateTime yearMonthFinalDate = LocalDateTime.of(finalDate, LocalDateTime.MIN.toLocalTime());

    final List<Revenue> revenueByYearAndMonth = revenueGateway.findByYearAndMonth(
        yearMonthInitialDate, yearMonthFinalDate);

    long revenueSum = 0;
    if (!revenueByYearAndMonth.isEmpty()) {
      revenueSum = revenueByYearAndMonth.stream().mapToLong(Revenue::getValue).sum();
    }

    final List<Expense> expenseByYearAndMonth = expenseGateway.findByYearAndMonth(
        yearMonthInitialDate, yearMonthFinalDate);

    long expenseSum = 0;
    if (!expenseByYearAndMonth.isEmpty()) {
      expenseSum = expenseByYearAndMonth.stream().mapToLong(Expense::getValue).sum();
    }

    final long finalBalance = revenueSum + expenseSum;

    final BudgetResponseJson budgetResponseJson = BudgetResponseJson.builder().totalRevenue(revenueSum)
        .totalExpense(expenseSum).finalBalance(finalBalance).build();

    return ResponseEntity.ok(budgetResponseJson);
  }
}
