/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Employee;
import javax.ejb.Remote;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author admin
 */
@Remote
public interface EmployeeSessionBeanRemote {
    public Long createNewEmployee(Employee employee);

    public Employee login(String userName, String password) throws InvalidLoginCredentialException;

    public Employee retrieveEmployeeById(Long employeeId) throws EmployeeNotFoundException;
}
