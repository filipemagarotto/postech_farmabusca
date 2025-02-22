package br.com.postech_farmabusca.controller;

import br.com.postech_farmabusca.commoms.mappers.PharmacyMapper;
import br.com.postech_farmabusca.controller.dto.pharmacy.CreatePharmacyRequest;
import br.com.postech_farmabusca.controller.dto.pharmacy.PharmacyResponse;
import br.com.postech_farmabusca.controller.dto.pharmacy.UpdatePharmacyRequest;
import br.com.postech_farmabusca.core.domain.Pharmacy;
import br.com.postech_farmabusca.core.service.PharmacyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pharmacies")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;
    private final PharmacyMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PharmacyResponse createPharmacy(@RequestBody @Valid CreatePharmacyRequest request) {
        Pharmacy pharmacy = mapper.toDomain(request);
        return mapper.toResponse(pharmacyService.createPharmacy(pharmacy));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PharmacyResponse getPharmacy(@PathVariable Long id) {
        return mapper.toResponse(pharmacyService.getPharmacy(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PharmacyResponse> getAllPharmacies() {
        return pharmacyService.getAllPharmacies().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PharmacyResponse updatePharmacy(@PathVariable Long id, @RequestBody UpdatePharmacyRequest request) {
        Pharmacy updatedData = mapper.toDomain(request);
        return mapper.toResponse(pharmacyService.updatePharmacy(id, updatedData));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePharmacy(@PathVariable Long id) {
        pharmacyService.deletePharmacy(id);
    }
}
