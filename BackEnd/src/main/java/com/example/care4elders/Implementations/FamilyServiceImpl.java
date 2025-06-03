package com.example.care4elders.Implementations;


import com.example.care4elders.controllers.DTO.FamilyUpdateRequest;
import com.example.care4elders.model.Family;
import com.example.care4elders.repository.FamilyRepository;
import com.example.care4elders.services.FamilyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {

    private final FamilyRepository familyRepository;

    @Override
    public Family save(Family family) {
        return familyRepository.save(family);
    }

    @Override
    public boolean deleteById(Long id) {
        if (familyRepository.existsById(id)) {
            familyRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public Family updatePassword(long id, String password) {
        Optional<Family> optionalFamily = familyRepository.findById(id); // Use findById, the standard JPA method

        if (optionalFamily.isPresent()) {
            Family family = optionalFamily.get();
            family.setPassword(password);
            return familyRepository.save(family);
        } else {

            return null; // Or throw new ResourceNotFoundException("Family not found with id: " + id);
        }
    }

    @Override
    public boolean checkFamilyExists(Long id, String relative, String password) {
        Optional<Family> familyOptional = familyRepository.findById(id);

        if (familyOptional.isPresent()) {

            Family family = familyOptional.get();

            return family.getRelative().equals(relative) && family.getPassword().equals(password);
        }
        return false;
    }

    @Override
    public boolean updateFamily(Long id, Family updated) {

        Optional<Family> optional = familyRepository.findById(id);

        if (optional.isPresent()) {

            Family existing = optional.get();

            existing.setFamilyname(updated.getFamilyname());
            existing.setPassword(updated.getPassword());

            familyRepository.save(existing);

            return true;
        }
        return false;
    }

    @Override
    public Family findById(Long id) {
        return familyRepository.findById(id).orElse(null);
    }

    @Override
    public Family addFamily(Family family) {
        return familyRepository.save(family);
    }

    @Override
    public List<Family> addFamilies(List<Family> families) {
        return familyRepository.saveAll(families);
    }

    @Override
    public List<Family> getAllFamilies() {
        return familyRepository.findAll();
    }

    @Override
    public Optional<Family> getFamilyById(Long id) {
        return familyRepository.findById(id);
    }

    @Override
    public void deleteAllFamilies() {
        familyRepository.deleteAll();
    }

    @Override
    public Optional<Family> getFamilysByFamilyId(Long familyId) {

        return familyRepository.findById(familyId);
    }

    @Override
    public Family updateInvestissement(Long familyId, float newInvest) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new EntityNotFoundException("Family not found with ID: " + familyId));
        family.setInvest(newInvest);
        return familyRepository.save(family); // Save the updated family
    }

    @Override
    public Family updateAdvice(Long familyId, String newAdvice) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new EntityNotFoundException("Family not found with ID: " + familyId));
        family.setAdvice(newAdvice);
        return familyRepository.save(family); // Save the updated family
    }

    @Override
    public Family resetLogin(Long familyId) {
        Optional<Family> optionalFamily = familyRepository.findById(familyId);
        if (optionalFamily.isPresent()) {
            Family family = optionalFamily.get();
            family.setLogin(0); // Remettre la valeur de login à zéro
            return familyRepository.save(family);
        } else {
            return null;
        }
    }

    @Override
    public Family incrementLogin(Long familyId) {
        Optional<Family> optionalFamily = familyRepository.findById(familyId);
        if (optionalFamily.isPresent()) {
            Family family = optionalFamily.get();
            family.setLogin(family.getLogin() + 1);
            return familyRepository.save(family);
        } else {
            return null;
        }
    }

    @Override
    public Optional<Family> updateFamilyFields(Long id, FamilyUpdateRequest updateRequest) {
        Optional<Family> existingFamilyOpt = familyRepository.findById(id);

        if (existingFamilyOpt.isPresent()) {
            Family existingFamily = existingFamilyOpt.get();

            if (updateRequest.getRelative() != null) {
                existingFamily.setRelative(updateRequest.getRelative());
            }
            if (updateRequest.getRelationship() != null) {
                existingFamily.setRelationship(updateRequest.getRelationship());
            }
            if (updateRequest.getAddress() != null) {
                existingFamily.setAddress(updateRequest.getAddress());
            }
            if (updateRequest.getPhoneNumber() != null) {
                existingFamily.setPhoneNumber(updateRequest.getPhoneNumber());
            }
            if (updateRequest.getAdvice() != null) {
                existingFamily.setAdvice(updateRequest.getAdvice());
            }
            if (updateRequest.getPassword() != null) {
                existingFamily.setPassword(updateRequest.getPassword());
            }

            return Optional.of(familyRepository.save(existingFamily));
        } else {
            return Optional.empty(); // Family not found
        }
    }

    @Override
    public Optional<Long> getFamilyIdByFamilyName(String familyname) {
        // Use the new repository method
        return familyRepository.findByFamilyname(familyname)
                .map(Family::getId); // If a doctor is found, map it to its ID
    }

}

