package br.com.postech_farmabusca.controller;

import br.com.postech_farmabusca.commoms.exception.UnprocessableEntityException;
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
import java.util.Optional;
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
        return mapper.toResponse(medicationService.createMedication(medication));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MedicationResponse getMedication(@PathVariable Long id) {
        return mapper.toResponse(medicationService.getMedication(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MedicationResponse> getAllMedications() {
        return medicationService.getAllMedications().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MedicationResponse updateMedication(@PathVariable Long id,
                                               @RequestBody @Valid UpdateMedicationRequest request) {
        Medication existingMedication = medicationService.getMedication(id);

        // Verifica se outro medicamento já tem o mesmo nome
        if (request.getName() != null &&
                !request.getName().equals(existingMedication.getName()) &&
                medicationService.getAllMedications().stream()
                        .anyMatch(m -> m.getName().equals(request.getName()))) {
            throw new UnprocessableEntityException("Já existe um medicamento com esse nome.");
        }

        Medication updatedData = new Medication();
        updatedData.setId(existingMedication.getId()); // Mantém o ID
        updatedData.setName(Optional.ofNullable(request.getName()).orElse(existingMedication.getName()));
        updatedData.setDescription(Optional.ofNullable(request.getDescription()).orElse(existingMedication.getDescription()));

        return mapper.toResponse(medicationService.updateMedication(id, updatedData));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
    }
}
