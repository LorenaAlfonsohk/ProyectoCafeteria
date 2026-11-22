package cafecentral.patrones.strategy;

public class DescuentoPorcentaje implements PromocionStrategy {

    private String nombre;
    private double porcentaje;

    public DescuentoPorcentaje(String nombre, double porcentaje) {
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }

    @Override
    public double calcularTotal(double subtotal) {
        double descuento = subtotal * porcentaje / 100;
        return subtotal - descuento;
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}
