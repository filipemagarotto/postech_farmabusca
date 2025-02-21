package br.com.postech_farmabusca.core.service;

import br.com.postech_farmabusca.commoms.mappers.MedicationMapper;
import br.com.postech_farmabusca.core.domain.Medication;
import br.com.postech_farmabusca.resources.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private  final MedicationMapper mapper;

    public Medication createMedication(Medication medication) {
//Falta regra de negócio

        return mapper.toDomain(medicationRepository.save(mapper.toEntity(medication)));
    }

    public Medication getMedication(Long id) {
        //Falta ajustar
        return mapper.toDomain(medicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado")));
    }

    public List<Medication> getAllMedications() {
        return medicationRepository.findAll().stream()
                .map(m -> mapper.toDomain(m))
                .collect(Collectors.toList());
    }

    @Transactional
    public Medication updateMedication(Long id, Medication updatedMedication) {
        Medication medication = getMedication(id);
        //Falta ajustar
        if (updatedMedication.getName() != null) {
            medication.setName(updatedMedication.getName());
        }
        if (updatedMedication.getDescription() != null) {
            medication.setDescription(updatedMedication.getDescription());
        }
        return mapper.toDomain(medicationRepository.save(mapper.toEntity(medication)));
    }

    @Transactional
    public void deleteMedication(Long id) {
        Medication medication = getMedication(id);
        medicationRepository.delete(mapper.toEntity(medication));
    }
}

