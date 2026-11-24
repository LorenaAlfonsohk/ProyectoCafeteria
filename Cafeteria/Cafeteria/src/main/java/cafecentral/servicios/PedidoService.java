package cafecentral.servicios;

import cafecentral.modelo.EstadoPedido;
import cafecentral.modelo.Pedido;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private List<Pedido> pedidos;
    private int siguienteId;

    public PedidoService() {
        pedidos = new ArrayList<>();
        siguienteId = 1;
    }

    public Pedido crearPedido() {
        Pedido pedido = new Pedido(siguienteId);
        siguienteId++;
        pedidos.add(pedido);
        return pedido;
    }

    public List<Pedido> listarPedidos() {
        return pedidos;
    }

    public Pedido buscarPedido(int id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        return null;
    }

    public void cambiarEstado(int id, EstadoPedido estado) {
        Pedido pedido = buscarPedido(id);
        if (pedido != null) {
            pedido.cambiarEstado(estado);
        }
    }
}
