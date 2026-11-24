package cafecentral.consola;

import cafecentral.modelo.Cafe;
import cafecentral.modelo.Pedido;
import cafecentral.patrones.decorator.LecheDeslactosada;
import cafecentral.patrones.decorator.ShotAdicional;
import cafecentral.patrones.strategy.DescuentoPorcentaje;
import cafecentral.servicios.PedidoService;
import cafecentral.patrones.observer.CocinaObserver;
import cafecentral.patrones.observer.CajaObserver;
import cafecentral.patrones.observer.MeseroObserver;
import cafecentral.modelo.EstadoPedido;

public class MainConsola {

    public static void main(String[] args) {

        PedidoService servicio = new PedidoService();

        Pedido pedido = servicio.crearPedido();
        CocinaObserver cocina = new CocinaObserver();
        CajaObserver caja = new CajaObserver();
        MeseroObserver mesero = new MeseroObserver();

        pedido.agregarObservador(cocina);
        pedido.agregarObservador(caja);
        pedido.agregarObservador(mesero);


        

        Cafe cafe = new Cafe("Cafe americano", 4000);

        LecheDeslactosada cafeConLeche = new LecheDeslactosada(cafe);
        ShotAdicional cafeCompleto = new ShotAdicional(cafeConLeche);

        pedido.agregarProducto(cafeCompleto, 1);
        pedido.setPromocion(new DescuentoPorcentaje("Descuento del 10%", 10));

        System.out.println("Pedido numero: " + pedido.getId());
        System.out.println("Producto: " + cafeCompleto.getDescripcion());
        System.out.println("Subtotal: $" + pedido.calcularSubtotal());
        System.out.println("Promocion: " + pedido.getNombrePromocion());
        System.out.println("Total: $" + pedido.calcularTotal());

        System.out.println("\n--- CAMBIANDO ESTADO DEL PEDIDO ---");

        pedido.cambiarEstado(EstadoPedido.EN_PREPARACION);

        pedido.cambiarEstado(EstadoPedido.LISTO);

        pedido.cambiarEstado(EstadoPedido.ENTREGADO);
    }
}
