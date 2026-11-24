package cafecentral.patrones.decorator;

import cafecentral.modelo.Producto;

public abstract class DecoradorProducto implements Producto {
    protected Producto producto;

    public DecoradorProducto(Producto producto) {
        this.producto = producto;
    }
}
