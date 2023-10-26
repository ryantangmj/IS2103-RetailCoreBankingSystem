/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import util.enumeration.DepositAccountType;

/**
 *
 * @author admin
 */
@Entity
public class DepositAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depositAccountId;
    private String accountNumber;
    private DepositAccountType account;
    private BigDecimal availableBalance;
    private BigDecimal holdBalance;
    private BigDecimal ledgerBalance;
    private Boolean enabled;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;
    
    @ManyToOne
    private AtmCard atmCard;
    
    @OneToMany(mappedBy = "depositAccount")
    private List<DepositAccountTransaction> transactions;
    
    public DepositAccount() {
        this.transactions = new ArrayList<DepositAccountTransaction>();
    }

    public DepositAccount(Customer customer, BigDecimal availableBalance) {
        this();
        
        Random random = new Random();
        this.accountNumber = String.valueOf(10000000 + random.nextInt(90000000));
        this.account = DepositAccountType.SAVINGS;
        this.availableBalance = availableBalance;
        this.holdBalance = BigDecimal.valueOf(0);
        this.ledgerBalance = availableBalance;
        this.enabled = true;
        this.customer = customer;
        this.atmCard = null;
    }

    public AtmCard getAtmCard() {
        return atmCard;
    }

    public void setAtmCard(AtmCard atmCard) {
        this.atmCard = atmCard;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    public BigDecimal getLedgerBalance() {
        return ledgerBalance;
    }

    public void setLedgerBalance(BigDecimal ledgerBalance) {
        this.ledgerBalance = ledgerBalance;
    }


    public BigDecimal getHoldBalance() {
        return holdBalance;
    }

    public void setHoldBalance(BigDecimal holdBalance) {
        this.holdBalance = holdBalance;
    }

    

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }


    public DepositAccountType getAccount() {
        return account;
    }

    public void setAccount(DepositAccountType account) {
        this.account = account;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public Long getDepositAccountId() {
        return depositAccountId;
    }

    public void setDepositAccountId(Long depositAccountId) {
        this.depositAccountId = depositAccountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depositAccountId != null ? depositAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DepositAccount)) {
            return false;
        }
        DepositAccount other = (DepositAccount) object;
        if ((this.depositAccountId == null && other.depositAccountId != null) || (this.depositAccountId != null && !this.depositAccountId.equals(other.depositAccountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DepositAccount[ id=" + depositAccountId + " ]";
    }
    
}
