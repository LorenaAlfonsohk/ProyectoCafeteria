package cafecentral.patrones.strategy;

public interface PromocionStrategy {
    double aplicarDescuento(double total);
    String getNombre();
}
