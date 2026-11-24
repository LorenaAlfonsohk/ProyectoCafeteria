package cafecentral.patrones.observer;

import cafecentral.modelo.Pedido;

public class CocinaObserver implements ObservadorPedido {

    @Override
    public void actualizar(Pedido pedido) {
        System.out.println(
            "COCINA: El pedido #" + pedido.getId()
            + " cambio a estado: " + pedido.getEstado()
        );
    }
}
