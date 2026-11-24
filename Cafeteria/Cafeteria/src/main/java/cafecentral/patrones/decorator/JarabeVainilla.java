package cafecentral.patrones.decorator;

import cafecentral.modelo.Producto;

public class JarabeVainilla extends DecoradorProducto {

    public JarabeVainilla(Producto producto) {
        super(producto);
    }

    @Override
    public String getNombre() {
        return producto.getNombre() + " con jarabe de vainilla";
    }

    @Override
    public double getPrecio() {
        return producto.getPrecio() + 1500;
    }
}
