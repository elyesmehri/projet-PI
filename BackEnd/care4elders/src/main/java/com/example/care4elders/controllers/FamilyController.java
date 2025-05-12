package com.example.care4elders.controllers;

import com.example.care4elders.model.Family;
import com.example.care4elders.services.FamilyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/family")
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping("/bulk")
    public ResponseEntity<List<Family>> createBulkFamily(@RequestBody List<Family> family) {
        try {
            List<Family> saved = familyService.addFamiliesBulk(family);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Family> createFamily(@RequestBody Family family) {
        return ResponseEntity.ok(familyService.saveFamily(family));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Family>> getAllFamilies() {
        return ResponseEntity.ok(familyService.getAllFamilies());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Family> getFamilyByName(@PathVariable String name) {
        return ResponseEntity.ok(familyService.getFamilyByName(name));
    }

    @PutMapping("/updateFamilyname")
    public ResponseEntity<?> updateFamilyname(
            @RequestParam String oldName,
            @RequestParam String newName) {
        try {
            Family updatedFamily = familyService.updateFamilyname(oldName, newName);
            return ResponseEntity.ok(updatedFamily);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Family not found");
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Void> deleteFamily(@PathVariable String name) {
        boolean deleted = familyService.deleteFamilyByName(name);
        if (deleted)  System.out.println ("Family :" + name  + " succesfully deleted !");
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/updateinvest")
    public ResponseEntity<?> updateInvestissement(
            @RequestParam String familyname,
            @RequestParam float invest) {
        try {
            Family updated = familyService.updateInvestissement(familyname, invest);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Family not found");
        }
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<Void> deleteBulkFamily(@RequestBody List<Long> ids) {
        try {
            familyService.deletefamilybulk(ids);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllFamilies() {
        try {
            familyService.deleteAllFamilies();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
   }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestParam String familyname, @RequestParam String password) {
        try {
            Family updated = familyService.updatePassword(familyname, password);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Family not found");
        }
    }

    @PutMapping("/updateAdvice")
    public ResponseEntity<?> updateAdvice(@RequestParam String familyname, @RequestParam String advice) {
        try {
            Family updated = familyService.updateAdvice(familyname, advice);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Family not found");
        }
    }

    @GetMapping("/checkFieldsExist")
    public ResponseEntity<Boolean> CheckFamilyExists(
            @RequestParam String familyname,
            @RequestParam String relative,
            @RequestParam String password) {

        boolean exists = familyService.checkFamilyExists(familyname, relative, password);

        return ResponseEntity.ok(exists);
    }

    @PostMapping("/login/{familyName}")
    public ResponseEntity<Family> incrementLogin(@PathVariable String familyName) {
        Family updatedFamily = familyService.incrementLogin(familyName);
        if (updatedFamily != null) {
            return new ResponseEntity<>(updatedFamily, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/resetlogin/{familyname}")
    public ResponseEntity<Family> resetLogin(@PathVariable String familyname) {
        Family updatedFamily = familyService.resetLogin(familyname);
        if (updatedFamily != null) {
            return new ResponseEntity<>(updatedFamily, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
