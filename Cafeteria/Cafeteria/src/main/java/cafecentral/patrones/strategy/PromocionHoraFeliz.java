package cafecentral.patrones.strategy;

public class PromocionHoraFeliz implements PromocionStrategy {

    @Override
    public double aplicarDescuento(double total) {
        return total - (total * 10 / 100);
    }

    @Override
    public String getNombre() {
        return "Hora feliz (10%)";
    }
}
