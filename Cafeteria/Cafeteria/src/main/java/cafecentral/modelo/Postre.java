package cafecentral.modelo;

public class Postre implements Producto {

    @Override
    public String getNombre() {
        return "Postre";
    }

    @Override
    public double getPrecio() {
        return 5000;
    }
}
