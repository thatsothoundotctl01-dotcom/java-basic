import java.util.ArrayList;
import java.util.Scanner;

class Student {

    private String name;
    private int id;
    private double grade;

    // Constructor
    public Student(String name, int id, double grade) {
        this.name = name;
        this.id = id;
        this.grade = grade;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public double getGrade() {
        return grade;
    }

    // Add grade points
    public void addGrade(double points) {
        grade += points;
    }

    // Get letter grade
    public char getAverage() {

        if (grade >= 90) {
            return 'A';

        } else if (grade >= 80) {
            return 'B';

        } else if (grade >= 70) {
            return 'C';

        } else if (grade >= 60) {
            return 'D';

        } else {
            return 'F';
        }
    }

    // Display student information
    public void displayStudent() {

        System.out.println("----------------------------");
        System.out.println("Name  : " + name);
        System.out.println("ID    : " + id);
        System.out.println("Grade : " + grade);
        System.out.println("Level : " + getAverage());
        System.out.println("----------------------------");
    }
}


public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Student> list = new ArrayList<>();

        int option;

        do {

            System.out.println("\n============================");
            System.out.println("       STUDENT PORTAL");
            System.out.println("============================");
            System.out.println("1. Add Student");
            System.out.println("2. Add Grade");
            System.out.println("3. Show Students");
            System.out.println("4. Show Top Student");
            System.out.println("5. Exit");
            System.out.println("============================");

            System.out.print("Choose option: ");
            option = sc.nextInt();

            switch (option) {

                // =========================
                // 1. ADD STUDENT
                // =========================
                case 1:

                    sc.nextLine();

                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();

                    System.out.print("Enter grade: ");
                    double grade = sc.nextDouble();

                    Student student = new Student(name, id, grade);

                    list.add(student);

                    System.out.println("\nStudent added successfully!");

                    break;


                // =========================
                // 2. ADD GRADE
                // =========================
                case 2:

                    if (list.isEmpty()) {
                        System.out.println("\nNo students available!");
                        break;
                    }

                    System.out.print("Enter student ID: ");
                    int searchId = sc.nextInt();

                    boolean found = false;

                    for (Student s : list) {

                        if (s.getId() == searchId) {

                            System.out.print("Enter points to add: ");
                            double points = sc.nextDouble();

                            s.addGrade(points);

                            System.out.println(
                                "Grade updated successfully!"
                            );

                            System.out.println(
                                "New grade: " + s.getGrade()
                            );

                            found = true;

                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Student not found!");
                    }

                    break;


                // =========================
                // 3. SHOW STUDENTS
                // =========================
                case 3:

                    if (list.isEmpty()) {

                        System.out.println("\nNo students available!");

                    } else {

                        System.out.println("\n===== ALL STUDENTS =====");

                        for (Student s : list) {
                            s.displayStudent();
                        }
                    }

                    break;


                // =========================
                // 4. SHOW TOP STUDENT
                // =========================
                case 4:

                    if (list.isEmpty()) {

                        System.out.println("\nNo students available!");

                    } else {

                        Student top = list.get(0);

                        for (Student s : list) {

                            if (s.getGrade() > top.getGrade()) {
                                top = s;
                            }
                        }

                        System.out.println("\n===== TOP STUDENT =====");

                        top.displayStudent();
                    }

                    break;


                // =========================
                // 5. EXIT
                // =========================
                case 5:

                    System.out.println("\nGoodbye!");

                    break;


                // =========================
                // INVALID OPTION
                // =========================
                default:

                    System.out.println(
                        "\nInvalid option! Please choose 1-5."
                    );
            }

        } while (option != 5);

        sc.close();
    }
}
