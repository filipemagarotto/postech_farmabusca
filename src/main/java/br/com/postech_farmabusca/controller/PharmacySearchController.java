package br.com.postech_farmabusca.controller;

import br.com.postech_farmabusca.core.service.PharmacySearchService;
import br.com.postech_farmabusca.resources.entities.PharmacyMedicationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class PharmacySearchController {

    private final PharmacySearchService pharmacySearchService;

    @GetMapping
    public ResponseEntity<List<PharmacyMedicationEntity>> searchPharmacies(
            @RequestParam String medicationName,
            @RequestParam(required = false) Integer zipCode,
            Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            zipCode = ((br.com.postech_farmabusca.resources.entities.UserEntity) user).getZipCode();
        }

        if (zipCode == null) {
            return ResponseEntity.badRequest().body(null);
        }

        List<PharmacyMedicationEntity> pharmacies = pharmacySearchService.searchPharmacies(medicationName, zipCode);
        return ResponseEntity.ok(pharmacies);
    }
}
