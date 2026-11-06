package dao;

import domain.Reparacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReparacionRepository extends JpaRepository<Reparacion, Integer> {

    List<Reparacion> findByCliente_IdOrderByFechaSolicitudDesc(Integer clienteId);

}
