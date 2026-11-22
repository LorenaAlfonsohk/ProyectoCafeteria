package cafecentral.patrones.strategy;

public class SinPromocion implements PromocionStrategy {

    @Override
    public double calcularTotal(double subtotal) {
        return subtotal;
    }

    @Override
    public String getNombre() {
        return "Sin promocion";
    }
}
