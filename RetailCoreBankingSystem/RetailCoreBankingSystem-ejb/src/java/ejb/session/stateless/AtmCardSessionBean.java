/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author admin
 */
@Stateless
public class AtmCardSessionBean implements AtmCardSessionBeanRemote, AtmCardSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;

    @Override
    public AtmCard issueNewCard(Long customerId, AtmCard newCard) {
        em.persist(newCard);
        em.flush();
        
        Customer customer = em.find(Customer.class, customerId);
        Query query = em.createQuery("SELECT c FROM DepositAccount c WHERE c.customer = :customer");
        query.setParameter("customer", customer);
        List<DepositAccount> accounts = (List<DepositAccount>)query.getResultList();
        
        if (customer.getAtmCard() != null) {
            em.remove(customer.getAtmCard());
        }
        
        for (DepositAccount account:accounts) {
            account.setAtmCard(newCard);
        }
        customer.setAtmCard(newCard);
        
        return newCard;
    }
    
    @Override
    public Long replaceCard(AtmCard oldCard, AtmCard newCard) {
        em.remove(oldCard);
        em.persist(newCard);
        em.flush();
        
        return newCard.getAtmCardId();
    }
    
    @Override
    public Boolean insertCard(Long atmCardId, String pin) {
        Query query = em.createQuery("SELECT c FROM AtmCard c WHERE c.atmCardId = :atmCardId");
        query.setParameter("atmCardId", atmCardId);
        AtmCard card = (AtmCard)query.getSingleResult();
        
        if (card.getPin().equals(pin)) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void changePin(String cardNumber, String newPin) {
        Query query = em.createQuery("SELECT c FROM AtmCard c WHERE c.cardNumber = :cardNumber");
        query.setParameter("cardNumber", cardNumber);
        AtmCard card = (AtmCard)query.getSingleResult();
        
        card.setPin(newPin);
    }

    @Override
    public AtmCard loginWithPin(String cardNumber, String cardPin) throws InvalidLoginCredentialException
    {
        try
        {
            Query query = em.createQuery("SELECT c FROM AtmCard c WHERE c.cardNumber = :cardNumber");
            query.setParameter("cardNumber", cardNumber);
            AtmCard card = (AtmCard)query.getSingleResult();
            
            if(card.getPin().equals(cardPin))
            {
                return card;
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
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
