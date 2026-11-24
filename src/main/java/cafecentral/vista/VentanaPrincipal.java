package cafecentral.vista;

import cafecentral.modelo.Cafe;
import cafecentral.modelo.EstadoPedido;
import cafecentral.modelo.ItemPedido;
import cafecentral.modelo.Pedido;
import cafecentral.modelo.Producto;
import cafecentral.patrones.decorator.LecheDeslactosada;
import cafecentral.patrones.decorator.ShotAdicional;
import cafecentral.patrones.observer.CajaObserver;
import cafecentral.patrones.observer.CocinaObserver;
import cafecentral.patrones.observer.MeseroObserver;
import cafecentral.patrones.observer.ObservadorPedido;
import cafecentral.servicios.PedidoService;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame implements ObservadorPedido {

    private PedidoService pedidoService;
    private Pedido pedidoActual;

    private JLabel numeroPedidoLabel;
    private JLabel estadoLabel;
    private JLabel subtotalLabel;
    private JLabel totalLabel;

    private JTextArea productosArea;
    private JTextArea notificacionesArea;

    public VentanaPrincipal() {

        pedidoService = new PedidoService();

        configurarVentana();
        crearInterfaz();
        crearNuevoPedido();
    }

    private void configurarVentana() {

        setTitle("Café Central - Sistema de Gestión");

        setSize(1000, 700);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
    }

    private void crearInterfaz() {

        setLayout(new BorderLayout(10, 10));

        add(crearEncabezado(), BorderLayout.NORTH);

        JPanel centro = new JPanel(
                new GridLayout(1, 2, 10, 10)
        );

        centro.add(crearPanelMenu());
        centro.add(crearPanelPedido());

        add(centro, BorderLayout.CENTER);

        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    private JPanel crearEncabezado() {

        JPanel panel = new JPanel(
                new BorderLayout()
        );

        JLabel titulo = new JLabel(
                "☕ CAFÉ CENTRAL",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        panel.add(titulo, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelMenu() {

        JPanel panel = new JPanel(
                new BorderLayout()
        );

        JLabel titulo = new JLabel(
                "MENÚ DE PRODUCTOS",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        panel.add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(
                new GridLayout(0, 1, 5, 5)
        );

        JButton americanoButton =
                new JButton(
                        "Café Americano - $4.000"
                );

        JButton capuchinoButton =
                new JButton(
                        "Capuchino - $6.000"
                );

        JButton lecheButton =
                new JButton(
                        "Agregar leche deslactosada"
                );

        JButton shotButton =
                new JButton(
                        "Agregar shot adicional"
                );

        JButton nuevoPedidoButton =
                new JButton(
                        "Nuevo pedido"
                );

        americanoButton.addActionListener(
                e -> agregarAmericano()
        );

        capuchinoButton.addActionListener(
                e -> agregarCapuchino()
        );

        lecheButton.addActionListener(
                e -> agregarLeche()
        );

        shotButton.addActionListener(
                e -> agregarShot()
        );

        nuevoPedidoButton.addActionListener(
                e -> crearNuevoPedido()
        );

        botones.add(americanoButton);
        botones.add(capuchinoButton);

        botones.add(
                new JLabel("")
        );

        botones.add(lecheButton);
        botones.add(shotButton);

        botones.add(
                new JLabel("")
        );

        botones.add(nuevoPedidoButton);

        panel.add(
                botones,
                BorderLayout.CENTER
        );

        return panel;
    }

    private JPanel crearPanelPedido() {

        JPanel panel = new JPanel(
                new BorderLayout()
        );

        JLabel titulo = new JLabel(
                "PEDIDO ACTUAL",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        panel.add(
                titulo,
                BorderLayout.NORTH
        );

        productosArea = new JTextArea();

        productosArea.setEditable(false);

        productosArea.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        JScrollPane scroll =
                new JScrollPane(productosArea);

        panel.add(
                scroll,
                BorderLayout.CENTER
        );

        JPanel informacion =
                new JPanel(
                        new GridLayout(4, 1)
                );

        numeroPedidoLabel =
                new JLabel("Pedido #");

        estadoLabel =
                new JLabel("Estado:");

        subtotalLabel =
                new JLabel("Subtotal: $0");

        totalLabel =
                new JLabel("Total: $0");

        informacion.add(numeroPedidoLabel);
        informacion.add(estadoLabel);
        informacion.add(subtotalLabel);
        informacion.add(totalLabel);

        panel.add(
                informacion,
                BorderLayout.SOUTH
        );

        return panel;
    }

    private JPanel crearPanelInferior() {

        JPanel panel = new JPanel(
                new BorderLayout()
        );

        JPanel botonesEstado =
                new JPanel(
                        new GridLayout(1, 4, 5, 5)
                );

        JButton pendienteButton =
                new JButton("PENDIENTE");

        JButton prepararButton =
                new JButton("EN PREPARACIÓN");

        JButton listoButton =
                new JButton("LISTO");

        JButton entregarButton =
                new JButton("ENTREGADO");

        pendienteButton.addActionListener(
                e -> cambiarEstado(
                        EstadoPedido.PENDIENTE
                )
        );

        prepararButton.addActionListener(
                e -> cambiarEstado(
                        EstadoPedido.EN_PREPARACION
                )
        );

        listoButton.addActionListener(
                e -> cambiarEstado(
                        EstadoPedido.LISTO
                )
        );

        entregarButton.addActionListener(
                e -> cambiarEstado(
                        EstadoPedido.ENTREGADO
                )
        );

        botonesEstado.add(pendienteButton);
        botonesEstado.add(prepararButton);
        botonesEstado.add(listoButton);
        botonesEstado.add(entregarButton);

        panel.add(
                botonesEstado,
                BorderLayout.NORTH
        );

        notificacionesArea =
                new JTextArea(7, 20);

        notificacionesArea.setEditable(false);

        notificacionesArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        13
                )
        );

        JScrollPane scroll =
                new JScrollPane(
                        notificacionesArea
                );

        JPanel notificacionesPanel =
                new JPanel(
                        new BorderLayout()
                );

        JLabel titulo =
                new JLabel(
                        "NOTIFICACIONES",
                        SwingConstants.CENTER
                );

        titulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        notificacionesPanel.add(
                titulo,
                BorderLayout.NORTH
        );

        notificacionesPanel.add(
                scroll,
                BorderLayout.CENTER
        );

        panel.add(
                notificacionesPanel,
                BorderLayout.CENTER
        );

        return panel;
    }

    private void crearNuevoPedido() {

        pedidoActual =
                pedidoService.crearPedido();

        CocinaObserver cocina =
                new CocinaObserver();

        CajaObserver caja =
                new CajaObserver();

        MeseroObserver mesero =
                new MeseroObserver();

        pedidoActual.agregarObservador(cocina);

        pedidoActual.agregarObservador(caja);

        pedidoActual.agregarObservador(mesero);

        pedidoActual.agregarObservador(this);

        notificacionesArea.setText("");

        notificacionesArea.append(
                "✓ Pedido #"
                + pedidoActual.getId()
                + " creado correctamente.\n\n"
        );

        actualizarPantalla();
    }

    private void agregarAmericano() {

        Producto cafe =
                new Cafe(
                        "Café Americano",
                        4000
                );

        pedidoActual.agregarProducto(
                cafe,
                1
        );

        actualizarPantalla();
    }

    private void agregarCapuchino() {

        Producto cafe =
                new Cafe(
                        "Capuchino",
                        6000
                );

        pedidoActual.agregarProducto(
                cafe,
                1
        );

        actualizarPantalla();
    }

    private void agregarLeche() {

        if (
                pedidoActual
                        .getProductos()
                        .isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Primero agrega un producto al pedido."
            );

            return;
        }

        int ultimo =
                pedidoActual
                        .getProductos()
                        .size() - 1;

        ItemPedido itemActual =
                pedidoActual
                        .getProductos()
                        .get(ultimo);

        Producto productoActual =
                itemActual.getProducto();

        Producto productoPersonalizado =
                new LecheDeslactosada(
                        productoActual
                );

        ItemPedido nuevoItem =
                new ItemPedido(
                        productoPersonalizado,
                        itemActual.getCantidad()
                );

        pedidoActual
                .getProductos()
                .set(
                        ultimo,
                        nuevoItem
                );

        actualizarPantalla();
    }

    private void agregarShot() {

        if (
                pedidoActual
                        .getProductos()
                        .isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Primero agrega un producto al pedido."
            );

            return;
        }

        int ultimo =
                pedidoActual
                        .getProductos()
                        .size() - 1;

        ItemPedido itemActual =
                pedidoActual
                        .getProductos()
                        .get(ultimo);

        Producto productoActual =
                itemActual.getProducto();

        Producto productoPersonalizado =
                new ShotAdicional(
                        productoActual
                );

        ItemPedido nuevoItem =
                new ItemPedido(
                        productoPersonalizado,
                        itemActual.getCantidad()
                );

        pedidoActual
                .getProductos()
                .set(
                        ultimo,
                        nuevoItem
                );

        actualizarPantalla();
    }

    private void cambiarEstado(
            EstadoPedido nuevoEstado
    ) {

        if (pedidoActual == null) {
            return;
        }

        pedidoActual.cambiarEstado(
                nuevoEstado
        );

        actualizarPantalla();
    }

    @Override
    public void actualizar(
            Pedido pedido
    ) {

        String mensaje =
                "✓ PEDIDO #"
                + pedido.getId()
                + " → "
                + pedido.getEstado()
                + "\n";

        notificacionesArea.append(
                mensaje
        );

        actualizarPantalla();
    }

    private void actualizarPantalla() {

        if (pedidoActual == null) {
            return;
        }

        numeroPedidoLabel.setText(
                "Pedido #"
                + pedidoActual.getId()
        );

        estadoLabel.setText(
                "Estado: "
                + pedidoActual.getEstado()
        );

        subtotalLabel.setText(
                String.format(
                        "Subtotal: $%.0f",
                        pedidoActual
                                .calcularSubtotal()
                )
        );

        totalLabel.setText(
                String.format(
                        "Total: $%.0f",
                        pedidoActual
                                .calcularTotal()
                )
        );

        productosArea.setText("");

        for (
                ItemPedido item
                : pedidoActual.getProductos()
        ) {

            productosArea.append(
                    item.getProducto()
                            .getDescripcion()
                    + " x"
                    + item.getCantidad()
                    + " - $"
                    + String.format(
                            "%.0f",
                            item.getSubtotal()
                    )
                    + "\n"
            );
        }
    }

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    VentanaPrincipal ventana =
                            new VentanaPrincipal();

                    ventana.setVisible(true);
                }
        );
    }
}