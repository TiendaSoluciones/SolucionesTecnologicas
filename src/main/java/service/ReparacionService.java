package service;

import dao.ReparacionRepository;
import domain.Reparacion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReparacionService {

    @Autowired
    private ReparacionRepository reparacionRepository;

    public List<Reparacion> listarReparaciones() {
        return reparacionRepository.findAll();
    }

    public List<Reparacion> listarPorCliente(Integer clienteId) {
        return reparacionRepository
                .findByCliente_IdOrderByFechaSolicitudDesc(clienteId);
    }

    public Reparacion buscarReparacion(Integer id) {
        return reparacionRepository.findById(id).orElse(null);
    }

    public void guardarReparacion(Reparacion reparacion) {
        reparacionRepository.save(reparacion);
    }

    public void eliminarReparacion(Integer id) {
        reparacionRepository.deleteById(id);
    }
}