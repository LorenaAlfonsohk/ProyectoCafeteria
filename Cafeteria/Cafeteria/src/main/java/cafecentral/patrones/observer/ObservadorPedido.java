package cafecentral.patrones.observer;

import cafecentral.modelo.EstadoPedido;

public interface ObservadorPedido {
    void actualizar(int idPedido, EstadoPedido nuevoEstado);
}
