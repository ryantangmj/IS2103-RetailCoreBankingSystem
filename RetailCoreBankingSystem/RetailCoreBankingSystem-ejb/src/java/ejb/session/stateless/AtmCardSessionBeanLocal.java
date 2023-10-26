/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.AtmCard;
import javax.ejb.Local;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author admin
 */
@Local
public interface AtmCardSessionBeanLocal {
    public AtmCard issueNewCard(Long customerId, AtmCard newCard);
    
    public Long replaceCard(AtmCard oldCard, AtmCard newCard);
    
    public Boolean insertCard(Long atmCardId, String pin);
    
    public void changePin(String cardNumber, String newPin);

    public AtmCard loginWithPin(String cardNumber, String cardPin) throws InvalidLoginCredentialException;
}
