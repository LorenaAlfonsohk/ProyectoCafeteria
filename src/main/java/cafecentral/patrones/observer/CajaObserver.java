package cafecentral.patrones.observer;

import cafecentral.modelo.Pedido;

public class CajaObserver implements ObservadorPedido {

    @Override
    public void actualizar(Pedido pedido) {
        System.out.println(
            "CAJA: El pedido #" + pedido.getId()
            + " cambio a estado: " + pedido.getEstado()
        );
    }
}
