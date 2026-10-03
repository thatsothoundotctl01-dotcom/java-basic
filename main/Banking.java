import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;
    private LocalDateTime time = LocalDateTime.now();

    Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return time.withNano(0) + "  " + type + "  " + amount;
    }
}

class Account {
    private long accountNumber;
    private String name;
    protected double balance;
    private ArrayList<Transaction> history = new ArrayList<>();

    Account(long accountNumber, String name, double openingBalance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = openingBalance;
        history.add(new Transaction("OPEN", openingBalance));
    }

    long getAccountNumber() { return accountNumber; }
    String getName() { return name; }
    double getBalance() { return balance; }

    void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive.");
        balance += amount;
        history.add(new Transaction("DEPOSIT", amount));
    }

    void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive.");
        if (!canWithdraw(amount)) throw new IllegalStateException("Insufficient funds.");
        balance -= amount;
        history.add(new Transaction("WITHDRAW", amount));
    }

    // Subclasses override this to change the rule
    protected boolean canWithdraw(double amount) {
        return balance >= amount;
    }

    void display() {
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Name           : " + name);
        System.out.println("Balance        : " + balance);
    }

    void printStatement() {
        System.out.println("Statement for " + name);
        for (Transaction t : history) System.out.println("  " + t);
        System.out.println("Balance: " + balance);
    }
}

class SavingAccount extends Account {
    private double interestRate;          // e.g. 0.04 for 4%
    private static final double MIN_BALANCE = 500;

    SavingAccount(long accountNumber, String name, double openingBalance, double interestRate) {
        super(accountNumber, name, openingBalance);
        this.interestRate = interestRate;
    }

    @Override
    protected boolean canWithdraw(double amount) {
        return balance - amount >= MIN_BALANCE;
    }

    void addInterest() {
        deposit(balance * interestRate);
    }

    @Override
    void display() {
        super.display();
        System.out.println("Type           : Saving");
        System.out.println("Interest rate  : " + interestRate);
    }
}

class CurrentAccount extends Account {
    private double overdraftLimit;

    CurrentAccount(long accountNumber, String name, double openingBalance, double overdraftLimit) {
        super(accountNumber, name, openingBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    protected boolean canWithdraw(double amount) {
        return balance - amount >= -overdraftLimit;
    }

    @Override
    void display() {
        super.display();
        System.out.println("Type           : Current");
        System.out.println("Overdraft limit: " + overdraftLimit);
    }
}

class Bank {
    private ArrayList<Account> accounts = new ArrayList<>();
    private long nextNumber = 1001;

    Account createSaving(String name, double opening) {
        Account a = new SavingAccount(nextNumber++, name, opening, 0.04);
        accounts.add(a);
        return a;
    }

    Account createCurrent(String name, double opening) {
        Account a = new CurrentAccount(nextNumber++, name, opening, 1000);
        accounts.add(a);
        return a;
    }

    Account find(long number) {
        for (Account a : accounts) {
            if (a.getAccountNumber() == number) return a;
        }
        throw new IllegalArgumentException("Account not found.");
    }

    void transfer(long from, long to, double amount) {
        Account src = find(from);
        Account dst = find(to);
        src.withdraw(amount);
        dst.deposit(amount);
    }
}

public class Banking {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner sc = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n1.Create Saving  2.Create Current  3.Deposit  4.Withdraw");
            System.out.println("5.Transfer  6.Display  7.Statement  0.Exit");
            System.out.print("Option : ");
            option = sc.nextInt();
            sc.nextLine();

            try {
                switch (option) {
                    case 1, 2 -> {
                        System.out.print("Name : ");
                        String name = sc.nextLine();
                        System.out.print("Opening balance : ");
                        double opening = sc.nextDouble();
                        sc.nextLine();
                        Account a = (option == 1) ? bank.createSaving(name, opening)
                                                  : bank.createCurrent(name, opening);
                        System.out.println("Created. Account number: " + a.getAccountNumber());
                    }
                    case 3 -> {
                        System.out.print("Account no : ");
                        long no = sc.nextLong();
                        System.out.print("Amount : ");
                        bank.find(no).deposit(sc.nextDouble());
                        sc.nextLine();
                        System.out.println("Deposited.");
                    }
                    case 4 -> {
                        System.out.print("Account no : ");
                        long no = sc.nextLong();
                        System.out.print("Amount : ");
                        bank.find(no).withdraw(sc.nextDouble());
                        sc.nextLine();
                        System.out.println("Withdrawn.");
                    }
                    case 5 -> {
                        System.out.print("From : ");
                        long from = sc.nextLong();
                        System.out.print("To : ");
                        long to = sc.nextLong();
                        System.out.print("Amount : ");
                        bank.transfer(from, to, sc.nextDouble());
                        sc.nextLine();
                        System.out.println("Transfer done.");
                    }
                    case 6 -> {
                        System.out.print("Account no : ");
                        long no = sc.nextLong();
                        sc.nextLine();
                        bank.find(no).display();
                    }
                    case 7 -> {
                        System.out.print("Account no : ");
                        long no = sc.nextLong();
                        sc.nextLine();
                        bank.find(no).printStatement();
                    }
                    case 0 -> System.out.println("Goodbye!");
                    default -> System.out.println("Invalid option.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (option != 0);

        sc.close();
    }
}
