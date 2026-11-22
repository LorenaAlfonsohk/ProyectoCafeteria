package cafecentral.patrones.observer;

import cafecentral.modelo.Pedido;

public interface ObservadorPedido {
    void actualizar(Pedido pedido);
}
