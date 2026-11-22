package cafecentral.modelo;

public class Cafe implements Producto {

    private String nombre;
    private double precio;

    public Cafe(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getDescripcion() {
        return nombre;
    }

    @Override
    public double getPrecio() {
        return precio;
    }
}
