package model;

import java.util.ArrayList;
import java.util.List;

public class AmountFilter implements TransactionFilter {

  private final double amount;

  public AmountFilter(double amount) {
    this.amount = amount;
  }

  @Override
  public List<Transaction> filter(List<Transaction> transactions) {
    List<Transaction> out = new ArrayList<>();
    for (Transaction t : transactions) {
      if (Double.compare(t.getAmount(), amount) == 0) {
        out.add(t);
      }
    }
    return out;
  }

}
