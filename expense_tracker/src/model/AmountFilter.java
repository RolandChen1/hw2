package model;

import java.util.ArrayList;
import java.util.List;

import controller.InputValidation;
/**
 * Filters transactions by a specific amount.
 */
public class AmountFilter implements TransactionFilter {

  private final double amount;
  /**
   * Creates a filter for the specified amount.
   *
   * @param amount the amount to filter by
   */

  public AmountFilter(double amount) {
    this.amount = amount;
  }

    /**
   * Returns a list of transactions whose amount matches
   * the specified value.
   *
   * @param transactions the list of transactions to filter
   * @return a list of transactions matching the amount
   */

  @Override
  public List<Transaction> filter(List<Transaction> transactions) {
    List<Transaction> out = new ArrayList<>();

    // Validate the amount before filtering
    if (!InputValidation.isValidAmount(amount)) {
      return out; // return empty list if invalid amount
    }

    for (Transaction t : transactions) {
      if (Double.compare(t.getAmount(), amount) == 0) {
        out.add(t);
      }
    }

    return out;
  }
}
