package ejb.session.singleton;

import ejb.session.stateless.EmployeeSessionBeanLocal;
import entity.Employee;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.EmployeeNotFoundException;



@Singleton
@LocalBean
@Startup

public class DataInitSessionBean 
{    
    @EJB
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;

    public DataInitSessionBean() 
    {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        try
        {
            employeeSessionBeanLocal.retrieveEmployeeById(1l);
        }
        catch(EmployeeNotFoundException ex)
        {
            loadTestData();
        }
    }
    
    
    
    private void loadTestData()
    {
        employeeSessionBeanLocal.createNewEmployee(new Employee("Employee", "One", "employee1", "password", EmployeeAccessRightEnum.TELLER));
        employeeSessionBeanLocal.createNewEmployee(new Employee("Employee", "Two",  "employee2", "password", EmployeeAccessRightEnum.TELLER));
        employeeSessionBeanLocal.createNewEmployee(new Employee("Employee", "Three", "employee3", "password", EmployeeAccessRightEnum.TELLER));
    }
    
}