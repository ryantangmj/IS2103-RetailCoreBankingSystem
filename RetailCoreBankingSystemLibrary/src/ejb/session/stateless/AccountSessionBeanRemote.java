/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import entity.DepositAccount;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author admin
 */
@Remote
public interface AccountSessionBeanRemote {
    public List<DepositAccount> createNewAccount(Long customerId, DepositAccount newAccount);

    public BigDecimal getBalance(Long accountId);

    //public void addDepositAccount(BigDecimal availableBalance, Long customerId);
}
