package com.mindex.challenge;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.Report;
import com.mindex.challenge.service.EmployeeService;
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
public class ChallengeApplicationTests {
	private static final Logger LOG = LoggerFactory.getLogger(DataBootstrap.class);

	@Test
	public void contextLoads() {
	}

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService eService;

	/**
	 * Test to check employee Read service
	 */
	@Test
	public void employeeRead() {
		Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
		assertNotNull(employee);
		assertEquals("John", employee.getFirstName());
		assertEquals("Lennon", employee.getLastName());
		assertEquals("Development Manager", employee.getPosition());
		assertEquals("Engineering", employee.getDepartment());
		LOG.debug("Employee Read tested for id [{}]", employee.getEmployeeId());
	}

	/**
	 * Test to check employee Write service
	 */
	@Test
	public void employeeWrite() {
		Employee employee = new Employee();
		employee.setFirstName("Nikhil");
		employee.setLastName("Chaudhuri");
		employee.setPosition("Backend Developer");
		employee.setDepartment("Software");
		employee=employeeRepository.insert(employee);
		employee = employeeRepository.findByEmployeeId(employee.getEmployeeId());
		assertNotNull(employee);
		assertEquals("Nikhil", employee.getFirstName());
		assertEquals("Chaudhuri", employee.getLastName());
		assertEquals("Backend Developer", employee.getPosition());
		assertEquals("Software", employee.getDepartment());
		LOG.debug("Employee Write tested complete for id [{}]", employee.getEmployeeId());
	}

	/**
	 * Test to check employee Report count service
	 */
	@Test
	public void employeeReports() {
		Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
		Report report=eService.readReport(employee.getEmployeeId());
		assertEquals(4, report.getNumberOfReports());
		assertEquals("John", report.getEmployee().getFirstName());
		assertEquals("Lennon", report.getEmployee().getLastName());
		LOG.debug("Employee Report tested complete for id [{}]", employee.getEmployeeId());
	}

}
