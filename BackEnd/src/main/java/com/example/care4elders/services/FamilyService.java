package com.example.care4elders.services;

import com.example.care4elders.controllers.DTO.FamilyUpdateRequest;
import com.example.care4elders.model.Family;

import java.util.List;
import java.util.Optional;

public interface FamilyService {


    Family save(Family family);

    boolean deleteById(Long id);

    Family updatePassword(long id, String password);

    boolean checkFamilyExists(Long id, String relative, String password);

    boolean updateFamily(Long id, Family updated);

    Family findById(Long id);

    Family addFamily(Family family);

    List<Family> addFamilies(List<Family> families);

    List<Family> getAllFamilies();

    Optional<Family> getFamilyById(Long id);

    void deleteAllFamilies();

    Optional<Family> getFamilysByFamilyId(Long familyId);

    Family updateInvestissement(Long familyId, float newInvest);

    Family updateAdvice(Long familyId, String newAdvice);

    Family resetLogin(Long familyId);

    Family incrementLogin(Long familyId);

    Optional<Family> updateFamilyFields(Long id, FamilyUpdateRequest updateRequest);

    Optional<Long> getFamilyIdByFamilyName(String familyName);
}
