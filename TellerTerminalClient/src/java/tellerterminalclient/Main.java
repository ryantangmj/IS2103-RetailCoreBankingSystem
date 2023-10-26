/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tellerterminalclient;

import ejb.session.stateless.AccountSessionBeanRemote;
import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author admin
 */
public class Main {

    @EJB
    private static AccountSessionBeanRemote accountSessionBeanRemote;
    @EJB
    private static CustomerSessionBeanRemote customerSessionBeanRemote;
    @EJB
    private static AtmCardSessionBeanRemote atmCardSessionBeanRemote;
    @EJB
    private static EmployeeSessionBeanRemote employeeSessionBeanRemote;
    
//    
    
    public static void main(String[] args)
    {
        MainApp mainApp = new MainApp(accountSessionBeanRemote, customerSessionBeanRemote, atmCardSessionBeanRemote, employeeSessionBeanRemote);
        mainApp.runApp();
    } 
    
}
