package controller;

import view.ExpenseTrackerView;

import java.util.List;



import model.ExpenseTrackerModel;
import model.Transaction;
import model.TransactionFilter;
import model.AmountFilter;
import model.CategoryFilter;
public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    // Set up view event handlers
  }

  //Apply a filter to the transactions and update the view.
  //Returns true if filter applied successfully, false on invalid input.
  public boolean applyFilter(String filterType, String filterValue) {
    List<Transaction> transactions = model.getTransactions();

    if (filterType == null || filterType.equalsIgnoreCase("none") || filterType.isEmpty()) {
      view.refreshTable(transactions);
      return true;
    }

    if (filterType.equalsIgnoreCase("amount")) {
      double amount;
      try {
        amount = Double.parseDouble(filterValue);
      } catch (NumberFormatException e) {
        return false;
      }

      if (!InputValidation.isValidAmount(amount)) {
        return false;
      }

      TransactionFilter f = new AmountFilter(amount);
      List<Transaction> filtered = f.filter(transactions);
      view.refreshTable(filtered);
      return true;
    }

    if (filterType.equalsIgnoreCase("category")) {
      if (!InputValidation.isValidCategory(filterValue)) {
        return false;
      }
      TransactionFilter f = new CategoryFilter(filterValue);
      List<Transaction> filtered = f.filter(transactions);
      view.refreshTable(filtered);
      return true;
    }

    return false;
  }

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions);

  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    return true;
  }
  
  // Other controller methods
}