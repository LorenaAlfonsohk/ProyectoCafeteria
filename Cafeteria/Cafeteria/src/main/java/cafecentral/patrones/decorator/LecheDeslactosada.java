package cafecentral.patrones.decorator;

import cafecentral.modelo.Producto;

public class LecheDeslactosada extends DecoradorProducto {

    public LecheDeslactosada(Producto producto) {
        super(producto);
    }

    @Override
    public String getNombre() {
        return producto.getNombre() + " con leche deslactosada";
    }

    @Override
    public double getPrecio() {
        return producto.getPrecio() + 1000;
    }
}
