package org.example.employee.service.impl;

import org.example.employee.AbstractIT;
import org.example.employee.dto.request.DepartmentRequestDTO;
import org.example.employee.dto.response.DepartmentResponseDTO;
import org.example.employee.error.MultiValidationException;
import org.example.employee.error.NotFoundRecordException;
import org.example.employee.service.DepartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class DepartmentServiceImplTest extends AbstractIT {

    @Autowired
    private DepartmentService departmentService;

    @Transactional
    @Test
    void createDepartmentTest() {
        DepartmentRequestDTO departmentDto = new DepartmentRequestDTO();
        departmentDto.setName("Головной отдел");
        DepartmentResponseDTO savedDepartment = departmentService.createDepartment(departmentDto);
        Assertions.assertNotNull(savedDepartment.getId());
        assertEquals(departmentDto.getName(), savedDepartment.getName());
        assertNull(savedDepartment.getChief());
    }

    @Transactional
    @Test
    void whenAddExistedDepartmentWeCatchError() {
        Assertions.assertThrows(MultiValidationException.class, () -> {
            DepartmentRequestDTO departmentDtoWithUncorrectName = new DepartmentRequestDTO();
            departmentDtoWithUncorrectName.setName("Головной отдел666");
            DepartmentResponseDTO savedDepartment = departmentService.createDepartment(departmentDtoWithUncorrectName);
        });
    }

    @Transactional
    @Test
    void whenGetDepartmentByIdWeGotDepartment() {
        DepartmentRequestDTO departmentDto = new DepartmentRequestDTO();
        departmentDto.setName("Отдел тестирования");
        DepartmentResponseDTO savedDepartment = departmentService.createDepartment(departmentDto);
        Assertions.assertNotNull(savedDepartment.getId());
        Long id = savedDepartment.getId();
        DepartmentResponseDTO findedDepartment = departmentService.getDepartmentById(id);
        assertEquals(savedDepartment, findedDepartment);
    }

    @Transactional
    @Test
    void whenTryFindDepartmentByIncorrectIdWeGotNotFoundError() {
        Long incorrectId = 666L;
        DepartmentRequestDTO departmentDto = new DepartmentRequestDTO();
        departmentDto.setName("Отдел глубокого бурения");
        departmentService.createDepartment(departmentDto);
        List<DepartmentResponseDTO> responseDTOList = departmentService.getDepartments();
        assertFalse(responseDTOList.isEmpty());
        assertFalse(responseDTOList.stream().anyMatch(c -> c.getId().equals(incorrectId)));
        Assertions.assertThrows(NotFoundRecordException.class, () -> {
            departmentService.getDepartmentById(incorrectId);
        });
    }

    @Transactional
    @Test
    void whenTryingToGetAllDepartmentsWeGotListOfDepartments() {
        List<DepartmentResponseDTO> responseDTOList = departmentService.getDepartments();
        assertTrue(responseDTOList.isEmpty());
        DepartmentRequestDTO firstDepartment = new DepartmentRequestDTO();
        String firstDepartmentName = "Первый отдел";
        String secondDepartmentName = "Второй отдел";
        firstDepartment.setName(firstDepartmentName);
        DepartmentRequestDTO secondDepartment = new DepartmentRequestDTO();
        secondDepartment.setName(secondDepartmentName);
        departmentService.createDepartment(firstDepartment);
        departmentService.createDepartment(secondDepartment);
        assertFalse((departmentService.getDepartments()).isEmpty());
        List<DepartmentResponseDTO> departmentResponseDTOS = departmentService.getDepartments();
        assertEquals(2, departmentResponseDTOS.size());
        assertTrue(departmentResponseDTOS.stream().anyMatch(c -> c.getName().equals(firstDepartmentName)));
        assertTrue(departmentResponseDTOS.stream().anyMatch(c -> c.getName().equals(secondDepartmentName)));
    }

    @Transactional
    @Test
    void WhenUpdateDepartmentGotUpdatedDepartment() {
        assertTrue(departmentService.getDepartments().isEmpty());
        DepartmentRequestDTO firstDepartment = new DepartmentRequestDTO();
        String firstDepartmentName = "Первый отдел первый";
        firstDepartment.setName(firstDepartmentName);
        departmentService.createDepartment(firstDepartment);
        List<DepartmentResponseDTO> departmentResponseDTOS = departmentService.getDepartments();
        assertEquals(1, departmentResponseDTOS.size());
        DepartmentRequestDTO updateDepartmentRequestDTO = new DepartmentRequestDTO();
        updateDepartmentRequestDTO.setName("Первый отдел исправленный");
        updateDepartmentRequestDTO.setId(departmentResponseDTOS.get(0).getId());
        DepartmentResponseDTO updatedResponseDepartment = departmentService.updateDepartment(updateDepartmentRequestDTO);
        assertEquals(1, departmentService.getDepartments().size());
        assertEquals("Первый отдел исправленный", updatedResponseDepartment.getName());
    }

    @Transactional
    @Test
    void WhenDeleteDepartmentThenDeleteIt() {
        assertTrue(departmentService.getDepartments().isEmpty());
        DepartmentRequestDTO firstDepartment = new DepartmentRequestDTO();
        String firstDepartmentName = "Первый отдел уничтожение";
        firstDepartment.setName(firstDepartmentName);
        DepartmentResponseDTO createdDepartment = departmentService.createDepartment(firstDepartment);
        List<DepartmentResponseDTO> departmentDeleteResponseDTOS = departmentService.getDepartments();
        assertEquals(1, departmentDeleteResponseDTOS.size());
        Long idToDelete = createdDepartment.getId();
        departmentService.deleteDepartment(idToDelete);
        List<DepartmentResponseDTO> departmentAfterDeleteResponseDTOS = departmentService.getDepartments();
        assertEquals(0, departmentAfterDeleteResponseDTOS.size());

    }

    @Transactional
    @Test
    void WhenTryDeleteDepartmentByNonexistentIdCatchError() {
        assertTrue(departmentService.getDepartments().isEmpty());
        Assertions.assertThrows(NotFoundRecordException.class, () -> {
            departmentService.getDepartmentById(1L);
        });
    }
}
