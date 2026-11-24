package cafecentral.patrones.decorator;

import cafecentral.modelo.Producto;

public class CremaBatida extends DecoradorProducto {

    public CremaBatida(Producto producto) {
        super(producto);
    }

    @Override
    public String getNombre() {
        return producto.getNombre() + " con crema batida";
    }

    @Override
    public double getPrecio() {
        return producto.getPrecio() + 2000;
    }
}
