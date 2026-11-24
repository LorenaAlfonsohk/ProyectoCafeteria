package cafecentral.patrones.observer;

import cafecentral.modelo.Pedido;

public class MeseroObserver implements ObservadorPedido {

    @Override
    public void actualizar(Pedido pedido) {
        System.out.println(
            "MESERO: El pedido #" + pedido.getId()
            + " cambio a estado: " + pedido.getEstado()
        );
    }
}
