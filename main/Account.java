import java.util.ArrayList;
import java.util.List;

abstract class Account {
    private final String owner;
    private double balance;

    protected Account(String owner, double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative");
        }
        this.owner = owner;
        this.balance = balance;
    }

    public String getOwner() { return owner; }
    public double getBalance() { return balance; }

    // How much can be taken out right now. Subclasses can extend this.
    protected double availableFunds() { return balance; }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit must be positive");
        }
        balance += amount;
        System.out.printf("%s deposited %.2f%n", owner, amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > availableFunds()) {
            return false;
        }
        balance -= amount;
        return true;
    }

    // Each account type decides what happens at month end.
    public abstract void monthlyUpdate();

    @Override
    public String toString() {
        return String.format("%s [%s]: %.2f", getClass().getSimpleName(), owner, balance);
    }
}

class SavingsAccount extends Account {
    private final double interestRate; // annual, e.g. 0.05 = 5%

    public SavingsAccount(String owner, double balance, double interestRate) {
        super(owner, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void monthlyUpdate() {
        double interest = getBalance() * interestRate / 12;
        deposit(interest);
    }
}

class CheckingAccount extends Account {
    private final double overdraftLimit;

    public CheckingAccount(String owner, double balance, double overdraftLimit) {
        super(owner, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() { return overdraftLimit; }

    // Overdraft is built into withdraw() via availableFunds(),
    // so no separate canWithdraw() check is needed.
    @Override
    protected double availableFunds() {
        return getBalance() + overdraftLimit;
    }

    @Override
    public void monthlyUpdate() {
        // No monthly changes yet (a fee could go here).
    }
}

public class Main {
    public static void main(String[] args) {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new SavingsAccount("Sokha", 1000, 0.05));
        accounts.add(new CheckingAccount("Dara", 200, 300));

        accounts.get(0).deposit(500);
        System.out.println("Withdraw 450 from checking: " + accounts.get(1).withdraw(450));
        System.out.println("Withdraw 100 more:          " + accounts.get(1).withdraw(100));

        for (Account a : accounts) {
            a.monthlyUpdate();
            System.out.println(a);
        }
    }
}
