package cafecentral.patrones.strategy;

public class SinPromocion implements PromocionStrategy {

    @Override
    public double aplicarDescuento(double total) {
        return total;
    }

    @Override
    public String getNombre() {
        return "Sin promoción";
    }
}
