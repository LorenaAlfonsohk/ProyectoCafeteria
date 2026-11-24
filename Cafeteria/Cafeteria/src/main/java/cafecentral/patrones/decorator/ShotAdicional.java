package cafecentral.patrones.decorator;

import cafecentral.modelo.Producto;

public class ShotAdicional extends DecoradorProducto {

    public ShotAdicional(Producto producto) {
        super(producto);
    }

    @Override
    public String getNombre() {
        return producto.getNombre() + " con shot adicional";
    }

    @Override
    public double getPrecio() {
        return producto.getPrecio() + 2000;
    }
}
