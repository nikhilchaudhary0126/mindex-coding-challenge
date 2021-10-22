# Coding Challenge
## What's Provided
A simple [Spring Boot](https://projects.spring.io/spring-boot/) web application has been created and bootstrapped 
with data. The application contains information about all employees at a company. On application start-up, an in-memory 
Mongo database is bootstrapped with a serialized snapshot of the database. While the application runs, the data may be
accessed and mutated in the database without impacting the snapshot.

### How to Run
The application may be executed by running `gradlew bootRun`.
Please ensure executable permissions on gradlew.

### How to Use
The following endpoints are available to use:
```
* CREATE - Employee
    * HTTP Method: POST 
    * URL: localhost:8080/employee
    * PAYLOAD: Employee
    * RESPONSE: Employee
* READ - Employee
    * HTTP Method: GET 
    * URL: localhost:8080/employee/{id}
    * RESPONSE: Employee
* UPDATE - Employee
    * HTTP Method: PUT 
    * URL: localhost:8080/employee/{id}
    * PAYLOAD: Employee
    * RESPONSE: Employee
```
Below are the new created endpoints for usage:
```
* READ  - ReportStructure
    * HTTP Method: GET 
    * URL: localhost:8080/employee/reportStructure/{id}
    * RESPONSE: Report
* CREATE  - Compensation
    * HTTP Method: POST 
    * URL: localhost:8080/compensation
    * PAYLOAD: Compensation
    * RESPONSE: Compensation
* READ  - Compensation
    * HTTP Method: GET 
    * URL: localhost:8080/compensation/employee/{id}
    * RESPONSE: Compensation
```

The Employee has a JSON schema of:
```json
{
  "type":"Employee",
  "properties": {
    "employeeId": {
      "type": "string"
    },
    "firstName": {
      "type": "string"
    },
    "lastName": {
          "type": "string"
    },
    "position": {
          "type": "string"
    },
    "department": {
          "type": "string"
    },
    "directReports": {
      "type": "array",
      "items" : "string"
    }
  }
}
```
The Report has a JSON schema of:
```json
{
  "type":"Report",
  "properties": {
    "employee": {
      "type": "Employee"
    },
    "numberOfReports": {
      "type": "integer"
    }
 }
```
The Compensation has a JSON schema of:
```json
{
  "type":"Compensation",
  "properties": {
    "employee": {
      "type": "Employee"
    },
    "effectiveDate": {
      "type": "string",
      "format": "mm/dd/yyyy"
    },
    "salary": {
      "type": "integer"
    },
 }
```

For all endpoints that require an "id" in the URL, this is the "employeeId" field.


## TASK 1
Create a new type, ReportingStructure, that has two properties: employee and numberOfReports.

For the field "numberOfReports", this should equal the total number of reports under a given employee. The number of 
reports is determined to be the number of directReports for an employee and all of their distinct reports. For example, 
given the following employee structure:
```
                    John Lennon
                /               \
         Paul McCartney         Ringo Starr
                               /        \
                          Pete Best     George Harrison
```
The numberOfReports for employee John Lennon (employeeId: 16a596ae-edd3-4847-99fe-c4518e82c86f) would be equal to 4.
This new type should have a new REST endpoint created for it. This new endpoint should accept an employeeId and return 
the fully filled out ReportingStructure for the specified employeeId. The values should be computed on the fly and will 
not be persisted.

### SUMMARY
* New REST enpoint(**/employee/reportStructure/{id}**) created.
* The numberOfReports parameter in Report is computed on the fly.
* Unit test case added to test this in ***EmployeeServiceReportImplTest.java***
* Sample output observed on Postman:
<p align="center">
  <img src="https://github.com/nikhilchaudhary0126/mindex-java-code-challenge/blob/main/sampleout/report.png" width="800" title="hover text">
</p>

## TASK 2
Create a new type, Compensation. A Compensation has the following fields: employee, salary, and effectiveDate. Create 
two new Compensation REST endpoints. One to create and one to read by employeeId. These should persist and query the 
Compensation from the persistence layer.
### SUMMARY
* New REST enpoint(**/compensation** and **compensation/employee/{id}**) created for create and read respectively.
* Test case added to ***DataBootstrapTest.java*** to test persistence layer added.
* Unit test case added to test this in ***CompensationServiceImplTest.java***
* Sample output observed on Postman:
<p align="center">
  <img src="https://github.com/nikhilchaudhary0126/mindex-java-code-challenge/blob/main/sampleout/compensation.png" width="800" title="hover text">
</p>

