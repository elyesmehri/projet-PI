package com.example.care4elders.Implementations;


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
    public Family saveFamily(Family family) {
        return familyRepository.save(family);
    }

    @Override
    public List<Family> getAllFamilies() {
        return familyRepository.findAll();
    }

    @Override
    public Family getFamilyByName(String familyname) {
        return familyRepository.findByFamilyname(familyname)
                .orElseThrow(() -> new EntityNotFoundException("Family not found"));
    }

    @Override
    public boolean deleteFamilyByName(String familyname) {
        return familyRepository.findByFamilyname(familyname).map(family -> {
            familyRepository.delete(family);
            return true;
        }).orElse(false);
    }

    @Override
    public Family updateFamilyname(String oldName, String newName) {
        Family family = familyRepository.findByFamilyname(oldName)
                .orElseThrow(() -> new EntityNotFoundException("Family not found"));

        family.setFamilyname(newName);
        return familyRepository.save(family);
    }

    @Override
    public Family updateInvestissement(String familyname, float newInvest) {
        Family family = familyRepository.findByFamilyname(familyname)
                .orElseThrow(() -> new EntityNotFoundException("Family not found"));
        family.setInvsest(newInvest);
        return familyRepository.save(family);
    }

    @Override
    public List<Family> addFamiliesBulk(List<Family> family) {
        return familyRepository.saveAll(family);
    }

    @Override
    public void deletefamilybulk(List<Long> ids) {
        familyRepository.deleteAllById(ids);
    }

    @Override
    public void deleteAllFamilies() {
        familyRepository.deleteAll();
    }

    @Override
    public Family updatePassword(String familyname, String password) {
        Family family = getFamilyByName(familyname);
        family.setPassword(password);
        return familyRepository.save(family);
    }

    @Override
    public Family updateAdvice(String familyname, String advice) {
        Family family = getFamilyByName(familyname);
        family.setAdvice(advice);
        return familyRepository.save(family);
    }

    @Override
    public boolean checkFamilyExists(String familyname, String relative, String password) {
        boolean familyExists = familyRepository.findByFamilyname(familyname).isPresent();
        boolean personExists = familyRepository.findByRelative(relative).isPresent();
        boolean passwordExists = familyRepository.findByPassword(password).isPresent();

        return familyExists && personExists && passwordExists;
    }


    @Override
    public Family resetLogin(String familyName) {
        Optional<Family> optionalFamily = familyRepository.findByFamilyname(familyName);
        if (optionalFamily.isPresent()) {
            Family family = optionalFamily.get();
            family.setLogin(0); // Remettre la valeur de login à zéro
            return familyRepository.save(family);
        } else {
            return null;
        }
    }

    @Override
    public Family incrementLogin(String familyName) {
        Optional<Family> optionalFamily = familyRepository.findByFamilyname(familyName);
        if (optionalFamily.isPresent()) {
            Family family = optionalFamily.get();
            family.setLogin(family.getLogin() + 1);
            return familyRepository.save(family);
        } else {
            return null;
        }
    }

}
