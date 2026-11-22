package cafecentral.patrones.strategy;

public interface PromocionStrategy {
    double calcularTotal(double subtotal);
    String getNombre();
}
