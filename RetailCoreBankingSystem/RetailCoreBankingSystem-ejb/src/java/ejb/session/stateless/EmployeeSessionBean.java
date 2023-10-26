/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
*/

package ejb.session.stateless;

import entity.Employee;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author admin
 */
@Stateless
public class EmployeeSessionBean implements EmployeeSessionBeanLocal, EmployeeSessionBeanRemote {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
 
    @Override
    public Long createNewEmployee(Employee employee)
    {
        em.persist(employee);
        em.flush();
        
        return employee.getEmployeeId();
    }
    
    @Override
    public Employee login(String userName, String password) throws InvalidLoginCredentialException
    {
        try
        {
            Query query = em.createQuery("SELECT e FROM Employee e WHERE e.userName = :userName");
            query.setParameter("userName", userName);
            Employee employee = (Employee)query.getSingleResult();
            
            if(employee.getPassword().equals(password))
            {
                return employee;
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
    public Employee retrieveEmployeeById(Long employeeId) throws EmployeeNotFoundException
    {
        Employee employee = em.find(Employee.class, employeeId);
        
        if(employee != null)
        {
            return employee;
        }
        else
        {
            throw new EmployeeNotFoundException("Employee does not exist: " + employeeId);
        }
    }
}
