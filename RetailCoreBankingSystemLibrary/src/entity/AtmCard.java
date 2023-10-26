/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author admin
 */
@Entity
public class AtmCard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long atmCardId;
    private String cardNumber;
    private String nameOnCard;
    private Boolean enabled;
    private String pin;
    
    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;
    
    @OneToMany(mappedBy="atmCard")
    private List<DepositAccount> depositAccount;

    public AtmCard() {
        this.depositAccount = new ArrayList<DepositAccount>();
    }

    
    public AtmCard(String pin, Customer customer) {
        this();
        
        Random random = new Random();
        this.pin = pin;
        this.cardNumber = String.valueOf(10000000 + random.nextInt(90000000));
        this.nameOnCard = customer.getFirstName() + " " + customer.getLastName();
        this.enabled = true;
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<DepositAccount> getDepositAccount() {
        return depositAccount;
    }

    public void setDepositAccount(List<DepositAccount> depositAccount) {
        this.depositAccount = depositAccount;
    }
    
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }


    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    public Long getAtmCardId() {
        return atmCardId;
    }

    public void setAtmCardId(Long atmCardId) {
        this.atmCardId = atmCardId;
    }
}
