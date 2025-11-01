// package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import model.AmountFilter;
import model.CategoryFilter;
import view.ExpenseTrackerView;


public class TestExample {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private ExpenseTrackerController controller;

  @Before
  public void setup() {
    model = new ExpenseTrackerModel();
    view = new ExpenseTrackerView();
    controller = new ExpenseTrackerController(model, view);
  }

    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }
    


    @Test
    public void testAddTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add a transaction
        assertTrue(controller.addTransaction(50.00, "food"));
    
        // Post-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());
    
        // Check the contents of the list
        assertEquals(50.00, getTotalCost(), 0.01);
    }

    @Test
    public void testAddValidTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add a transaction
        assertTrue(controller.addTransaction(50.00, "food"));
    
        // Post-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());
    
        // Check the contents of the list
        assertEquals(50.00, getTotalCost(), 0.01);

        // Verify the details of the added transaction
        Transaction addedTransaction = model.getTransactions().get(0);
        assertEquals("food", addedTransaction.getCategory());
        assertEquals(50.00, addedTransaction.getAmount(), 0.01);
    }

    @Test
    public void testInvalidAmountTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add a transaction
        // Should be rejected due to invalid amount
        assertEquals(false, controller.addTransaction(-50.00, "food"));

        System.out.println("Transaction failed to add due to invalid amount");

        // Post-condition: List of transactions remains empty
        assertEquals(0, model.getTransactions().size());

        // Total cost remains unchanged
        assertEquals(0.00, getTotalCost(), 0.01);
    }

    @Test
    public void testInvalidCategoryTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add a transaction
        // Should be rejected due to invalid category
        assertEquals(false, controller.addTransaction(50.00, "not food"));

        System.out.println("Transaction failed to add due to invalid category");

        // Post-condition: List of transactions remains empty
        assertEquals(0, model.getTransactions().size());

        // Total cost remains unchanged
        assertEquals(0.00, getTotalCost(), 0.01);
    }

    @Test
    public void testAmountFilter() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());

        // Perform the action: Add multiple transactions
        assertTrue(controller.addTransaction(50.00, "food"));
        assertTrue(controller.addTransaction(100.00, "travel"));
        assertTrue(controller.addTransaction(200.00, "entertainment"));
        assertTrue(controller.addTransaction(100.00, "bills"));

        // Verify all 4 transactions were added
        List<Transaction> allTransactions = model.getTransactions();
        assertEquals(4, model.getTransactions().size());

        // Apply amount filter (updates the view)
        double targetAmount = 100.00;
        AmountFilter amountFilter = new AmountFilter(targetAmount);
        List<Transaction> filtered = amountFilter.filter(allTransactions);

        // Post-condition: Filtered transactions are as expected (2 transactions)
        assertEquals(2, filtered.size());
        
        // Verify correct amount
        for (Transaction t : filtered) {
            assertEquals(targetAmount, t.getAmount(), 0.01);
        }
        // Verify correct categories are included
        List<String> categories = new ArrayList<>();
        for (Transaction t : filtered) {
            categories.add(t.getCategory());
        }

        assertTrue(categories.contains("travel"));
        assertTrue(categories.contains("bills"));
        
        // Verify the excluded transactions are not in the filtered list
        assertFalse(categories.contains("food"));
        assertFalse(categories.contains("entertainment"));

        // Display filtered transactions
        System.out.println("Filtered transactions:");
        for (Transaction t : filtered) {
            System.out.println(t);
        }
    }

    @Test
    public void testCategoryFilter() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());

        // Perform the action: Add multiple transactions
        assertTrue(controller.addTransaction(50.00, "food"));
        assertTrue(controller.addTransaction(70.00, "travel"));
        assertTrue(controller.addTransaction(200.00, "food"));           // Matches
        assertTrue(controller.addTransaction(150.00, "bills"));

        // Verify all 4 transactions were added
        assertEquals(4, model.getTransactions().size());

        // Apply category filter for "food" (updates the view)
        controller.applyFilter("category", "food");

        // Verify filtering logic by testing the filter directly
        CategoryFilter categoryFilter = new CategoryFilter("food");
        List<Transaction> filtered = categoryFilter.filter(model.getTransactions());
        
        // Post-condition: Filtered transactions are as expected (2 transactions)
        assertEquals(2, filtered.size());
        
        // Verify all filtered transactions are in "food" category
        for (Transaction t : filtered) {
            assertEquals("food", t.getCategory().toLowerCase());
        }
        
        // Verify correct amounts are included
        List<Double> amounts = new ArrayList<>();
        for (Transaction t : filtered) {
            amounts.add(t.getAmount());
        }
        assertTrue(amounts.contains(50.00));
        assertTrue(amounts.contains(200.00));
        
        // Verify the excluded categories are not in the filtered list
        for (Transaction t : filtered) {
            assertFalse(t.getCategory().equalsIgnoreCase("travel"));
            assertFalse(t.getCategory().equalsIgnoreCase("bills"));
        }

        // Display filtered transactions
        System.out.println("Filtered transactions (category: food):");
        for (Transaction t : filtered) {
            System.out.println(t);
        }
    }

    @Test
    public void testRemoveTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add and remove a transaction
        Transaction addedTransaction = new Transaction(50.00, "Groceries");
        model.addTransaction(addedTransaction);
    
        // Pre-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());
    
        // Perform the action: Remove the transaction
        model.removeTransaction(addedTransaction);
    
        // Post-condition: List of transactions is empty
        List<Transaction> transactions = model.getTransactions();
        assertEquals(0, transactions.size());
    
        // Check the total cost after removing the transaction
        double totalCost = getTotalCost();
        assertEquals(0.00, totalCost, 0.01);
    }
    
}