/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.util.List;
import javax.ejb.Local;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author admin
 */
@Local
public interface CustomerSessionBeanLocal {
    public Customer createNewCustomer(Customer newCustomer);

    public Customer login(Long atmCardId, String pin) throws InvalidLoginCredentialException;
    
    public Customer retrieveCustomerById(Long customerId) throws CustomerNotFoundException;
    
    public Customer loginWithName(String firstName, String identificationNumber) throws InvalidLoginCredentialException;

    public List<DepositAccount> getDepositAccounts(AtmCard atmCard);
}
