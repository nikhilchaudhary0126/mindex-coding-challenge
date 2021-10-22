package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationIdUrl;
    private String employeeUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/employee/{id}";
    }

    @Test
    public void testCreateReadUpdate() {

        Employee testEmployee = new Employee();
        testEmployee.setFirstName("Nikhil");
        testEmployee.setLastName("Chaudhary");
        testEmployee.setPosition("Backend Developer");
        testEmployee.setDepartment("Software");

        Employee createdTestEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();


        Compensation testCompensation=new Compensation();
        testCompensation.setEmployee(createdTestEmployee);
        testCompensation.setEffectiveDate("10/10/2010");
        testCompensation.setSalary(100000);

        // Create checks
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation,
                Compensation.class).getBody();

        assertNotNull(createdCompensation.getEmployee().getEmployeeId());
        assertEmployeeEquivalence(testCompensation, createdCompensation);

        // Read checks
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class,
                createdCompensation.getEmployee().getEmployeeId()).getBody();
        assertEquals(createdCompensation.getEmployee().getEmployeeId(), readCompensation.getEmployee().getEmployeeId());
        assertEmployeeEquivalence(createdCompensation, readCompensation);

    }

    private static void assertEmployeeEquivalence(Compensation expected, Compensation actual){
        Employee expectedEmployee=expected.getEmployee();
        Employee compensationEmployee=actual.getEmployee();
        assertEquals(expectedEmployee.getFirstName(), compensationEmployee.getFirstName());
        assertEquals(expectedEmployee.getLastName(), compensationEmployee.getLastName());
        assertEquals(expectedEmployee.getDepartment(), compensationEmployee.getDepartment());
        assertEquals(expectedEmployee.getPosition(), compensationEmployee.getPosition());
        assertEquals(expected.getSalary(),actual.getSalary());
        assertEquals(expected.getEffectiveDate(),actual.getEffectiveDate());
    }
}
