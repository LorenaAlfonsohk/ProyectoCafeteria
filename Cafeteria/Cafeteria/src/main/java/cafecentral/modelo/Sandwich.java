package cafecentral.modelo;

public class Sandwich implements Producto {

    @Override
    public String getNombre() {
        return "Sándwich";
    }

    @Override
    public double getPrecio() {
        return 8000;
    }
}
