

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// BankAccount class
class BankAccount {
    private String accountHolder;
    private String accountId;
    private double balance;
    private ArrayList<String> transactionHistory;

    public BankAccount(String accountHolder, String accountId) {
        this.accountHolder = accountHolder;
        this.accountId = accountId;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    private String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(getTimestamp() + " - Deposited ₹" + amount);
            System.out.println("₹" + amount + " deposited successfully.");
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0) {
            if (balance >= amount) {
                balance -= amount;
                transactionHistory.add(getTimestamp() + " - Withdrawn ₹" + amount);
                System.out.println("₹" + amount + " withdrawn successfully.");
            } else {
                System.out.println("Insufficient balance!");
            }
        } else {
            System.out.println("Withdrawal amount must be positive.");
        }
    }

    public void checkBalance() {
        System.out.println("Account Balance: ₹" + balance);
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("=== Transaction History for " + accountHolder + " ===");
            transactionHistory.forEach(System.out::println);
        }
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountHolder() {
        return accountHolder;
    }
}

// Main BankingSystem class
public class BankingSystem {
    private static HashMap<String, BankAccount> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int accountCounter = 1001; // For automatic account IDs

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = getValidIntegerInput();
            handleChoice(choice);
        }
    }

    private static void showMenu() {
        System.out.println("\n=== Banking System Menu ===");
        System.out.println("1. Create Account");
        System.out.println("2. Access Existing Account");
        System.out.println("3. Exit");
        System.out.print("Select an option: ");
    }

    private static void handleChoice(int choice) {
        switch (choice) {
            case 1 -> createAccount();
            case 2 -> accessAccount();
            case 3 -> exitSystem();
            default -> System.out.println("Invalid choice. Try again.");
        }
    }

    private static void createAccount() {
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();
        String accountId = "ACC" + accountCounter++;
        BankAccount newAccount = new BankAccount(name, accountId);
        accounts.put(accountId, newAccount);
        System.out.println("Account created successfully!");
        System.out.println("Account Holder: " + name + " | Account ID: " + accountId);
    }

    private static void accessAccount() {
        System.out.print("Enter account ID: ");
        String id = scanner.nextLine();
        BankAccount account = accounts.get(id);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        while (true) {
            System.out.println("\n--- Account Menu for " + account.getAccountHolder() + " ---");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Check Balance");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit to Main Menu");
            System.out.print("Select an option: ");

            int choice = getValidIntegerInput();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter amount to deposit: ₹");
                    double amount = getValidDoubleInput();
                    account.deposit(amount);
                }
                case 2 -> {
                    System.out.print("Enter amount to withdraw: ₹");
                    double amount = getValidDoubleInput();
                    account.withdraw(amount);
                }
                case 3 -> account.checkBalance();
                case 4 -> account.showTransactionHistory();
                case 5 -> {
                    System.out.println("Returning to Main Menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void exitSystem() {
        System.out.println("Thank you for using the Banking System!");
        System.exit(0);
    }

    private static int getValidIntegerInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number: ");
            }
        }
    }

    private static double getValidDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a valid amount: ₹");
            }
        }
    }
}

