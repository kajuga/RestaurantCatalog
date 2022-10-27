package org.example.employee.repository;

import org.example.employee.AbstractIT;
import org.example.employee.model.DepartmentEntity;
import org.example.employee.model.EmployeeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryTest extends AbstractIT {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;




    @Test
    void testCreateEmployee() {
        DepartmentEntity departmentCreated = new DepartmentEntity();
        departmentCreated.setName("AAA");
        DepartmentEntity departmentSaved = departmentRepository.save(departmentCreated);

        EmployeeEntity baseEmployee = new EmployeeEntity();
        baseEmployee.setName("ASD");
        baseEmployee.setEmail("asd@mail.ru");
        baseEmployee.setDepartment(departmentSaved);
        baseEmployee.setSalary(new BigDecimal(12.22));
        EmployeeEntity employee = employeeRepository.save(baseEmployee);
        assertNotNull(employee.getId());

    }






}