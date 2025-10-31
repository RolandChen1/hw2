package model;

import java.util.ArrayList;
import java.util.List;

public class CategoryFilter implements TransactionFilter {

  private final String category;

  public CategoryFilter(String category) {
    this.category = category;
  }

  @Override
  public List<Transaction> filter(List<Transaction> transactions) {
    List<Transaction> out = new ArrayList<>();
    if (category == null) return out;
    for (Transaction t : transactions) {
      if (t.getCategory() != null && t.getCategory().equalsIgnoreCase(category)) {
        out.add(t);
      }
    }
    return out;
  }

}
