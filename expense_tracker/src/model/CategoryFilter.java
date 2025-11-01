package model;

import java.util.ArrayList;
import java.util.List;

import controller.InputValidation;

/**
 * Filters transactions by category name.
 */
public class CategoryFilter implements TransactionFilter {

  private final String category;
  /**
   * Creates a filter for the specified category.
   *
   * @param category the category to filter by
   */
  public CategoryFilter(String category) {
    this.category = category;
  }
  /**
   * Returns a list of transactions whose category matches
   * the specified name (case-insensitive).
   *
   * @param transactions the list of transactions to filter
   * @return a list of transactions matching the category
   */
  @Override
  public List<Transaction> filter(List<Transaction> transactions) {
    List<Transaction> out = new ArrayList<>();

    // Validate the category before filtering
    if (!InputValidation.isValidCategory(category)) {
      return out; // return empty list if invalid category
    }

    for (Transaction t : transactions) {
      if (t.getCategory() != null && t.getCategory().equalsIgnoreCase(category)) {
        out.add(t);
      }
    }

    return out;
  }
}
