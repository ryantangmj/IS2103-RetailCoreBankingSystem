/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author admin
 */
@Stateless
public class AccountSessionBean implements AccountSessionBeanRemote, AccountSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;

    @Override
    public List<DepositAccount> createNewAccount(Long customerId, DepositAccount newAccount) {
        em.persist(newAccount);
        em.flush();
        
        Customer customer = em.find(Customer.class, customerId);
        List<DepositAccount> accountList = customer.getDepositAccounts();
        accountList.add(newAccount);
        customer.setDepositAccounts(accountList);
        
        AtmCard atmCard = customer.getAtmCard();
        newAccount.setAtmCard(atmCard);
        
        return accountList;
    }
    
    @Override
    public BigDecimal getBalance(Long accountId) {
        Query query = em.createQuery("SELECT a FROM DepositAccount a WHERE a.depositAccountId = :depositAccountId");
        query.setParameter("depositAccountId", accountId);
        DepositAccount account = (DepositAccount)query.getSingleResult();
        
        return account.getAvailableBalance();
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
