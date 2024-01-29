package com.udacity.jdnd.course3.critter.pet;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return PetDTO.mapToDto(
                petService.savePet(
                        PetDTO.mapToEntity(petDTO),
                        petDTO.getOwnerId()
                )
        );
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return PetDTO.mapToDto(
                petService.getPet(petId)
        );
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.getPets()
                .stream()
                .map(PetDTO::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getPetsByOwner(ownerId)
                .stream()
                .map(PetDTO::mapToDto)
                .collect(Collectors.toList());
    }
}
