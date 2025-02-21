package br.com.postech_farmabusca.core.service;

import br.com.postech_farmabusca.core.domain.PharmacyMedication;
import br.com.postech_farmabusca.core.domain.Reservation;
import br.com.postech_farmabusca.resources.repository.MedicationRepository;
import br.com.postech_farmabusca.resources.repository.PharmacyMedicationRepository;
import br.com.postech_farmabusca.resources.repository.PharmacyRepository;
import br.com.postech_farmabusca.resources.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final MedicationRepository medicationRepository;
    private final PharmacyRepository pharmacyRepository;
    private final PharmacyMedicationRepository pharmacyMedicationRepository;
    private final ReservationRepository reservationRepository;

    public List<PharmacyMedication> searchMedicationAvailability(String medicationName) {
// Falta adicionar regra de negócio
        throw new UnsupportedOperationException("Método não implementado ainda.");
    }

    @Transactional
    public Reservation createReservation(String userId, Long pharmacyId, Long medicationId, int quantity) {
//Falta implementar regra de negócio
        throw new UnsupportedOperationException("Método não implementado ainda.");
    }

    @Transactional
    public Reservation confirmPickup(Long reservationId) {
        // Falta implementar regra confirmacao reserva
        throw new UnsupportedOperationException("Método não implementado ainda.");
    }

    @Transactional
    public Reservation cancelReservation(Long reservationId) {
        // Falta implementar cancelamento reserva
        throw new UnsupportedOperationException("Método não implementado ainda.");
    }
}
