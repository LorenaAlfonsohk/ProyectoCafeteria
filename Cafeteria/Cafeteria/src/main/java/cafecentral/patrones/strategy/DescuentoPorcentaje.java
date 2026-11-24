package cafecentral.patrones.strategy;

public class DescuentoPorcentaje implements PromocionStrategy {
    private double porcentaje;
    private String nombre;

    public DescuentoPorcentaje(double porcentaje, String nombre) {
        this.porcentaje = porcentaje;
        this.nombre = nombre;
    }

    @Override
    public double aplicarDescuento(double total) {
        return total - (total * porcentaje / 100);
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}
