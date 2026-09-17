import java.io.*;
import java.util.*;

/*
 * Bank Account Management System
 * Developed using:
 * - Modularity
 * - Readability
 * - Maintainability
 * - Encapsulation
 * - Information Hiding
 */

public class BankSystem {

    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        BankService bankService = new BankService();
        bankService.loadAccounts();

        while (true) {

            System.out.println("\n=================================");
            System.out.println(" BANK ACCOUNT MANAGEMENT SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");

            System.out.print("Choose Option: ");

            try {

                int choice = Integer.parseInt(input.nextLine());

                switch (choice) {

                    case 1:
                        adminLogin(bankService);
                        break;

                    case 2:
                        userLogin(bankService);
                        break;

                    case 3:
                        bankService.saveAccounts();
                        System.out.println("Thank you for using the system.");
                        return;

                    default:
                        System.out.println("Invalid option.");

                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // -------------------------------------------------------
    // ADMIN LOGIN
    // -------------------------------------------------------

    private static void adminLogin(BankService bankService) {

        System.out.print("Admin Username: ");
        String username = input.nextLine();

        System.out.print("Admin Password: ");
        String password = input.nextLine();

        if (AuthenticationService.isAdminAuthenticated(
                username,
                password)) {

            System.out.println("\nAdmin Login Successful");
            adminMenu(bankService);

        } else {

            System.out.println("Invalid credentials.");
        }
    }

    // -------------------------------------------------------
    // ADMIN MENU
    // -------------------------------------------------------

    private static void adminMenu(BankService bankService) {

        while (true) {

            System.out.println("\n------ ADMIN MENU ------");
            System.out.println("1. Create Account");
            System.out.println("2. View Accounts");
            System.out.println("3. Search Account");
            System.out.println("4. Update Account");
            System.out.println("5. Logout");

            System.out.print("Choose Option: ");

            try {

                int choice =
                        Integer.parseInt(input.nextLine());

                switch (choice) {

                    case 1:
                        createAccount(bankService);
                        break;

                    case 2:
                        bankService.viewAllAccounts();
                        break;

                    case 3:
                        searchAccount(bankService);
                        break;

                    case 4:
                        updateAccount(bankService);
                        break;

                    case 5:
                        return;

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
    }

    // -------------------------------------------------------
    // ACCOUNT CREATION
    // -------------------------------------------------------

    private static void createAccount(
            BankService bankService) {

        System.out.print("Enter Name: ");
        String name = input.nextLine();

        System.out.print("Enter 4-Digit PIN: ");
        String pin = input.nextLine();

        Account account =
                bankService.createAccount(name, pin);

        System.out.println(
                "Account Created Successfully!");
        System.out.println(
                "New Account ID: " +
                        account.getAccountId());
    }

    // -------------------------------------------------------
    // SEARCH ACCOUNT
    // -------------------------------------------------------

    private static void searchAccount(
            BankService bankService) {

        System.out.print("Enter Account ID: ");
        String accountId = input.nextLine();

        Account account =
                bankService.findAccount(accountId);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println("\nAccount Found");
        System.out.println("ID      : " +
                account.getAccountId());
        System.out.println("Name    : " +
                account.getName());
        System.out.println("Balance : " +
                account.getBalance());
    }

    // -------------------------------------------------------
    // UPDATE ACCOUNT
    // -------------------------------------------------------

    private static void updateAccount(
            BankService bankService) {

        System.out.print("Enter Account ID: ");
        String accountId = input.nextLine();

        Account account =
                bankService.findAccount(accountId);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("New Name: ");
        account.setName(input.nextLine());

        System.out.print("New PIN: ");
        account.setPin(input.nextLine());

        System.out.println(
                "Account updated successfully.");
    }

    // -------------------------------------------------------
    // USER LOGIN
    // -------------------------------------------------------

    private static void userLogin(
            BankService bankService) {

        System.out.print("Account ID: ");
        String accountId = input.nextLine();

        System.out.print("PIN: ");
        String pin = input.nextLine();

        Account account =
                AuthenticationService.authenticateUser(
                        bankService.getAccounts(),
                        accountId,
                        pin);

        if (account == null) {

            System.out.println(
                    "Invalid ID or PIN.");
            return;
        }

        System.out.println(
                "\nUser Login Successful");

        userMenu(bankService, account);
    }

    // -------------------------------------------------------
    // USER MENU
    // -------------------------------------------------------

    private static void userMenu(
            BankService bankService,
            Account account) {

        while (true) {

            System.out.println(
                    "\n------ USER MENU ------");
            System.out.println(
                    "1. View Account");
            System.out.println(
                    "2. Deposit");
            System.out.println(
                    "3. Withdraw");
            System.out.println(
                    "4. Transfer Funds");
            System.out.println(
                    "5. View History");
            System.out.println(
                    "6. Logout");

            System.out.print("Choose Option: ");

            try {

                int choice =
                        Integer.parseInt(input.nextLine());

                switch (choice) {

                    case 1:
                        viewAccount(account);
                        break;

                    case 2:
                        deposit(bankService, account);
                        break;

                    case 3:
                        withdraw(bankService, account);
                        break;

                    case 4:
                        transfer(bankService, account);
                        break;

                    case 5:
                        HistoryManager.viewHistory(account);
                        break;

                    case 6:
                        return;

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Enter a valid number.");
            }
        }
    }

    // -------------------------------------------------------
    // VIEW ACCOUNT
    // -------------------------------------------------------

    private static void viewAccount(
            Account account) {

        System.out.println("\nAccount Details");
        System.out.println(
                "ID      : " +
                        account.getAccountId());

        System.out.println(
                "Name    : " +
                        account.getName());

        System.out.println(
                "Balance : " +
                        account.getBalance());
    }

    // -------------------------------------------------------
    // DEPOSIT
    // -------------------------------------------------------

    private static void deposit(
            BankService bankService,
            Account account) {

        try {

            System.out.print(
                    "Deposit Amount: ");

            double amount =
                    Double.parseDouble(
                            input.nextLine());

            bankService.deposit(account, amount);

        } catch (NumberFormatException e) {

            System.out.println(
                    "Enter valid amount.");
        }
    }

    // -------------------------------------------------------
    // WITHDRAW
    // -------------------------------------------------------

    private static void withdraw(
            BankService bankService,
            Account account) {

        try {

            System.out.print(
                    "Withdraw Amount: ");

            double amount =
                    Double.parseDouble(
                            input.nextLine());

            bankService.withdraw(
                    account,
                    amount);

        } catch (NumberFormatException e) {

            System.out.println(
                    "Enter valid amount.");
        }
    }

    // -------------------------------------------------------
    // TRANSFER
    // -------------------------------------------------------

    private static void transfer(
            BankService bankService,
            Account sender) {

        try {

            System.out.print(
                    "Recipient Account ID: ");

            String receiverId =
                    input.nextLine();

            System.out.print(
                    "Transfer Amount: ");

            double amount =
                    Double.parseDouble(
                            input.nextLine());

            Account receiver =
                    bankService.findAccount(
                            receiverId);

            if (receiver == null) {

                System.out.println(
                        "Recipient not found.");
                return;
            }

            bankService.transferFunds(
                    sender,
                    receiver,
                    amount);

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid amount.");
        }
    }
}

/* ====================================================
   ACCOUNT CLASS
==================================================== */

class Account {

    private String accountId;
    private String name;
    private String pin;
    private double balance;
    private String historyFile;

    public Account(
            String accountId,
            String name,
            String pin) {

        this.accountId = accountId;
        this.name = name;
        this.pin = pin;
        this.balance = 0;
        this.historyFile =
                accountId + "_history.txt";
    }

    public String getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getHistoryFile() {
        return historyFile;
    }
}

/* ====================================================
   AUTHENTICATION SERVICE
==================================================== */

class AuthenticationService {

    private static final String ADMIN_USERNAME =
            "admin";

    private static final String ADMIN_PASSWORD =
            "123";

    public static boolean isAdminAuthenticated(
            String username,
            String password) {

        return username.equals(
                ADMIN_USERNAME)
                &&
                password.equals(
                        ADMIN_PASSWORD);
    }

    public static Account authenticateUser(
            List<Account> accounts,
            String accountId,
            String pin) {

        for (Account account : accounts) {

            if (account.getAccountId()
                    .equals(accountId)
                    &&
                    account.getPin()
                            .equals(pin)) {

                return account;
            }
        }

        return null;
    }
}

/* ====================================================
   HISTORY MANAGER
==================================================== */

class HistoryManager {

    public static void writeTransaction(
            Account account,
            String transactionText) {

        try {

            FileWriter writer =
                    new FileWriter(
                            account.getHistoryFile(),
                            true);

            writer.write(
                    transactionText + "\n");

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Unable to save history.");
        }
    }

    public static void viewHistory(
            Account account) {

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(
                                    account.getHistoryFile()));

            String line;

            System.out.println(
                    "\nTransaction History");

            while ((line =
                    reader.readLine()) != null) {

                System.out.println(line);
            }

            reader.close();

        } catch (Exception e) {

            System.out.println(
                    "No history available.");
        }
    }
}

/* ====================================================
   BANK SERVICE
==================================================== */

class BankService {

    private static final String FILE_NAME =
            "accounts.txt";

    private final List<Account> accounts =
            new ArrayList<>();

    public List<Account> getAccounts() {
        return accounts;
    }

    public Account createAccount(
            String name,
            String pin) {

        String accountId =
                "ACC" + (accounts.size() + 1);

        Account account =
                new Account(
                        accountId,
                        name,
                        pin);

        accounts.add(account);

        return account;
    }

    public Account findAccount(
            String accountId) {

        for (Account account : accounts) {

            if (account.getAccountId()
                    .equals(accountId)) {

                return account;
            }
        }

        return null;
    }

    public void viewAllAccounts() {

        if (accounts.isEmpty()) {

            System.out.println(
                    "No accounts available.");
            return;
        }

        for (Account account : accounts) {

            System.out.println(
                    "ID: " +
                            account.getAccountId()
                            +
                            " | Name: "
                            +
                            account.getName()
                            +
                            " | Balance: "
                            +
                            account.getBalance());
        }
    }

    public void deposit(
            Account account,
            double amount) {

        if (amount <= 0) {

            System.out.println(
                    "Invalid amount.");
            return;
        }

        account.setBalance(
                account.getBalance()
                        + amount);

        HistoryManager.writeTransaction(
                account,
                "Deposit: " + amount);

        System.out.println(
                "Deposit successful.");
    }

    public void withdraw(
            Account account,
            double amount) {

        if (amount <= 0) {

            System.out.println(
                    "Invalid amount.");
            return;
        }

        if (amount >
                account.getBalance()) {

            System.out.println(
                    "Insufficient balance.");
            return;
        }

        account.setBalance(
                account.getBalance()
                        - amount);

        HistoryManager.writeTransaction(
                account,
                "Withdraw: " + amount);

        System.out.println(
                "Withdrawal successful.");
    }

    public void transferFunds(
            Account sender,
            Account receiver,
            double amount) {

        if (amount >
                sender.getBalance()) {

            System.out.println(
                    "Insufficient balance.");
            return;
        }

        sender.setBalance(
                sender.getBalance()
                        - amount);

        receiver.setBalance(
                receiver.getBalance()
                        + amount);

        HistoryManager.writeTransaction(
                sender,
                "Transferred "
                        + amount
                        + " to "
                        + receiver.getAccountId());

        HistoryManager.writeTransaction(
                receiver,
                "Received "
                        + amount
                        + " from "
                        + sender.getAccountId());

        System.out.println(
                "Transfer successful.");
    }

    // --------------------------------------
    // SAVE ACCOUNTS
    // --------------------------------------

    public void saveAccounts() {

        try {

            PrintWriter writer =
                    new PrintWriter(
                            new FileWriter(
                                    FILE_NAME));

            for (Account account :
                    accounts) {

                writer.println(
                        account.getAccountId()
                                + ","
                                + account.getName()
                                + ","
                                + account.getPin()
                                + ","
                                + account.getBalance());
            }

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Unable to save accounts.");
        }
    }

    // --------------------------------------
    // LOAD ACCOUNTS
    // --------------------------------------

    public void loadAccounts() {

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(FILE_NAME));

            String line;

            while ((line =
                    reader.readLine())
                    != null) {

                String[] data =
                        line.split(",");

                Account account =
                        new Account(
                                data[0],
                                data[1],
                                data[2]);

                account.setBalance(
                        Double.parseDouble(
                                data[3]));

                accounts.add(account);
            }

            reader.close();

        } catch (IOException e) {

            System.out.println(
                "No existing accounts found.");
        }
    }
}