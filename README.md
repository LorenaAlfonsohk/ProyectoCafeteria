# Café Central

Este es el proyecto de la cafetería para el parcial de Patrones de Diseño.

La idea es tener un programa donde se puedan crear pedidos, agregar productos y cambiar el estado de cada pedido. También se pueden agregar cosas a un café y aplicar descuentos.

## Qué hay hasta ahora

- Productos y cafés.
- Personalización de café con leche deslactosada y shot adicional.
- Promociones con Strategy.
- Pedido y estados del pedido.
- Interfaz para Observer, que se va a completar con las clases de cocina, caja y mesero.
- Un ejemplo sencillo que se ejecuta por consola.

## Organización

- `modelo`: clases principales del programa.
- `patrones/decorator`: clases para agregar cosas a un producto.
- `patrones/strategy`: clases para manejar promociones.
- `patrones/observer`: interfaz que se usa para avisar cuando cambia un pedido.
- `servicios`: operaciones para crear y buscar pedidos.
- `consola`: ejemplo para probar el programa.

## Para continuar con Observer

La interfaz ya está creada en `ObservadorPedido.java`.

Se pueden crear estas clases:

- `CocinaObserver`
- `CajaObserver`
- `MeseroObserver`

Cada una debe implementar `ObservadorPedido` y el método `actualizar(Pedido pedido)`.

Para agregar un observador a un pedido se usa:

```java
pedido.agregarObservador(new CocinaObserver());
```

Cuando se cambia el estado con `pedido.cambiarEstado(EstadoPedido.LISTO)`, se llama a los observadores que estén registrados.

## Para usar los pedidos

```java
PedidoService servicio = new PedidoService();
Pedido pedido = servicio.crearPedido();

pedido.agregarProducto(new Cafe("Americano", 4000), 1);
servicio.cambiarEstado(pedido.getId(), EstadoPedido.EN_PREPARACION);
```

La interfaz gráfica debe usar `PedidoService` y las clases que ya están en el proyecto. No hace falta crear otro sistema de pedidos.

## Ejecutar

Se puede abrir la carpeta como proyecto Maven en NetBeans o IntelliJ, usando Java 17 o una versión superior.

La clase para probar el ejemplo es:

`cafecentral.consola.MainConsola`

Por ahora los pedidos se guardan en memoria, así que se borran cuando se cierra el programa.
