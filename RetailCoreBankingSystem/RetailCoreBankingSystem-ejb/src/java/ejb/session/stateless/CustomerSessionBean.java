/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author admin
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanRemote, CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
    
    @PostConstruct
    public void postConstruct()
    {
    }
    
    @PreDestroy
    public void preDestroy()
    {
    }

    @Override
    public Customer createNewCustomer(Customer newCustomer) {
        em.persist(newCustomer);
        em.flush();
        
        return newCustomer;
    }
    
    @Override
    public Customer login(Long atmCardId, String pin) throws InvalidLoginCredentialException
    {
        try
        {
            Query query = em.createQuery("SELECT c FROM Customer c WHERE c.atmCardId = :atmCardId");
            query.setParameter("atmCardId", atmCardId);
            Customer customer = (Customer)query.getSingleResult();
            
            if(customer.getAtmCard().getPin().equals(pin))
            {
                return customer;
            }
            else
            {
                throw new InvalidLoginCredentialException("Invalid login credential");
            }
        }
        catch(NoResultException ex)
        {
            throw new InvalidLoginCredentialException("Invalid login credential");
        }
    }
    
    @Override
    public Customer loginWithName(String firstName, String identificationNumber) throws InvalidLoginCredentialException
    {
        try
        {
            Query query = em.createQuery("SELECT c FROM Customer c WHERE c.firstName = :firstName");
            query.setParameter("firstName", firstName);
            Customer customer = (Customer)query.getSingleResult();
            
            if(customer.getIdentificationNumber().equals(identificationNumber))
            {
                return customer;
            }
            else
            {
                throw new InvalidLoginCredentialException("Invalid login credential");
            }
        }
        catch(NoResultException ex)
        {
            throw new InvalidLoginCredentialException("Invalid login credential");
        }
    }
    
    @Override 
    public List<DepositAccount> getDepositAccounts(AtmCard atmCard) {
        Query query = em.createQuery("SELECT c FROM DepositAccount c WHERE c.atmCard = :atmCard");
        query.setParameter("atmCard", atmCard);
        List<DepositAccount> accounts = (List<DepositAccount>)query.getResultList();

        return accounts;
    }
    
    @Override
    public Customer retrieveCustomerById(Long customerId) throws CustomerNotFoundException
    {
        Customer customer = em.find(Customer.class, customerId);
        
        if(customer != null)
        {
            return customer;
        }
        else
        {
            throw new CustomerNotFoundException("Customer does not exist: " + customerId);
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
