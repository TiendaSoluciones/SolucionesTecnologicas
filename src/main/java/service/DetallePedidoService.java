package service;

import dao.DetallePedidoRepository;
import domain.DetallePedido;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public List<DetallePedido> listarDetalles() {
        return detallePedidoRepository.findAll();
    }

    public DetallePedido buscarDetalle(Integer id) {
        return detallePedidoRepository.findById(id).orElse(null);
    }

    public void guardarDetalle(DetallePedido detallePedido) {
        detallePedidoRepository.save(detallePedido);
    }

    public void eliminarDetalle(Integer id) {
        detallePedidoRepository.deleteById(id);
    }
}
