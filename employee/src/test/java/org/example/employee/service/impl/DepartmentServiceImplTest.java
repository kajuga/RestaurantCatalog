package org.example.employee.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.employee.AbstractIT;
import org.example.employee.dto.request.DepartmentRequestDTO;
import org.example.employee.dto.response.DepartmentResponseDTO;
import org.example.employee.service.DepartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class DepartmentServiceImplTest extends AbstractIT {

    @Autowired
    private DepartmentService departmentService;


    @Test
    void createDepartmentTest() {
        DepartmentRequestDTO departmentDto = new DepartmentRequestDTO();
        departmentDto.setName("Головной отдел");
        DepartmentResponseDTO savedDepartment = departmentService.createDepartment(departmentDto);
        Assertions.assertNotNull(savedDepartment.getId());


    }



}
