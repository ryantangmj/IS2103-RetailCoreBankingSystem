/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatedtellermachineclient;

import ejb.session.stateless.AccountSessionBeanRemote;
import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
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

    private AtmCard currentCard;

    public MainApp() 
    {
        currentCard = null;
        
    }
    
    public MainApp(AccountSessionBeanRemote accountSessionBeanRemote, CustomerSessionBeanRemote customerSessionBeanRemote, 
            AtmCardSessionBeanRemote atmCardSessionBeanRemote) 
    {
        this();
        this.accountSessionBeanRemote = accountSessionBeanRemote;
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.atmCardSessionBeanRemote = atmCardSessionBeanRemote;
    }
    
    public void runApp()
    {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        Integer secondResponse = 0;
        
        while(true)
        {
            System.out.println("*** Welcome to the Automated Teller Machine ***\n");
            System.out.println("1: Insert ATM Card");
            System.out.println("2: Exit\n");
            response = 0;
            
            while(response < 1 || response > 2)
            {
                System.out.print("> ");

                response = scanner.nextInt();


                if (response == 1)
                {   
                    try {
                        doLogin();
                        
                        while(secondResponse > 1 || secondResponse < 3)
                        {
                            System.out.println("");
                            System.out.println("*** What transaction would you like to perform today? ***\n");
                            System.out.println("1: Change PIN");
                            System.out.println("2: Enquire account balance");
                            System.out.println("3: Exit\n");
                        
                        
                            System.out.print("> ");
                            secondResponse = scanner.nextInt();
                            scanner.nextLine();
                        
                            if (secondResponse == 1) {
                                System.out.println("*** Teller Terminal :: Change Pin ***\n");
                                System.out.print("Enter new card pin: ");
                                
                                String cardPin = scanner.nextLine().trim();
                                atmCardSessionBeanRemote.changePin(currentCard.getCardNumber(), cardPin);

                                System.out.println("The pin for " + currentCard.getCardNumber() + " has been successfully changed!\n");   
                            } 
                            else if (secondResponse == 2) {
                                System.out.println("*** Teller Terminal :: Enquire account balance ***\n");
                                
                                List<DepositAccount> accounts = customerSessionBeanRemote.getDepositAccounts(currentCard);
                                
                                for (DepositAccount account : accounts) {
                                    System.out.println("Account " + account.getAccountNumber() + " has " + account.getAvailableBalance() + "!\n");
                                }
                            } 
                            else if (secondResponse == 3) {
                                break;
                            }
                        }
                    }
                    catch(InvalidLoginCredentialException ex) 
                    {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    } 
                }   
            }
            if (response == 2) {
                break;
            }
        }
    }
    
    
    
    private void doLogin() throws InvalidLoginCredentialException
    {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** Teller Terminal :: Login ***\n");
        System.out.print("Enter card number> ");
        String cardNumber = scanner.nextLine().trim();
        System.out.print("Enter card pin> ");
        String cardPin = scanner.nextLine().trim();
        
        if(cardNumber.length() > 0 && cardPin.length() > 0)
        {
            currentCard = atmCardSessionBeanRemote.loginWithPin(cardNumber, cardPin);
        }
        else
        {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
    }
}
