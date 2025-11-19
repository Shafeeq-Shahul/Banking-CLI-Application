import java.util.Scanner;

public class BankingApplication {

    public static void startApplication() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Account number : ");
        String ac = scanner.nextLine();
        if (ac == null || ac.isEmpty()) {
            throw new NullPointerException("Account number is empty");
        }
        System.out.print("Enter your Name : ");
        String name = scanner.nextLine();
        if (name == null || name.isEmpty()) {
            throw new NullPointerException("Customer name is empty");
        }
        BankAccount bankAccount = new BankAccount(ac, name);
        BankMethodsImpl bankMethods = new BankMethodsImpl();
        BankService bankService = new BankService(bankAccount, bankMethods);
        bankService.optionsLogic();
        scanner.close();
    }
}

