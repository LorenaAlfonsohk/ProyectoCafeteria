package cafecentral.servicios;

import cafecentral.modelo.EstadoPedido;
import cafecentral.modelo.Pedido;
import java.util.ArrayList;

public class PedidoService {

    private ArrayList<Pedido> pedidos;
    private int siguienteId;

    public PedidoService() {
        pedidos = new ArrayList<>();
        siguienteId = 1;
    }

    public Pedido crearPedido() {
        Pedido pedido = new Pedido(siguienteId);
        pedidos.add(pedido);
        siguienteId++;
        return pedido;
    }

    public ArrayList<Pedido> listarPedidos() {
        return pedidos;
    }

    public Pedido buscarPorId(int id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }

        return null;
    }

    public void cambiarEstado(int id, EstadoPedido nuevoEstado) {
        Pedido pedido = buscarPorId(id);

        if (pedido != null) {
            pedido.cambiarEstado(nuevoEstado);
        }
    }
}
