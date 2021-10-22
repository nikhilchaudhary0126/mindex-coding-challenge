package com.mindex.challenge;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataBootstrapTest {
    private static final Logger LOG = LoggerFactory.getLogger(DataBootstrap.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    @Test
    public void employeeDataTest() {
        Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        assertNotNull(employee);
        assertEquals("John", employee.getFirstName());
        assertEquals("Lennon", employee.getLastName());
        assertEquals("Development Manager", employee.getPosition());
        assertEquals("Engineering", employee.getDepartment());
    }

    @Test
    public void compensationDataTest() {
        Compensation comp = compensationRepository.findCompensationByEmployeeEmployeeId("b7839309-3348-463b-a7e3-5de1c168beb3");
        assertNotNull(comp);
        assertEquals("Paul", comp.getEmployee().getFirstName());
        assertEquals("McCartney", comp.getEmployee().getLastName());
        assertEquals("Developer I", comp.getEmployee().getPosition());
        assertEquals("Engineering", comp.getEmployee().getDepartment());
        assertEquals(10000, comp.getSalary());
        assertEquals("01/07/2019", comp.getEffectiveDate());
    }

}