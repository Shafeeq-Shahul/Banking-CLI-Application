import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class BankService {

    // Dependency Injection
    private final BankAccount bankAccount;
    private final BankMethods bankMethods;

    // to store the deposit and withdraw action with date and time
    private final ArrayList<String> transactionHistory = new ArrayList<>();

    // to prevent duplication
    private int lastLoggedIndex = 0;

    public BankService(BankAccount bankAccount, BankMethods bankMethods) {
        this.bankAccount = bankAccount;
        this.bankMethods = bankMethods;
    }

    private void depositAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount should be positive");
        }
        bankAccount.setBalance(bankMethods.deposit(bankAccount.getBalance(), amount));
        transactionHistory.add("Amount : " + amount + " deposited at " + LocalDateTime.now() + ". "+ "Total Balance : " + bankAccount.getBalance());
        System.out.println("Amount : " + amount + " deposited at " + LocalDateTime.now() + ". "+ "Total Balance : " + bankAccount.getBalance());
    }

    private void withdrawAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount should be positive");
        }
        if (amount > bankAccount.getBalance()) {
            throw new IllegalArgumentException("Insufficient Balance");
        }
        bankAccount.setBalance(bankMethods.withdrawal(bankAccount.getBalance(), amount));
        transactionHistory.add("Amount : " + amount + " withdraw at " + LocalDateTime.now() + ". "+ "Total Balance : " + bankAccount.getBalance());
        System.out.println("Amount : " + amount + " withdraw at " + LocalDateTime.now() + ". "+ "Total Balance : " + bankAccount.getBalance());
    }

    private void showAccountDetails() {
        System.out.println(bankAccount);
    }

    private void showTransactionHistory() {
        for (String s : transactionHistory) {
            System.out.println(s);
        }
    }

    private void logData() throws IOException {
        File transactionFile = new File("output.txt");
        if (!transactionFile.exists()) {
            transactionFile.createNewFile();
        }
        try (FileWriter fileWriter = new FileWriter(transactionFile, true)) {
            for (int i = lastLoggedIndex; i < transactionHistory.size(); i++) {
                fileWriter.write(transactionHistory.get(i) + System.lineSeparator());
            }
            lastLoggedIndex = transactionHistory.size();
            System.out.println("Successfully logged.");
        }
    }

    private void displayApplication() {
        System.out.println(
                """
                        ===============================
                               CLI BANKING SYSTEM     \s
                        ===============================
                        1. Deposit Funds
                        2. Withdraw Funds
                        3. View Transaction History
                        4. Log Data into the File
                        5. View Account Details
                        6. Exit
                        -------------------------------
                """
        );
    }

    public void optionsLogic() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            displayApplication();
            System.out.print("Enter the option : ");
            int option = scanner.nextInt();
            if (option == 1) {
                System.out.print("Enter the deposit amount : ");
                double amount = scanner.nextDouble();
                depositAmount(amount);
            }
            else if (option == 2) {
                System.out.print("Enter the withdraw amount : ");
                double amount = scanner.nextDouble();
                withdrawAmount(amount);
            }
            else if (option == 3) {
                showTransactionHistory();
            }
            else if (option == 4) {
                try {
                    logData();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
            else if (option == 5) {
                showAccountDetails();
            }
            else if (option == 6) {
                System.out.println("Exit...");
                break;
            }
            else {
                System.out.println("Invalid Option. Please Try Again");
            }
        }
        // close the resources
        scanner.close();
    }
}
