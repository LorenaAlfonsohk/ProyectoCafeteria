package cafecentral.patrones.strategy;

public class PromocionLunes implements PromocionStrategy {

    @Override
    public double aplicarDescuento(double total) {
        return total - (total * 15 / 100);
    }

    @Override
    public String getNombre() {
        return "Promoción lunes (15%)";
    }
}
