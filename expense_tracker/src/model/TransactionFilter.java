package model;

import java.util.List;

public interface TransactionFilter {

/**
   * Returns a list of transactions that match this filter’s condition.
   *
   * @param transactions the list of transactions to evaluate
   * @return a list containing only the matching transactions
   */

  List<Transaction> filter(List<Transaction> transactions);
  
}
