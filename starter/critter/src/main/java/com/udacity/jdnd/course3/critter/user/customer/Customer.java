package com.udacity.jdnd.course3.critter.user.customer;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author Najed Alseghair at 1/26/2024
 */
@Entity
public class Customer extends User {
    private String phoneNumber;
    @Lob
    private String notes;
    @OneToMany(mappedBy = "owner")
    private List<Pet> owningPets;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getOwningPets() {
        return owningPets;
    }

    public void setOwningPets(List<Pet> owningPets) {
        this.owningPets = owningPets;
    }
}
