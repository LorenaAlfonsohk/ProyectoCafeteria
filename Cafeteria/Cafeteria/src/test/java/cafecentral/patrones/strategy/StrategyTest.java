package cafecentral.patrones.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StrategyTest {

    @Test
    void lunesAplicaQuincePorCiento() {
        PromocionStrategy promocion = new PromocionLunes();
        assertEquals(17000, promocion.aplicarDescuento(20000));
    }

    @Test
    void horaFelizAplicaDiezPorCiento() {
        PromocionStrategy promocion = new PromocionHoraFeliz();
        assertEquals(18000, promocion.aplicarDescuento(20000));
    }

    @Test
    void sinPromocionNoCambiaElTotal() {
        PromocionStrategy promocion = new SinPromocion();
        assertEquals(20000, promocion.aplicarDescuento(20000));
    }
}
