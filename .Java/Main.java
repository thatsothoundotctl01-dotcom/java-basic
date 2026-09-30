import java.util.ArrayList;
import java.util.Scanner;

class Employee {
    private String name;
    private long code;
    private String designation;
    private int exp;
    private int age;

    public Employee(String name, long code, String designation, int exp, int age) {
        this.name = name;
        this.code = code;
        this.designation = designation;
        this.exp = exp;
        this.age = age;
    }

    public String getName() { return name; }
    public long getCode() { return code; }
    public String getDesignation() { return designation; }
    public int getExp() { return exp; }
    public int getAge() { return age; }

    public void displayEmployee() {
        System.out.println("Name        : " + name);
        System.out.println("Code        : " + code);
        System.out.println("Designation : " + designation);
        System.out.println("Experience  : " + exp);
        System.out.println("Age         : " + age);
        System.out.println("---------------------");
    }
}

public class Main {
    public static void main(String[] args) {
        ArrayList<Employee> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int option;

        do {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add");
            System.out.println("2. Display");
            System.out.println("3. Search");
            System.out.println("0. Exit");
            System.out.print("Option : ");
            option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                System.out.print("Name : ");
                String name = sc.nextLine();

                System.out.print("Code : ");
                long code = sc.nextLong();
                sc.nextLine();

                System.out.print("Designation : ");
                String designation = sc.nextLine();

                System.out.print("Experience : ");
                int exp = sc.nextInt();

                System.out.print("Age : ");
                int age = sc.nextInt();
                sc.nextLine();

                list.add(new Employee(name, code, designation, exp, age));
                System.out.println("Employee added.");

            } else if (option == 2) {
                if (list.isEmpty()) {
                    System.out.println("No employees yet.");
                } else {
                    for (Employee e : list) {
                        e.displayEmployee();
                    }
                }

            } else if (option == 3) {
                System.out.print("Enter code to search : ");
                long searchCode = sc.nextLong();
                sc.nextLine();

                boolean found = false;
                for (Employee e : list) {
                    if (e.getCode() == searchCode) {
                        e.displayEmployee();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Employee not found.");
                }

            } else if (option != 0) {
                System.out.println("Invalid option.");
            }

        } while (option != 0);

        System.out.println("Goodbye!");
        sc.close();
    }
}
