public class BankMethodsImpl implements BankMethods {

    public double deposit(double balance, double amount) {
        balance += amount;
        return balance;
    }

    public double withdrawal(double balance, double amount) {
        balance -= amount;
        return balance;
    }
}
