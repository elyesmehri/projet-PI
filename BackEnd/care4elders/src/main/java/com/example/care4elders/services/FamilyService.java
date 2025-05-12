package com.example.care4elders.services;

import com.example.care4elders.model.Doctor;
import com.example.care4elders.model.Family;

import java.util.List;

public interface FamilyService {

    Family saveFamily(Family family);

    List<Family> getAllFamilies();

    Family getFamilyByName(String familyname);

    boolean deleteFamilyByName(String familyname);

    public Family updateFamilyname(String oldName, String newName);

    Family updateInvestissement(String familyname, float newInvest);

    List<Family> addFamiliesBulk(List<Family> family);

    void deletefamilybulk(List<Long> ids);

    void deleteAllFamilies();

    Family updatePassword(String familyname, String password);

    Family updateAdvice(String familyname, String advice);

    boolean checkFamilyExists(String familyname, String personInCharge, String password);

    Family resetLogin(String familyName);

    Family incrementLogin(String familyName);

}
