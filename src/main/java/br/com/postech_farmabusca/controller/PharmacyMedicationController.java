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
        PharmacyMedication stock = mapper.toDomain(request);
        return mapper.toResponse(service.createStock(stock));
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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PharmacyMedicationResponse updateStock(@PathVariable Long id,
                                                  @RequestBody @Valid UpdatePharmacyMedicationRequest request) {
        PharmacyMedication updatedStock = mapper.toDomain(request);
        return mapper.toResponse(service.updateStock(id, updatedStock));
    }
}