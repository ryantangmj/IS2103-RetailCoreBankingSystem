/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package automatedtellermachineclient;

import ejb.session.stateless.AccountSessionBeanRemote;
import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author admin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    @EJB
    private static AccountSessionBeanRemote accountSessionBeanRemote;
    @EJB
    private static CustomerSessionBeanRemote customerSessionBeanRemote;
    @EJB
    private static AtmCardSessionBeanRemote atmCardSessionBeanRemote;
    //    
    public static void main(String[] args) {
        // TODO code application logic here
       
        MainApp mainApp = new MainApp(accountSessionBeanRemote, customerSessionBeanRemote, atmCardSessionBeanRemote);
        mainApp.runApp();
        
    }
    
}
