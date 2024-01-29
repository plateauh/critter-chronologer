package com.udacity.jdnd.course3.critter.user.employee;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.udacity.jdnd.course3.critter.user.employee.EmployeeSkill.FEEDING;

/**
 * @author Najed Alseghair at 1/26/2024
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee newlyCreated = employeeRepository.save(EmployeeDTO.mapToEntity(employeeDTO));
        return EmployeeDTO.mapToDto(newlyCreated);
    }

    @Transactional
    public Employee getEmployee(long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(IllegalStateException::new);
    }

    @Transactional
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee searchedEmployee = employeeRepository.findById(employeeId).orElseThrow(IllegalStateException::new);
        searchedEmployee.setDaysAvailable(daysAvailable);
    }

    @Transactional
    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
        DayOfWeek dayOfWeek = employeeDTO.getDate().getDayOfWeek();
        List<Employee> employees = employeeRepository.findByDaysAvailableContaining(dayOfWeek);
        return employees.stream()
                .filter(e -> e.getSkills().containsAll(employeeDTO.getSkills()))
                .map(EmployeeDTO::mapToDto)
                .collect(Collectors.toList());
    }
}
