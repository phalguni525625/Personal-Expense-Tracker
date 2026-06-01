import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

class Expense implements Serializable {
    double amount;
    String category;
    LocalDate date;

    Expense(double amount, String category, LocalDate date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String toString() {
        return "Amount: ₹" + amount + " | Category: " + category + " | Date: " + date;
    }
}

public class PersonalExpenseTracker {

    static ArrayList<Expense> expenses = new ArrayList<>();

    public static void addExpense(Scanner sc) {
        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        System.out.print("Enter Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(sc.nextLine());

        expenses.add(new Expense(amount, category, date));
        System.out.println("Expense Added Successfully!");
    }

    public static void displayExpenses() {
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    public static void monthlyReport(Scanner sc) {
        System.out.print("Enter Month Number (1-12): ");
        Month month = Month.of(sc.nextInt());

        double total = 0;
        for (Expense e : expenses) {
            if (e.date.getMonth() == month) {
                System.out.println(e);
                total += e.amount;
            }
        }
        System.out.println("Total Monthly Expense: ₹" + total);
    }

    public static void highestExpenseCategory() {
        HashMap<String, Double> map = new HashMap<>();

        for (Expense e : expenses) {
            map.put(e.category, map.getOrDefault(e.category, 0.0) + e.amount);
        }

        String category = "";
        double max = 0;

        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                category = entry.getKey();
            }
        }

        System.out.println("Highest Expense Category: " + category + " (₹" + max + ")");
    }

    public static void saveToFile() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("expenses.dat"));
            out.writeObject(expenses);
            out.close();
        } catch (Exception e) {
            System.out.println("Error Saving File");
        }
    }

    public static void loadFromFile() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("expenses.dat"));
            expenses = (ArrayList<Expense>) in.readObject();
            in.close();
        } catch (Exception e) {
            System.out.println("No Previous Data Found");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadFromFile();

        int choice;
        do {
            System.out.println("\n1.Add Expense\n2.Display Expenses\n3.Monthly Report\n4.Highest Category\n5.Save\n6.Exit");
            choice = sc.nextInt();

            switch (choice) {
                case 1: addExpense(sc); break;
                case 2: displayExpenses(); break;
                case 3: monthlyReport(sc); break;
                case 4: highestExpenseCategory(); break;
                case 5: saveToFile(); break;
                case 6: saveToFile(); break;
                default: System.out.println("Invalid Choice");
            }
        } while (choice != 6);

        sc.close();
    }
}
