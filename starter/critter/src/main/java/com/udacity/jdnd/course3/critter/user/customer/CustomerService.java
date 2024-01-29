package com.udacity.jdnd.course3.critter.user.customer;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Najed Alseghair at 1/26/2024
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    public CustomerService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    @Transactional
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer toBeSaved = CustomerDTO.mapToEntity(customerDTO);
        if (customerDTO.getPetIds() != null && !customerDTO.getPetIds().isEmpty())
            toBeSaved.setOwningPets(petRepository.findAllById(customerDTO.getPetIds()));
        return CustomerDTO.mapToDto(customerRepository.save(toBeSaved));
    }

    @Transactional
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerDTO::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Customer getOwnerByPet(long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(IllegalArgumentException::new);
        return customerRepository.findByOwningPetsContains(pet)
                .orElseThrow(IllegalArgumentException::new);
    }

    public Customer getCustomer(long customerId) {
        return customerRepository.findById(customerId).orElseThrow(IllegalArgumentException::new);
    }
}
