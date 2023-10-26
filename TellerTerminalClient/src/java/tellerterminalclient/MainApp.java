/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tellerterminalclient;

import ejb.session.stateless.AccountSessionBeanRemote;
import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import entity.Employee;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author admin
 */
public class MainApp {

    private AccountSessionBeanRemote accountSessionBeanRemote;
    private CustomerSessionBeanRemote customerSessionBeanRemote;
    private AtmCardSessionBeanRemote atmCardSessionBeanRemote;
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;

    private Customer currentCustomer;
    private Employee currentEmployee;

    public MainApp() {
        currentCustomer = null;
        currentEmployee = null;

    }

    public MainApp(AccountSessionBeanRemote accountSessionBeanRemote, CustomerSessionBeanRemote customerSessionBeanRemote,
            AtmCardSessionBeanRemote atmCardSessionBeanRemote, EmployeeSessionBeanRemote employeeSessionBeanRemote) {
        this();
        this.accountSessionBeanRemote = accountSessionBeanRemote;
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.atmCardSessionBeanRemote = atmCardSessionBeanRemote;
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        try {
            doLoginEmployee();
        
            while (true) {
                System.out.println("*** Welcome to the Teller Terminal ***\n");
                System.out.println("1: Become a new customer");
                System.out.println("2: Open a new deposit account");
                System.out.println("3: Get a new ATM card");
                System.out.println("4: Get a replacement ATM card");
                System.out.println("5: Exit\n");
                response = 0;

                while (response < 1 || response > 5) {
                    System.out.print("> ");

                    response = scanner.nextInt();

                    if (response == 1) {
                        System.out.println("*** Teller Terminal :: Create a customer ***\n");
                        scanner.nextLine();
                        System.out.print("Enter your firstName: ");
                        String firstName = scanner.nextLine().trim();

                        System.out.print("Enter your lastName: ");
                        String lastName = scanner.nextLine().trim();

                        System.out.print("Enter your identification number: ");
                        String identificationNumber = scanner.nextLine().trim();

                        System.out.print("Enter your contact number: ");
                        String contactNumber = scanner.nextLine().trim();

                        System.out.print("Enter your address line 1: ");
                        String addressLineOne = scanner.nextLine().trim();

                        System.out.print("Enter your address line 2: ");
                        String addressLineTwo = scanner.nextLine().trim();

                        System.out.print("Enter your postal code: ");
                        String postalCode = scanner.nextLine().trim();

                        Customer customer = customerSessionBeanRemote.createNewCustomer(new Customer(firstName,
                                lastName, identificationNumber, contactNumber, addressLineOne,
                                addressLineTwo, postalCode));

                        System.out.println("New account successfully created " + customer.getFirstName() + "!\n");
                    } else if (response == 2) {
                        System.out.println("*** Teller Terminal :: Open a deposit account ***\n");
                        try {
                            doLogin();
                            System.out.println("Login successful as " + currentCustomer.getFirstName() + "!\n");

                            System.out.print("Enter your the amount you want to deposit in your account: ");
                            BigDecimal amount = scanner.nextBigDecimal();

                            List<DepositAccount> account = accountSessionBeanRemote.createNewAccount(currentCustomer.getCustomerId(), new DepositAccount(currentCustomer, amount));

                            System.out.println("New account successfully created for " + currentCustomer.getFirstName() + "!\n");
                        } catch (InvalidLoginCredentialException ex) {
                            System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                        }
                    } else if (response == 3) {
                        System.out.println("*** Teller Terminal :: Get a ATM card ***\n");
                        try {
                            doLogin();
                            System.out.println("Login successful as " + currentCustomer.getFirstName() + "!\n");
                        } catch (InvalidLoginCredentialException ex) {
                            System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                        }

                        if (currentCustomer.getAtmCard() != null) {
                            System.out.println("You already have an ATM card, get a replacement card if you need it!\n");
                        } else {
                            System.out.print("Enter your desired pin number: ");
                            scanner.nextLine();
                            String pinNumber = scanner.nextLine().trim();
                            AtmCard card = atmCardSessionBeanRemote.issueNewCard(currentCustomer.getCustomerId(), new AtmCard(pinNumber, currentCustomer));
                            
                            System.out.println("Successfully created new card of card number " +  card.getCardNumber() + "!\n");
                        }
                    } else if (response == 4) {
                        System.out.println("*** Teller Terminal :: Replace your ATM card ***\n");
                        try {
                            doLogin();
                            System.out.println("Login successful as " + currentCustomer.getFirstName() + "!\n");
                        } catch (InvalidLoginCredentialException ex) {
                            System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                        }

                        if (currentCustomer.getAtmCard() == null) {
                            System.out.println("You do not have an ATM card, get a new card if you need it!\n");
                        } else {
                            scanner.nextLine();
                            System.out.print("Enter your desired pin number ");
                            String pinNumber = scanner.nextLine().trim();
                            AtmCard card = atmCardSessionBeanRemote.issueNewCard(currentCustomer.getCustomerId(), new AtmCard(pinNumber, currentCustomer));

                            System.out.println("Your new card number is " + card.getCardNumber() + "!\n");
                        }
                    } else if (response == 5) {
                        break;
                    } else {
                        System.out.println("Invalid option, please try again!\n");
                    }

                }

                if (response == 5) {
                    break;
                }
            }
        }
        catch (InvalidLoginCredentialException ex) {
            System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
        }
    }

    private void doLoginEmployee() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Teller Terminal for Employee :: Login ***\n");
        System.out.print("Enter user name> ");
        String userName = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        String password = scanner.nextLine().trim();

        if (userName.length() > 0 && password.length() > 0) {
            currentEmployee = employeeSessionBeanRemote.login(userName, password);
            System.out.println("Login successful as " + currentEmployee.getUserName() + "!\n");
        } else {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
    }

    private void doLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Teller Terminal :: Login ***\n");
        System.out.print("Enter first name> ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter identification number> ");
        String identificationNumber = scanner.nextLine().trim();

        if (firstName.length() > 0 && identificationNumber.length() > 0) {
            currentCustomer = customerSessionBeanRemote.loginWithName(firstName, identificationNumber);
        } else {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
    }
}
