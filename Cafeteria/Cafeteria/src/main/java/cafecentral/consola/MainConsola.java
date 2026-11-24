package cafecentral.consola;

import cafecentral.modelo.*;
import cafecentral.patrones.decorator.*;
import cafecentral.patrones.strategy.*;
import cafecentral.servicios.PedidoService;
import java.util.Scanner;

public class MainConsola {
    private static Scanner teclado = new Scanner(System.in);
    private static PedidoService servicio = new PedidoService();

    public static void main(String[] args) {
        int opcion = 0;

        while (opcion != 6) {
            System.out.println("\n===== CAFETERIA =====");
            System.out.println("1. Ver productos");
            System.out.println("2. Crear pedido");
            System.out.println("3. Consultar pedidos");
            System.out.println("4. Cambiar estado de pedido");
            System.out.println("5. Consultar ventas entregadas");
            System.out.println("6. Salir");
            System.out.print("Elige una opcion: ");
            opcion = leerNumero();

            switch (opcion) {
                case 1:
                    verProductos();
                    break;
                case 2:
                    crearPedido();
                    break;
                case 3:
                    consultarPedidos();
                    break;
                case 4:
                    cambiarEstado();
                    break;
                case 5:
                    consultarVentas();
                    break;
                case 6:
                    System.out.println("Gracias por usar Cafeteria.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private static int leerNumero() {
        while (!teclado.hasNextInt()) {
            System.out.print("Escribe un numero: ");
            teclado.nextLine();
        }
        int numero = teclado.nextInt();
        teclado.nextLine();
        return numero;
    }

    private static void verProductos() {
        System.out.println("\nPRODUCTOS");
        System.out.println("1. Cafe - $4.000");
        System.out.println("2. Sandwich - $8.000");
        System.out.println("3. Postre - $5.000");
    }

    private static void crearPedido() {
        Pedido pedido = servicio.crearPedido();
        int opcion = 0;

        System.out.println("\nCreando pedido #" + pedido.getId());

        while (opcion != 4) {
            verProductos();
            System.out.println("4. Terminar de agregar productos");
            System.out.print("Selecciona un producto: ");
            opcion = leerNumero();

            Producto producto = null;
            if (opcion == 1) {
                producto = new Cafe();
            } else if (opcion == 2) {
                producto = new Sandwich();
            } else if (opcion == 3) {
                producto = new Postre();
            } else if (opcion != 4) {
                System.out.println("Producto no valido.");
            }

            if (producto != null) {
                System.out.print("Cantidad: ");
                int cantidad = leerNumero();
                if (cantidad > 0) {
                    if (opcion == 1) {
                        producto = agregarComplementos(producto);
                    }
                    pedido.agregarProducto(producto, cantidad);
                    System.out.println("Producto agregado.");
                } else {
                    System.out.println("La cantidad debe ser mayor que cero.");
                }
            }
        }

        if (pedido.getProductos().isEmpty()) {
            System.out.println("El pedido quedo vacio y no se guardara.");
            servicio.listarPedidos().remove(pedido);
            return;
        }

        System.out.println("\nSelecciona una promocion:");
        System.out.println("1. Sin promocion");
        System.out.println("2. Lunes - 15%");
        System.out.println("3. Hora feliz - 10%");
        int promo = leerNumero();

        PromocionStrategy estrategia;
        if (promo == 2) {
            estrategia = new PromocionLunes();
        } else if (promo == 3) {
            estrategia = new PromocionHoraFeliz();
        } else {
            estrategia = new SinPromocion();
        }

        pedido.setPromocion(estrategia);
        System.out.println("Subtotal: $" + (int) pedido.getSubtotal());
        System.out.println("Promocion: " + estrategia.getNombre());
        System.out.println("Total: $" + (int) pedido.getTotal());
        System.out.println("Pedido registrado.");
    }

    private static Producto agregarComplementos(Producto producto) {
        int opcion = 0;
        while (opcion != 5) {
            System.out.println("\nComplementos para el cafe:");
            System.out.println("1. Leche deslactosada (+$1.000)");
            System.out.println("2. Shot adicional (+$2.000)");
            System.out.println("3. Jarabe de vainilla (+$1.500)");
            System.out.println("4. Crema batida (+$2.000)");
            System.out.println("5. No agregar mas");
            System.out.print("Opcion: ");
            opcion = leerNumero();

            if (opcion == 1) {
                producto = new LecheDeslactosada(producto);
            } else if (opcion == 2) {
                producto = new ShotAdicional(producto);
            } else if (opcion == 3) {
                producto = new JarabeVainilla(producto);
            } else if (opcion == 4) {
                producto = new CremaBatida(producto);
            } else if (opcion != 5) {
                System.out.println("Opcion no valida.");
            }
        }
        return producto;
    }

    private static void consultarPedidos() {
        if (servicio.listarPedidos().isEmpty()) {
            System.out.println("Todavia no hay pedidos.");
            return;
        }

        for (Pedido pedido : servicio.listarPedidos()) {
            System.out.println("\nPedido #" + pedido.getId()
                    + " | Estado: " + pedido.getEstado()
                    + " | Total: $" + (int) pedido.getTotal());
            for (ItemPedido item : pedido.getProductos()) {
                System.out.println("- " + item.getCantidad() + " x "
                        + item.getProducto().getNombre()
                        + " ($" + (int) item.getSubtotal() + ")");
            }
        }
    }

    private static void cambiarEstado() {
        consultarPedidos();
        System.out.print("\nNumero del pedido: ");
        int id = leerNumero();
        Pedido pedido = servicio.buscarPedido(id);

        if (pedido == null) {
            System.out.println("No existe ese pedido.");
            return;
        }

        System.out.println("1. Recibido");
        System.out.println("2. En preparacion");
        System.out.println("3. Listo");
        System.out.println("4. Entregado");
        System.out.print("Nuevo estado: ");
        int opcion = leerNumero();

        if (opcion == 1) {
            servicio.cambiarEstado(id, EstadoPedido.RECIBIDO);
        } else if (opcion == 2) {
            servicio.cambiarEstado(id, EstadoPedido.EN_PREPARACION);
        } else if (opcion == 3) {
            servicio.cambiarEstado(id, EstadoPedido.LISTO);
        } else if (opcion == 4) {
            servicio.cambiarEstado(id, EstadoPedido.ENTREGADO);
        } else {
            System.out.println("Estado no valido.");
            return;
        }
        System.out.println("Estado actualizado.");
    }

    private static void consultarVentas() {
        double total = 0;
        int cantidad = 0;

        for (Pedido pedido : servicio.listarPedidos()) {
            if (pedido.getEstado() == EstadoPedido.ENTREGADO) {
                total += pedido.getTotal();
                cantidad++;
            }
        }

        System.out.println("Pedidos entregados: " + cantidad);
        System.out.println("Total de ventas entregadas: $" + (int) total);
    }
}
