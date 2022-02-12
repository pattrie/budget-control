package com.github.pattrie.budgetcontrol.usecases;

import com.github.pattrie.budgetcontrol.controllers.jsons.BudgetResponseJson;
import com.github.pattrie.budgetcontrol.domains.Expense;
import com.github.pattrie.budgetcontrol.domains.ExpenseCategory;
import com.github.pattrie.budgetcontrol.domains.Revenue;
import com.github.pattrie.budgetcontrol.domains.enums.Category;
import com.github.pattrie.budgetcontrol.gateways.ExpenseGateway;
import com.github.pattrie.budgetcontrol.gateways.RevenueGateway;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    final List<Category> categories = expenseByYearAndMonth.stream()
            .map(Expense::getCategory)
            .distinct()
            .collect(Collectors.toList());

    final BudgetResponseJson budgetResponseJson = BudgetResponseJson.builder()
            .totalRevenue(revenueSum)
            .totalExpense(expenseSum)
            .finalBalance(finalBalance)
            .expenseCategory(getExpenseCategories(expenseByYearAndMonth, categories))
            .build();

    return ResponseEntity.ok(budgetResponseJson);
  }

  private List<ExpenseCategory> getExpenseCategories(final List<Expense> expenseByYearAndMonth,
      final List<Category> categories) {
    final List<ExpenseCategory> categoryList = new ArrayList<>();
    for (Category category: categories) {
      final ExpenseCategory expenseCategory = new ExpenseCategory();
      expenseCategory.setCategory(category);
      final Float totalCategory = expenseByYearAndMonth.stream()
          .filter(expense -> expense.getCategory().equals(category))
          .reduce(0.0f, (total, expense) -> total + expense.getValue(), Float::sum);
      expenseCategory.setTotal(totalCategory);
      categoryList.add(expenseCategory);
    }
    return categoryList;
  }
}
