import java.io.*;
import java.util.*;

public class Bank {
    static Scanner input = new Scanner(System.in);
    static String[][] accounts = new String[100][5]; // [ID, Name, PIN, Balance, HistoryFile]
    static int accountCount = 0; // total number of accounts
    static final String FILE_NAME = "accounts.txt"; // file to store account records

    public static void main(String[] args) {
        loadAccounts(); // load existing accounts from file

        while (true) {
            try {
                System.out.println("=== Bank Account Management System ===");
                System.out.println("1. Admin Login");
                System.out.println("2. User Login");
                System.out.println("3. Exit");
                System.out.print("Choose option: ");
                int choice = Integer.parseInt(input.nextLine());

                switch (choice) {
                    case 1:
                        adminLogin(); 
                        break;
                    case 2:
                        userLogin(); 
                        break;
                    case 3:
                        saveAccounts(); 
                        System.out.println("Exiting system. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error! Please enter valid input.");
            }
        }
    }

    public static void adminLogin() {
        System.out.print("Enter Admin Username: ");
        String user = input.nextLine();
        System.out.print("Enter Admin Password: ");
        String pass = input.nextLine();

        if (user.equals("admin") && pass.equals("123")) {
            System.out.println("Login successful!\n");
            adminMenu();
        } else {
            System.out.println("Invalid admin credentials.\n");
        }
    }

    public static void adminMenu() {
        while (true) {
            try {
                System.out.println("\n--- Admin Menu ---");
                System.out.println("1. Add New Account");
                System.out.println("2. View All Accounts");
                System.out.println("3. Search Account by ID");
                System.out.println("4. Update Account Details");
                System.out.println("5. Logout");
                System.out.print("Select option: ");
                int choice = Integer.parseInt(input.nextLine());

                switch (choice) {
                    case 1:
                        addAccount();
                        break;
                    case 2:
                        viewAccounts();
                        break;
                    case 3:
                        searchAccount();
                        break;
                    case 4:
                        updateAccount();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public static void addAccount() {
        try {
            System.out.print("Enter Name: ");
            String name = input.nextLine();

            System.out.print("Enter PIN (4 digits): ");
            String pin = input.nextLine();

            String id = "ACC" + (accountCount + 1);
            String balance = "0";
            String historyFile = id + "_history.txt";

            accounts[accountCount][0] = id;
            accounts[accountCount][1] = name;
            accounts[accountCount][2] = pin;
            accounts[accountCount][3] = balance;
            accounts[accountCount][4] = historyFile;
            accountCount++;

            System.out.println("Account Created. ID: " + id);
        } catch (Exception e) {
            System.out.println("Error creating account. Try again.");
        }
    }

    public static void viewAccounts() {
        System.out.println("\n--- All Accounts ---");
        for (int i = 0; i < accountCount; i++) {
            System.out.println("ID: " + accounts[i][0] + ", Name: " + accounts[i][1] + ", Balance: " + accounts[i][3]);
        }
    }

    public static void searchAccount() {
        System.out.print("Enter Account ID to search: ");
        String id = input.nextLine();
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i][0].equals(id)) {
                System.out.println("Found: Name: " + accounts[i][1] + ", Balance: " + accounts[i][3]);
                return;
            }
        }
        System.out.println("Account not found.");
    }

    public static void updateAccount() {
        System.out.print("Enter Account ID to update: ");
        String id = input.nextLine();
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i][0].equals(id)) {
                System.out.print("Enter new Name: ");
                accounts[i][1] = input.nextLine();
                System.out.print("Enter new PIN: ");
                accounts[i][2] = input.nextLine();
                System.out.println("Account updated successfully.");
                return;
            }
        }
        System.out.println("Account not found.");
    }

    public static void userLogin() {
        System.out.print("Enter Account ID: ");
        String id = input.nextLine();
        System.out.print("Enter PIN: ");
        String pin = input.nextLine();

        for (int i = 0; i < accountCount; i++) {
            if (accounts[i][0].equals(id) && accounts[i][2].equals(pin)) {
                System.out.println("Login successful!");
                userMenu(i);
                return;
            }
        }
        System.out.println("Invalid ID or PIN.");
    }

    public static void userMenu(int index) {
        while (true) {
            try {
                System.out.println("\n--- User Menu ---");
                System.out.println("1. View Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Transfer Funds");
                System.out.println("5. View Transaction History");
                System.out.println("6. Logout");
                System.out.print("Select option: ");
                int choice = Integer.parseInt(input.nextLine());

                switch (choice) {
                    case 1:
                        viewAccount(index);
                        break;
                    case 2:
                        deposit(index);
                        break;
                    case 3:
                        withdraw(index);
                        break;
                    case 4:
                        transferFunds(index);
                        break;
                    case 5:
                        viewTransactions(index);
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Input error. Try again.");
            }
        }
    }

    public static void viewAccount(int i) {
        System.out.println("Account ID: " + accounts[i][0]);
        System.out.println("Name: " + accounts[i][1]);
        System.out.println("Balance: " + accounts[i][3]);
    }

    public static void deposit(int i) {
        try {
            System.out.print("Enter amount to deposit: ");
            double amt = Double.parseDouble(input.nextLine());
            double balance = Double.parseDouble(accounts[i][3]);
            balance += amt;
            accounts[i][3] = String.valueOf(balance);
            writeTransaction(i, "+ Deposit: " + amt);
            System.out.println("Deposit successful.");
        } catch (Exception e) {
            System.out.println("Invalid amount.");
        }
    }

    public static void withdraw(int i) {
        try {
            System.out.print("Enter amount to withdraw: ");
            double amt = Double.parseDouble(input.nextLine());
            double balance = Double.parseDouble(accounts[i][3]);
            if (amt > balance) {
                System.out.println("Insufficient funds.");
                return;
            }
            balance -= amt;
            accounts[i][3] = String.valueOf(balance);
            writeTransaction(i, "- Withdraw: " + amt);
            System.out.println("Withdrawal successful.");
        } catch (Exception e) {
            System.out.println("Invalid amount.");
        }
    }

    public static void transferFunds(int i) {
        try {
            System.out.print("Enter recipient Account ID: ");
            String toID = input.nextLine();
            System.out.print("Enter amount: ");
            double amt = Double.parseDouble(input.nextLine());

            for (int j = 0; j < accountCount; j++) {
                if (accounts[j][0].equals(toID)) {
                    double balFrom = Double.parseDouble(accounts[i][3]);
                    if (amt > balFrom) {
                        System.out.println("Insufficient balance.");
                        return;
                    }
                    double balTo = Double.parseDouble(accounts[j][3]);
                    balFrom -= amt;
                    balTo += amt;
                    accounts[i][3] = String.valueOf(balFrom);
                    accounts[j][3] = String.valueOf(balTo);
                    writeTransaction(i, "Transferred " + amt + " to " + toID);
                    writeTransaction(j, "Received " + amt + " from " + accounts[i][0]);
                    System.out.println("Transfer successful.");
                    return;
                }
            }
            System.out.println("Recipient not found.");
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    public static void viewTransactions(int i) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(accounts[i][4]));
            String line;
            System.out.println("--- Transaction History ---");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("No transactions found.");
        }
    }

    public static void writeTransaction(int i, String text) {
        try {
            FileWriter fw = new FileWriter(accounts[i][4], true);
            fw.write(text + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("Error writing transaction.");
        }
    }

    public static void saveAccounts() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME));
            for (int i = 0; i < accountCount; i++) {
                pw.println(accounts[i][0] + "," + accounts[i][1] + "," + accounts[i][2] + "," + accounts[i][3] + "," + accounts[i][4]);
            }
            pw.close();
        } catch (Exception e) {
            System.out.println("Error saving accounts.");
        }
    }

    public static void loadAccounts() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                for (int i = 0; i < 5; i++) {
                    accounts[accountCount][i] = parts[i];
                }
                accountCount++;
            }
            br.close();
        } catch (Exception e) {
            System.out.println("No saved accounts found. Starting fresh.");
        }
    }
} // End of Program
