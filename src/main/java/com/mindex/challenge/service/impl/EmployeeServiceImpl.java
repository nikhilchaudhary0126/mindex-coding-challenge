package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.Report;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Reading employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    /**
     *  Returns employee details with reporting employees count for a given employeeID
     * @param id    EmployeeId of input employee
     * @return  Report object with employee details and report count
     */
    @Override
    public Report readReport(String id) {
        LOG.debug("Fetching reporting structure for employee [{}]", id);
        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        return new Report(employee,computeReportCount(id));
    }

    /**
     *  Computes report count value on the fly
     * @param id    EmployeeId of input employee
     * @return Number of reports
     */
    @Override
    public int computeReportCount(String id){
        LOG.debug("Computing report count for employee [{}]", id);

        int counter=0;
        // Stack to traverse object of objects - Space - O(depth)
        Stack<String> stack = new Stack<>();
        stack.push(id);
        // Store visited id's to ensure unique employee count in case of employee reporting to multiple managers.
        HashSet<String> visited=new HashSet<>();
        while (stack.empty()==false){
            String eid=stack.pop();
            List<Employee> employees=employeeRepository.findByEmployeeId(eid).getDirectReports();
            // Check directReports for an employee
            if (employees!=null){
                for (int i=0;i<employees.size();i++) {
                    Employee emp2=employees.get(i);
                    // uniqueness check
                    if (!visited.contains(emp2.getEmployeeId())){
                        stack.push(employees.get(i).getEmployeeId());
                        visited.add(emp2.getEmployeeId());
                        counter+=1;
                    }
                }
            }
        }
        return counter;
    }
}
