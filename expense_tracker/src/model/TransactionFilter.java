package model;

import java.util.List;

public interface TransactionFilter {

//Return a list containing only the transactions that match this filter.

  List<Transaction> filter(List<Transaction> transactions);
  
}
