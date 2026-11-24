package cafecentral.modelo;

import cafecentral.patrones.observer.ObservadorPedido;
import cafecentral.patrones.strategy.PromocionStrategy;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private List<ItemPedido> productos;
    private EstadoPedido estado;
    private List<ObservadorPedido> observadores;
    private PromocionStrategy promocion;

    public Pedido(int id) {
        this.id = id;
        productos = new ArrayList<>();
        observadores = new ArrayList<>();
        estado = EstadoPedido.RECIBIDO;
    }

    public int getId() {
        return id;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        productos.add(new ItemPedido(producto, cantidad));
    }

    public List<ItemPedido> getProductos() {
        return productos;
    }

    public double getSubtotal() {
        double total = 0;
        for (ItemPedido item : productos) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void setPromocion(PromocionStrategy promocion) {
        this.promocion = promocion;
    }

    public double getTotal() {
        if (promocion == null) {
            return getSubtotal();
        }
        return promocion.aplicarDescuento(getSubtotal());
    }

    public void agregarObservador(ObservadorPedido observador) {
        observadores.add(observador);
    }

    public void eliminarObservador(ObservadorPedido observador) {
        observadores.remove(observador);
    }

    public void cambiarEstado(EstadoPedido nuevoEstado) {
        estado = nuevoEstado;
        notificarObservadores();
    }

    private void notificarObservadores() {
        for (ObservadorPedido observador : observadores) {
            observador.actualizar(id, estado);
        }
    }
}
