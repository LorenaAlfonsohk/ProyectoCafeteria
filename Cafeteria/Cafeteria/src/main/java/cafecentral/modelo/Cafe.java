package cafecentral.modelo;

public class Cafe implements Producto {

    @Override
    public String getNombre() {
        return "Café";
    }

    @Override
    public double getPrecio() {
        return 4000;
    }
}
