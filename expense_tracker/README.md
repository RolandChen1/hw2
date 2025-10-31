# hw1- Manual Review

The homework will be based on this project named "Expense Tracker",where users will be able to add/remove daily transaction. 

## Compile

To compile the code from terminal, use the following command:
```
cd src
javac ExpenseTrackerApp.java
java ExpenseTrackerApp
```

You should be able to view the GUI of the project upon successful compilation. 

## Java Version
This code is compiled with ```openjdk 17.0.7 2023-04-18```. Please update your JDK accordingly if you face any incompatibility issue.

## New Features (HW2)

### Filtering
Users can now filter transactions by either **Amount** or **Category**.
This feature is implemented using the **Strategy Design Pattern**, where each filter type (AmountFilter, CategoryFilter) is encapsulated as a separate strategy.

### Input Validation
Filter inputs apply the same validation as the Add Transaction form.

### Running Tests
Unit tests are provided under the `test/` folder. Run them with:
