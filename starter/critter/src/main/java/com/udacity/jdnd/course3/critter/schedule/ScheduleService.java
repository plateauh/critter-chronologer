package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Najed Alseghair at 1/28/2024
 */
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;
    private final EmployeeRepository employeeRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, PetRepository petRepository, EmployeeRepository employeeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule toBeCreated = ScheduleDTO.mapToEntity(scheduleDTO);
        toBeCreated.setPets(petRepository.findAllById(scheduleDTO.getPetIds()));
        toBeCreated.setEmployees(employeeRepository.findAllById(scheduleDTO.getEmployeeIds()));
        return ScheduleDTO.mapToDto(scheduleRepository.save(toBeCreated));
    }

    @Transactional
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        return scheduleList.stream()
                .map(ScheduleDTO::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ScheduleDTO> getScheduleForPet(long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(IllegalArgumentException::new);
        List<Schedule> scheduleList = scheduleRepository.findByPetsContaining(pet);
        return scheduleList.stream()
                .map(ScheduleDTO::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(IllegalArgumentException::new);
        List<Schedule> scheduleList = scheduleRepository.findByEmployeesContaining(employee);
        return scheduleList.stream()
                .map(ScheduleDTO::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
        List<Long> petsIds = petRepository.findByOwnerId(customerId).stream().map(Pet::getId).collect(Collectors.toList());
        List<Schedule> scheduleList = scheduleRepository.findByPetsIdIn(petsIds);
        return scheduleList.stream()
                .map(ScheduleDTO::mapToDto)
                .collect(Collectors.toList());
    }
}
