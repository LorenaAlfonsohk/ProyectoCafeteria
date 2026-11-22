package cafecentral.modelo;

import cafecentral.patrones.observer.ObservadorPedido;
import cafecentral.patrones.strategy.PromocionStrategy;
import cafecentral.patrones.strategy.SinPromocion;
import java.util.ArrayList;

public class Pedido {

    private int id;
    private ArrayList<ItemPedido> productos;
    private ArrayList<ObservadorPedido> observadores;
    private EstadoPedido estado;
    private PromocionStrategy promocion;

    public Pedido(int id) {
        this.id = id;
        productos = new ArrayList<>();
        observadores = new ArrayList<>();
        estado = EstadoPedido.PENDIENTE;
        promocion = new SinPromocion();
    }

    public int getId() {
        return id;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public ArrayList<ItemPedido> getProductos() {
        return productos;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        ItemPedido item = new ItemPedido(producto, cantidad);
        productos.add(item);
    }

    public double calcularSubtotal() {
        double subtotal = 0;

        for (ItemPedido item : productos) {
            subtotal = subtotal + item.getSubtotal();
        }

        return subtotal;
    }

    public void setPromocion(PromocionStrategy promocion) {
        this.promocion = promocion;
    }

    public String getNombrePromocion() {
        return promocion.getNombre();
    }

    public double calcularTotal() {
        return promocion.calcularTotal(calcularSubtotal());
    }

    public void agregarObservador(ObservadorPedido observador) {
        observadores.add(observador);
    }

    public void eliminarObservador(ObservadorPedido observador) {
        observadores.remove(observador);
    }

    public void notificarObservadores() {
        for (ObservadorPedido observador : observadores) {
            observador.actualizar(this);
        }
    }

    public void cambiarEstado(EstadoPedido nuevoEstado) {
        estado = nuevoEstado;
        notificarObservadores();
    }
}
