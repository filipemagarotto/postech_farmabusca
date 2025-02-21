package br.com.postech_farmabusca.controller;

import br.com.postech_farmabusca.commoms.mappers.MedicationMapper;
import br.com.postech_farmabusca.controller.dto.medication.CreateMedicationRequest;
import br.com.postech_farmabusca.controller.dto.medication.MedicationResponse;
import br.com.postech_farmabusca.controller.dto.medication.UpdateMedicationRequest;
import br.com.postech_farmabusca.core.domain.Medication;
import br.com.postech_farmabusca.core.service.MedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medications")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;
    private final MedicationMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicationResponse createMedication(@RequestBody @Valid CreateMedicationRequest request) {
        Medication medication = mapper.toDomain(request);

        return mapper.toResponse(         medicationService.createMedication(medication));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MedicationResponse getMedication(@PathVariable Long id) {
        Medication medication = medicationService.getMedication(id);
        return mapper.toResponse(medication);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MedicationResponse> getAllMedications() {
        List<Medication> medications = medicationService.getAllMedications();
        return medications.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MedicationResponse updateMedication(@PathVariable Long id,
                                               @RequestBody UpdateMedicationRequest request) {
        Medication updatedData = mapper.toDomain(request);
        Medication updated = medicationService.updateMedication(id, updatedData);
        return mapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
    }
}
