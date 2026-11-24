package cafecentral.patrones.decorator;

import cafecentral.modelo.Cafe;
import cafecentral.modelo.Producto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DecoratorTest {

    @Test
    void cafeTienePrecioInicial() {
        Producto cafe = new Cafe();
        assertEquals(4000, cafe.getPrecio());
    }

    @Test
    void lecheAumentaElPrecio() {
        Producto cafe = new LecheDeslactosada(new Cafe());
        assertEquals(5000, cafe.getPrecio());
        assertEquals("Café con leche deslactosada", cafe.getNombre());
    }

    @Test
    void sePuedenCombinarComplementos() {
        Producto cafe = new ShotAdicional(new LecheDeslactosada(new Cafe()));
        assertEquals(7000, cafe.getPrecio());
    }

    @Test
    void jarabeYCremaAumentanElPrecio() {
        Producto cafe = new CremaBatida(new JarabeVainilla(new Cafe()));
        assertEquals(7500, cafe.getPrecio());
    }
}
