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
import cafecentral.patrones.strategy.DescuentoPorcentaje;
import cafecentral.patrones.strategy.SinPromocion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class VentanaPrincipal extends JFrame implements ObservadorPedido {

    private PedidoService pedidoService;
    private Pedido pedidoActual;

    private JLabel numeroPedidoLabel;
    private JLabel estadoLabel;
    private JLabel subtotalLabel;
    private JLabel totalLabel;
    private JLabel descuentoLabel;

    private JTextArea productosArea;
    private JTextArea notificacionesArea;

    private final Color COLOR_FONDO = new Color(247, 243, 238);
    private final Color COLOR_CAFE_OSCURO = new Color(59, 35, 20);
    private final Color COLOR_CAFE_MEDIO = new Color(111, 78, 55);
    private final Color COLOR_CARAMELO = new Color(210, 145, 75);
    private final Color COLOR_TARJETA = Color.WHITE;
    private final Color COLOR_BORDE = new Color(220, 210, 198);

    public VentanaPrincipal() {
        pedidoService = new PedidoService();

        configurarVentana();
        crearInterfaz();
        crearNuevoPedido();
    }

    private void configurarVentana() {
        setTitle("Café Central - Sistema de Gestión");
        setSize(1050, 740);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void crearInterfaz() {

        JPanel contenedorPrincipal = new JPanel(new BorderLayout(15, 15));
        contenedorPrincipal.setBackground(COLOR_FONDO);
        contenedorPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contenedorPrincipal);

        add(crearEncabezado(), BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(1, 2, 15, 15));
        centro.setBackground(COLOR_FONDO);
        centro.add(crearPanelMenu());
        centro.add(crearPanelPedido());

        add(centro, BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_CAFE_OSCURO);
        panel.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel titulo = new JLabel("☕  CAFÉ CENTRAL", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(new Color(250, 240, 225));

        JLabel subtitulo = new JLabel("Sistema de Pedidos y Barra de Café", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitulo.setForeground(COLOR_CARAMELO);

        panel.add(titulo, BorderLayout.CENTER);
        panel.add(subtitulo, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelMenu() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TARJETA);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDE, 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titulo = new JLabel("MENÚ DE PRODUCTOS", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(COLOR_CAFE_OSCURO);
        titulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(0, 1, 10, 10));
        botones.setBackground(COLOR_TARJETA);

        JButton americanoButton = crearBotonEstilizado("☕  Café Americano  —  $4.000", COLOR_CAFE_MEDIO, Color.WHITE);
        JButton capuchinoButton = crearBotonEstilizado("🥛  Capuchino  —  $6.000", COLOR_CAFE_MEDIO, Color.WHITE);

        JLabel extrasLabel = new JLabel("PERSONALIZAR ÚLTIMO CAFÉ:", SwingConstants.LEFT);
        extrasLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        extrasLabel.setForeground(Color.GRAY);
        extrasLabel.setBorder(new EmptyBorder(8, 2, 0, 0));

        JButton lecheButton = crearBotonEstilizado("+ Agregar leche deslactosada", new Color(242, 232, 218), COLOR_CAFE_OSCURO);
        JButton shotButton = crearBotonEstilizado("+ Agregar shot adicional", new Color(242, 232, 218), COLOR_CAFE_OSCURO);

        JLabel promocionesLabel = new JLabel("PROMOCIONES:", SwingConstants.LEFT);
        promocionesLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        promocionesLabel.setForeground(Color.GRAY);

        JButton descuento10Button = crearBotonEstilizado("Descuento del 10%", new Color(242, 232, 218), COLOR_CAFE_OSCURO);
        JButton descuento20Button = crearBotonEstilizado("Descuento del 20%", new Color(242, 232, 218), COLOR_CAFE_OSCURO);
        JButton sinDescuentoButton = crearBotonEstilizado("Quitar descuento", new Color(242, 232, 218), COLOR_CAFE_OSCURO);

        descuento10Button.addActionListener(e -> aplicarDescuento(10));
        descuento20Button.addActionListener(e -> aplicarDescuento(20));
        sinDescuentoButton.addActionListener(e -> quitarDescuento());

        JButton nuevoPedidoButton = crearBotonEstilizado("✨  Nuevo Pedido", COLOR_CARAMELO, Color.WHITE);

        americanoButton.addActionListener(e -> agregarAmericano());
        capuchinoButton.addActionListener(e -> agregarCapuchino());
        lecheButton.addActionListener(e -> agregarLeche());
        shotButton.addActionListener(e -> agregarShot());
        nuevoPedidoButton.addActionListener(e -> crearNuevoPedido());

        botones.add(americanoButton);
        botones.add(capuchinoButton);
        botones.add(extrasLabel);
        botones.add(lecheButton);
        botones.add(shotButton);
        botones.add(promocionesLabel);
        botones.add(descuento10Button);
        botones.add(descuento20Button);
        botones.add(sinDescuentoButton);
        botones.add(new JLabel(""));
        botones.add(nuevoPedidoButton);

        panel.add(botones, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelPedido() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TARJETA);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDE, 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titulo = new JLabel("RESUMEN DEL PEDIDO", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(COLOR_CAFE_OSCURO);
        titulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.add(titulo, BorderLayout.NORTH);

        productosArea = new JTextArea();
        productosArea.setEditable(false);
        productosArea.setFont(new Font("SansSerif", Font.PLAIN, 15));
        productosArea.setBackground(new Color(252, 250, 247));
        productosArea.setForeground(new Color(45, 45, 45));
        productosArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(productosArea);
        scroll.setBorder(new LineBorder(COLOR_BORDE, 1));
        panel.add(scroll, BorderLayout.CENTER);

        JPanel informacion = new JPanel(new GridLayout(5, 1, 4, 4));
        informacion.setBackground(new Color(247, 243, 238));
        informacion.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDE, 1),
                new EmptyBorder(10, 12, 10, 12)
        ));

        numeroPedidoLabel = new JLabel("Pedido #");
        numeroPedidoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        numeroPedidoLabel.setForeground(COLOR_CAFE_OSCURO);

        estadoLabel = new JLabel("Estado:");
        estadoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        estadoLabel.setForeground(new Color(180, 95, 6));

        subtotalLabel = new JLabel("Subtotal: $0");
        subtotalLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtotalLabel.setForeground(Color.DARK_GRAY);

        descuentoLabel = new JLabel("Descuento: $0");
        descuentoLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descuentoLabel.setForeground(new Color(56, 142, 60));

        totalLabel = new JLabel("Total: $0");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        totalLabel.setForeground(COLOR_CAFE_OSCURO);

        informacion.add(numeroPedidoLabel);
        informacion.add(estadoLabel);
        informacion.add(subtotalLabel);
        informacion.add(descuentoLabel);
        informacion.add(totalLabel);

        panel.add(informacion, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_FONDO);

        JPanel botonesEstado = new JPanel(new GridLayout(1, 4, 10, 10));
        botonesEstado.setBackground(COLOR_FONDO);

        JButton pendienteButton = crearBotonEstilizado("PENDIENTE", new Color(230, 175, 46), Color.WHITE);
        JButton prepararButton = crearBotonEstilizado("EN PREPARACIÓN", new Color(217, 119, 36), Color.WHITE);
        JButton listoButton = crearBotonEstilizado("LISTO", new Color(56, 142, 60), Color.WHITE);
        JButton entregarButton = crearBotonEstilizado("ENTREGADO", new Color(90, 98, 106), Color.WHITE);

        pendienteButton.addActionListener(e -> cambiarEstado(EstadoPedido.PENDIENTE));
        prepararButton.addActionListener(e -> cambiarEstado(EstadoPedido.EN_PREPARACION));
        listoButton.addActionListener(e -> cambiarEstado(EstadoPedido.LISTO));
        entregarButton.addActionListener(e -> cambiarEstado(EstadoPedido.ENTREGADO));

        botonesEstado.add(pendienteButton);
        botonesEstado.add(prepararButton);
        botonesEstado.add(listoButton);
        botonesEstado.add(entregarButton);

        panel.add(botonesEstado, BorderLayout.NORTH);

        JPanel notificacionesPanel = new JPanel(new BorderLayout(5, 5));
        notificacionesPanel.setBackground(COLOR_TARJETA);
        notificacionesPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDE, 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));

        JLabel titulo = new JLabel("PANEL DE NOTIFICACIONES (OBSERVER)", SwingConstants.LEFT);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 13));
        titulo.setForeground(COLOR_CAFE_OSCURO);
        notificacionesPanel.add(titulo, BorderLayout.NORTH);

        notificacionesArea = new JTextArea(5, 20);
        notificacionesArea.setEditable(false);
        notificacionesArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        notificacionesArea.setBackground(new Color(38, 34, 32));
        notificacionesArea.setForeground(new Color(144, 238, 144));
        notificacionesArea.setBorder(new EmptyBorder(8, 10, 8, 10));

        JScrollPane scroll = new JScrollPane(notificacionesArea);
        scroll.setBorder(null);
        notificacionesPanel.add(scroll, BorderLayout.CENTER);

        panel.add(notificacionesPanel, BorderLayout.CENTER);

        return panel;
    }

    private JButton crearBotonEstilizado(String texto, Color fondo, Color textoColor) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setBackground(fondo);
        boton.setForeground(textoColor);
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(100, 42));
        return boton;
    }

    private void crearNuevoPedido() {
        pedidoActual = pedidoService.crearPedido();

        CocinaObserver cocina = new CocinaObserver();
        CajaObserver caja = new CajaObserver();
        MeseroObserver mesero = new MeseroObserver();

        pedidoActual.agregarObservador(cocina);
        pedidoActual.agregarObservador(caja);
        pedidoActual.agregarObservador(mesero);
        pedidoActual.agregarObservador(this);

        notificacionesArea.setText("");
        notificacionesArea.append("✓ Pedido #" + pedidoActual.getId() + " creado correctamente.\n");

        actualizarPantalla();
    }

    private void agregarAmericano() {
        Producto cafe = new Cafe("Café Americano", 4000);
        pedidoActual.agregarProducto(cafe, 1);
        actualizarPantalla();
    }

    private void agregarCapuchino() {
        Producto cafe = new Cafe("Capuchino", 6000);
        pedidoActual.agregarProducto(cafe, 1);
        actualizarPantalla();
    }

    private void agregarLeche() {
        if (pedidoActual.getProductos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero agrega un producto al pedido.");
            return;
        }

        int ultimo = pedidoActual.getProductos().size() - 1;
        ItemPedido itemActual = pedidoActual.getProductos().get(ultimo);
        Producto productoActual = itemActual.getProducto();

        Producto productoPersonalizado = new LecheDeslactosada(productoActual);
        ItemPedido nuevoItem = new ItemPedido(productoPersonalizado, itemActual.getCantidad());

        pedidoActual.getProductos().set(ultimo, nuevoItem);
        actualizarPantalla();
    }

    private void agregarShot() {
        if (pedidoActual.getProductos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero agrega un producto al pedido.");
            return;
        }

        int ultimo = pedidoActual.getProductos().size() - 1;
        ItemPedido itemActual = pedidoActual.getProductos().get(ultimo);
        Producto productoActual = itemActual.getProducto();

        Producto productoPersonalizado = new ShotAdicional(productoActual);
        ItemPedido nuevoItem = new ItemPedido(productoPersonalizado, itemActual.getCantidad());

        pedidoActual.getProductos().set(ultimo, nuevoItem);
        actualizarPantalla();
    }

    private void aplicarDescuento(int porcentaje) {
        pedidoActual.setPromocion(new DescuentoPorcentaje(porcentaje + "%", porcentaje));
        actualizarPantalla();
    }

    private void quitarDescuento() {
        pedidoActual.setPromocion(new SinPromocion());
        actualizarPantalla();
    }

    private void cambiarEstado(EstadoPedido nuevoEstado) {
        if (pedidoActual == null) {
            return;
        }
        pedidoActual.cambiarEstado(nuevoEstado);
        actualizarPantalla();
    }

    @Override
    public void actualizar(Pedido pedido) {
        String mensaje = "✓ PEDIDO #" + pedido.getId() + " → " + pedido.getEstado() + "\n";
        notificacionesArea.append(mensaje);
        actualizarPantalla();
    }

    private void actualizarPantalla() {
        if (pedidoActual == null) {
            return;
        }

        numeroPedidoLabel.setText("Pedido #" + pedidoActual.getId());
        estadoLabel.setText("Estado: " + pedidoActual.getEstado());
        subtotalLabel.setText(String.format("Subtotal: $%.0f", pedidoActual.calcularSubtotal()));
        double descuento = pedidoActual.calcularSubtotal() - pedidoActual.calcularTotal();
        descuentoLabel.setText(String.format("Descuento: $%.0f", descuento));
        totalLabel.setText(String.format("Total: $%.0f", pedidoActual.calcularTotal()));

        productosArea.setText("");
        for (ItemPedido item : pedidoActual.getProductos()) {
            productosArea.append(
                    "• " + item.getProducto().getDescripcion()
                            + " (x" + item.getCantidad() + ")  —  $"
                            + String.format("%.0f", item.getSubtotal())
                            + "\n"
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}