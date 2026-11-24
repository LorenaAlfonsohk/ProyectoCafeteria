# Cafeteria

Proyecto académico de una aplicación sencilla para gestionar pedidos de una cafetería.

## Tecnologías
- Java 17
- Maven
- JUnit 5 para las pruebas

## Patrones incluidos
- **Decorator:** permite agregar ingredientes o complementos a un producto sin crear una clase para cada combinación.
- **Strategy:** permite escoger una promoción para calcular el total de un pedido.
- **Observer:** `Pedido` incluye la estructura para registrar observadores y avisar cuando cambia su estado. La implementación de observadores concretos y la interfaz gráfica se integran por separado.

## Ejecutar
Abre el proyecto como proyecto Maven en IntelliJ IDEA, NetBeans o Eclipse. Ejecuta la clase:

`src/main/java/cafecentral/consola/MainConsola.java`

También puedes compilar y ejecutar las pruebas desde una terminal ubicada en la carpeta del proyecto:

```bash
mvn test
```

La primera ejecución necesita conexión a internet para descargar las dependencias de JUnit.

## Menú
La aplicación permite ver productos, crear pedidos, consultar pedidos, cambiar su estado y consultar el total de pedidos entregados.

## Reglas de ejemplo
Los precios y porcentajes son valores de ejemplo para el proyecto:
- Café: $4.000
- Sándwich: $8.000
- Postre: $5.000
- Leche deslactosada: +$1.000
- Shot adicional: +$2.000
- Jarabe de vainilla: +$1.500
- Crema batida: +$2.000
- Promoción lunes: 15% de descuento
- Hora feliz: 10% de descuento

## Nota de integración
La interfaz `ObservadorPedido` y los métodos de registro/notificación están preparados para que se agreguen los observadores concretos y la interfaz gráfica.
