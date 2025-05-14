package com.example.care4elders.Implementations;

import com.example.care4elders.model.Appointment;
import com.example.care4elders.repository.AppointmentRepository;
import com.example.care4elders.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public boolean deleteById(Long id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAppointment(Long id, Appointment updated) {
        Optional<Appointment> optional = appointmentRepository.findById(id);
        if (optional.isPresent()) {
            Appointment existing = optional.get();
            existing.setDate(updated.getDate());
            existing.setTariff(updated.getTariff());
            existing.setNature(updated.getNature());
            existing.setPeriod(updated.getPeriod());
            existing.setDoctor(updated.getDoctor());
            existing.setfamily(updated.getfamily());
            existing.setEmergency(updated.getEmergency());
            existing.setQuoted(updated.isQuoted());
            existing.setDescription(updated.getDescription());
            existing.setSkipped(updated.isSkipped());

            appointmentRepository.save(existing);
            return true;
        }
        return false;
    }

    @Override
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteAllAppointments() {
        appointmentRepository.deleteAll();
    }
}
