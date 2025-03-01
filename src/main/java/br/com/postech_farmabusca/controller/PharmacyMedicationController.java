package br.com.postech_farmabusca.controller;

import br.com.postech_farmabusca.commoms.mappers.PharmacyMedicationMapper;
import br.com.postech_farmabusca.controller.dto.pharmacymedication.CreatePharmacyMedicationRequest;
import br.com.postech_farmabusca.controller.dto.pharmacymedication.PharmacyMedicationResponse;
import br.com.postech_farmabusca.controller.dto.pharmacymedication.UpdatePharmacyMedicationRequest;
import br.com.postech_farmabusca.core.domain.PharmacyMedication;
import br.com.postech_farmabusca.core.service.PharmacyMedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class PharmacyMedicationController {

    private final PharmacyMedicationService service;
    private final PharmacyMedicationMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PharmacyMedicationResponse createStock(@RequestBody @Valid CreatePharmacyMedicationRequest request) {
        PharmacyMedication stock = service.createStock(request);
        return mapper.toResponse(stock);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PharmacyMedicationResponse getStockById(@PathVariable Long id) {
        return mapper.toResponse(service.getStockById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PharmacyMedicationResponse> getAllStocks() {
        return service.getAllStocks().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PharmacyMedicationResponse updateStock(@PathVariable Long id,
                                                  @RequestBody @Valid UpdatePharmacyMedicationRequest request) {
        PharmacyMedication updatedStock = service.updateStock(id, request);
        return mapper.toResponse(updatedStock);
    }

    @GetMapping("/pharmacy/{pharmacyId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PharmacyMedicationResponse> getStockByPharmacy(@PathVariable Long pharmacyId) {
        return service.getStockByPharmacy(pharmacyId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/medication/{medicationId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PharmacyMedicationResponse> getStockByMedication(@PathVariable Long medicationId) {
        return service.getStockByMedication(medicationId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/pharmacy/{pharmacyId}/medication/{medicationId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PharmacyMedicationResponse> getStockByPharmacyAndMedication(
            @PathVariable Long pharmacyId,
            @PathVariable Long medicationId) {

        return service.getStockByPharmacyAndMedication(pharmacyId, medicationId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}