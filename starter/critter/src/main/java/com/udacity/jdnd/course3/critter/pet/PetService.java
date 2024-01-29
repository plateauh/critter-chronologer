package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Najed Alseghair at 1/26/2024
 */
@Service
public class PetService {
    private final PetRepository petRepository;

    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Pet savePet(Pet pet, long ownerId) {
        Customer customer = customerRepository.findById(ownerId).orElseThrow(IllegalArgumentException::new);
        pet.setOwner(customer);
        Pet newlySavedPet = petRepository.save(pet);

        // warning: this block shouldn't be implemented at all!!! But the test couldn't be passed without it...
        List<Pet> siblings = customer.getOwningPets();
        if (siblings == null || siblings.isEmpty()) siblings = new ArrayList<>();
        siblings.add(newlySavedPet);
        customer.setOwningPets(siblings);

        return newlySavedPet;
    }

    @Transactional
    public Pet getPet(long petId) {
        return petRepository.findById(petId).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    @Transactional
    public List<Pet> getPetsByOwner(long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }
}
