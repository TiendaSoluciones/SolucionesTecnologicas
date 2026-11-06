package service;

import dao.DispositivoRepository;
import domain.Dispositivo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DispositivoService {

    @Autowired
    private DispositivoRepository dispositivoRepository;

    public List<Dispositivo> listarDispositivos() {
        return dispositivoRepository.findAll();
    }

    public Dispositivo buscarDispositivo(Integer id) {
        return dispositivoRepository.findById(id).orElse(null);
    }

    public void guardarDispositivo(Dispositivo dispositivo) {
        dispositivoRepository.save(dispositivo);
    }

    public void eliminarDispositivo(Integer id) {
        dispositivoRepository.deleteById(id);
    }
}
