import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
// ============================================================================
// PATRÓN DE COMPORTAMIENTO: STRATEGY
// ============================================================================
/**
 * PATRÓN STRATEGY
 * 
 * Propósito: Define una familia de algoritmos (métodos de pago), encapsula 
 * cada uno y los hace intercambiables.
 * 
 * Uso en este caso: Diferentes estrategias para procesar pagos (Efectivo, 
 * Tarjeta, Transferencia) sin que el pedido necesite conocer los detalles
 * de implementación de cada método.
 */

/**
 * Interfaz Strategy - Define el contrato para todos los métodos de pago
 */
interface MetodoPago {
    /**
     * Procesa el pago por el monto especificado
     * @param monto Cantidad a pagar
     * @return true si el pago fue exitoso
     */
    boolean procesarPago(double monto);
    
    /**
     * Obtiene el nombre del método de pago
     */
    String getNombre();
}
/**
 * Estrategia Concreta 1: Pago en Efectivo
 */
class PagoEfectivo implements MetodoPago {
    
    @Override
    public boolean procesarPago(double monto) {
        System.out.println("💵 Procesando pago en EFECTIVO...");
        System.out.println("   Monto a pagar: Bs. " + String.format("%.2f", monto));
        System.out.println("   ✅ Pago en efectivo recibido");
        System.out.println("   Gracias por su preferencia!\n");
        return true;
    }
    
    @Override
    public String getNombre() {
        return "Efectivo";
    }
}
/**
 * Estrategia Concreta 2: Pago con Tarjeta
 */
class PagoTarjeta implements MetodoPago {
    private String numeroTarjeta;
    
    public PagoTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
    
    @Override
    public boolean procesarPago(double monto) {
        System.out.println("💳 Procesando pago con TARJETA...");
        System.out.println("   Tarjeta: **** **** **** " + numeroTarjeta.substring(12));
        System.out.println("   Monto: Bs. " + String.format("%.2f", monto));
        System.out.println("   Conectando con banco...");
        System.out.println("   ✅ Transacción aprobada");
        System.out.println("   Código de autorización: " + 
                          (int)(Math.random() * 900000 + 100000) + "\n");
        return true;
    }
    
    @Override
    public String getNombre() {
        return "Tarjeta";
    }
}

/**
 * Estrategia Concreta 3: Pago por Transferencia
 */
class PagoTransferencia implements MetodoPago {
    private String numeroCuenta;
    
    public PagoTransferencia(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
    
    @Override
    public boolean procesarPago(double monto) {
        System.out.println("🏦 Procesando TRANSFERENCIA bancaria...");
        System.out.println("   Cuenta destino: " + numeroCuenta);
        System.out.println("   Monto: Bs. " + String.format("%.2f", monto));
        System.out.println("   Verificando transferencia...");
        System.out.println("   ✅ Transferencia confirmada");
        System.out.println("   Referencia: TRF-" + System.currentTimeMillis() + "\n");
        return true;
    }
    
    @Override
    public String getNombre() {
        return "Transferencia";
    }
}
// ============================================================================
// PATRÓN CREACIONAL: FACTORY METHOD
// ============================================================================
/**
 * PATRÓN FACTORY METHOD
 * 
 * Propósito: Define una interfaz para crear objetos (pedidos), pero permite
 * que las subclases decidan qué clase instanciar.
 * 
 * Uso en este caso: Crear diferentes tipos de pedidos (Desayuno, Almuerzo, 
 * Cena) sin que el cliente necesite conocer los detalles de construcción
 * de cada tipo.
 */

/**
 * Enumeración de tipos de pedido
 */
enum TipoPedido {
    DESAYUNO,
    ALMUERZO,
    CENA
}

/**
 * Producto Abstracto - Interfaz común para todos los pedidos
 */
abstract class Pedido {
    protected String nombre;
    protected double precioBase;
    protected String descripcion;
    
    /**
     * Constructor del pedido base
     */
    public Pedido(String nombre, double precioBase, String descripcion) {
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.descripcion = descripcion;
    }
    
    /**
     * Método abstracto para preparar el pedido
     * Cada tipo de pedido implementa su propia preparación
     */
    public abstract void preparar();
    
    /**
     * Obtiene el precio del pedido (puede ser modificado por decoradores)
     */
    public double getPrecio() {
        return precioBase;
    }
    
    /**
     * Obtiene la descripción del pedido
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Obtiene el nombre del pedido
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Muestra el resumen del pedido
     */
    public void mostrarResumen() {
        System.out.println("   📋 " + nombre);
        System.out.println("      " + descripcion);
        System.out.println("      Precio: Bs. " + String.format("%.2f", getPrecio()));
    }
}
/**
 * Producto Concreto 1: Pedido de Desayuno
 */
class PedidoDesayuno extends Pedido {
    
    public PedidoDesayuno() {
        super("Desayuno Completo", 
              35.00, 
              "Huevos, pan, jugo de naranja, café");
    }
    
    @Override
    public void preparar() {
        System.out.println("🍳 Preparando DESAYUNO:");
        System.out.println("   - Cocinando huevos revueltos...");
        System.out.println("   - Tostando pan...");
        System.out.println("   - Exprimiendo jugo de naranja fresco...");
        System.out.println("   - Preparando café...");
        System.out.println("   ✅ Desayuno listo!\n");
    }
}
/**
 * Producto Concreto 2: Pedido de Almuerzo
 */
class PedidoAlmuerzo extends Pedido {
    
    public PedidoAlmuerzo() {
        super("Almuerzo Ejecutivo", 
              45.00, 
              "Sopa, plato principal, postre, refresco");
    }
    
    @Override
    public void preparar() {
        System.out.println("🍽️  Preparando ALMUERZO:");
        System.out.println("   - Calentando sopa del día...");
        System.out.println("   - Cocinando plato principal (pollo con arroz)...");
        System.out.println("   - Preparando ensalada fresca...");
        System.out.println("   - Sirviendo postre...");
        System.out.println("   ✅ Almuerzo listo!\n");
    }
}
/**
 * Producto Concreto 3: Pedido de Cena
 */
class PedidoCena extends Pedido {
    
    public PedidoCena() {
        super("Cena Especial", 
              55.00, 
              "Entrada, plato fuerte gourmet, vino, postre");
    }
    
    @Override
    public void preparar() {
        System.out.println("🌙 Preparando CENA:");
        System.out.println("   - Preparando entrada (ensalada caprese)...");
        System.out.println("   - Cocinando plato gourmet (filete con papas)...");
        System.out.println("   - Sirviendo vino de la casa...");
        System.out.println("   - Preparando postre especial...");
        System.out.println("   ✅ Cena lista!\n");
    }
}

/**
 * Factory - Fábrica de Pedidos
 * 
 * Esta clase implementa el patrón Factory Method.
 * Encapsula la lógica de creación de diferentes tipos de pedidos.
 */
class FabricaPedidos {
    
    /**
     * Factory Method: Crea el pedido apropiado según el tipo solicitado
     * 
     * @param tipo Tipo de pedido a crear
     * @return Instancia del pedido correspondiente
     * @throws IllegalArgumentException si el tipo no es válido
     */
    public static Pedido crearPedido(TipoPedido tipo) {
        switch (tipo) {
            case DESAYUNO:
                return new PedidoDesayuno();
            case ALMUERZO:
                return new PedidoAlmuerzo();
            case CENA:
                return new PedidoCena();
            default:
                throw new IllegalArgumentException("Tipo de pedido no válido: " + tipo);
        }
    }
}


// ============================================================================
// PATRÓN ESTRUCTURAL: DECORATOR
// ============================================================================
/**
 * PATRÓN DECORATOR
 * 
 * Propósito: Permite agregar funcionalidades adicionales a un objeto 
 * dinámicamente sin modificar su estructura original.
 * 
 * Uso en este caso: Agregar extras/modificadores a los pedidos (queso extra,
 * porción adicional, bebida premium) sin modificar las clases de pedido base.
 */

/**
 * Decorador Base - Implementa la misma interfaz que Pedido
 */
abstract class PedidoDecorador extends Pedido {
    protected Pedido pedidoBase;
    
    /**
     * Constructor que recibe el pedido a decorar
     */
    public PedidoDecorador(Pedido pedidoBase) {
        super(pedidoBase.getNombre(), pedidoBase.getPrecio(), pedidoBase.getDescripcion());
        this.pedidoBase = pedidoBase;
    }
    
    @Override
    public void preparar() {
        pedidoBase.preparar();
    }
}
/**
 * Decorador Concreto 1: Agrega Queso Extra
 */
class ConQuesoExtra extends PedidoDecorador {
    
    public ConQuesoExtra(Pedido pedidoBase) {
        super(pedidoBase);
    }
    
    @Override
    public double getPrecio() {
        return pedidoBase.getPrecio() + 8.00;
    }
    
    @Override
    public String getDescripcion() {
        return pedidoBase.getDescripcion() + " + Queso Extra";
    }
    
    @Override
    public void preparar() {
        pedidoBase.preparar();
        System.out.println("🧀 Agregando QUESO EXTRA de primera calidad...");
    }
}
/**
 * Decorador Concreto 2: Agrega Porción Extra
 */
class ConPorcionExtra extends PedidoDecorador {
    
    public ConPorcionExtra(Pedido pedidoBase) {
        super(pedidoBase);
    }
    
    @Override
    public double getPrecio() {
        return pedidoBase.getPrecio() + 15.00;
    }
    
    @Override
    public String getDescripcion() {
        return pedidoBase.getDescripcion() + " + Porción Extra";
    }
    
    @Override
    public void preparar() {
        pedidoBase.preparar();
        System.out.println("🍴 Agregando PORCIÓN EXTRA (tamaño doble)...");
    }
}

/**
 * Decorador Concreto 3: Agrega Bebida Premium
 */
class ConBebidaPremium extends PedidoDecorador {
    
    public ConBebidaPremium(Pedido pedidoBase) {
        super(pedidoBase);
    }
    
    @Override
    public double getPrecio() {
        return pedidoBase.getPrecio() + 12.00;
    }
    
    @Override
    public String getDescripcion() {
        return pedidoBase.getDescripcion() + " + Bebida Premium";
    }
    
    @Override
    public void preparar() {
        pedidoBase.preparar();
        System.out.println("🥤 Agregando BEBIDA PREMIUM (jugo natural o smoothie)...");
    }
}
// ============================================================================
// SISTEMA DE RESTAURANTE - INTEGRACIÓN DE PATRONES
// ============================================================================
/**
 * Clase principal que gestiona los pedidos del restaurante
 * Integra los tres patrones de diseño
 */
class SistemaRestaurante {
    private List<Pedido> pedidos;
    
    public SistemaRestaurante() {
        this.pedidos = new ArrayList<>();
    }
    
    /**
     * Método principal que demuestra el uso integrado de los tres patrones
     * 
     * @param tipoPedido Tipo de pedido base a crear (Factory Method)
     * @param agregarQueso Si se agrega queso extra (Decorator)
     * @param agregarPorcion Si se agrega porción extra (Decorator)
     * @param agregarBebida Si se agrega bebida premium (Decorator)
     * @param metodoPago Método de pago a utilizar (Strategy)
     */
    public void procesarPedido(TipoPedido tipoPedido, 
                              boolean agregarQueso,
                              boolean agregarPorcion,
                              boolean agregarBebida,
                              MetodoPago metodoPago) {
        
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("         PROCESANDO NUEVO PEDIDO");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Fecha: " + LocalDateTime.now()
                          .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println();
        
        // ═══════════════════════════════════════════════════════════
        // PASO 1: PATRÓN CREACIONAL - FACTORY METHOD
        // ═══════════════════════════════════════════════════════════
        // Crear el pedido base usando el Factory
        Pedido pedido = FabricaPedidos.crearPedido(tipoPedido);
        System.out.println("✓ Pedido creado: " + pedido.getNombre());
        System.out.println();
        
        // ═══════════════════════════════════════════════════════════
        // PASO 2: PATRÓN ESTRUCTURAL - DECORATOR
        // ═══════════════════════════════════════════════════════════
        // Agregar extras/modificadores dinámicamente
        if (agregarQueso) {
            pedido = new ConQuesoExtra(pedido);
            System.out.println("✓ Agregado: Queso Extra (+Bs. 8.00)");
        }
        
        if (agregarPorcion) {
            pedido = new ConPorcionExtra(pedido);
            System.out.println("✓ Agregado: Porción Extra (+Bs. 15.00)");
        }
        
        if (agregarBebida) {
            pedido = new ConBebidaPremium(pedido);
            System.out.println("✓ Agregado: Bebida Premium (+Bs. 12.00)");
        }
        
        System.out.println();
        
        // Mostrar resumen del pedido
        System.out.println("───────────────────────────────────────────────────────");
        System.out.println("RESUMEN DEL PEDIDO:");
        System.out.println("───────────────────────────────────────────────────────");
        pedido.mostrarResumen();
        System.out.println();
        
        // Preparar el pedido
        System.out.println("───────────────────────────────────────────────────────");
        System.out.println("PREPARACIÓN:");
        System.out.println("───────────────────────────────────────────────────────");
        pedido.preparar();
        System.out.println("✅ Pedido completamente preparado!\n");
        
        // ═══════════════════════════════════════════════════════════
        // PASO 3: PATRÓN DE COMPORTAMIENTO - STRATEGY
        // ═══════════════════════════════════════════════════════════
        // Procesar el pago con la estrategia seleccionada
        System.out.println("───────────────────────────────────────────────────────");
        System.out.println("PROCESAMIENTO DE PAGO:");
        System.out.println("───────────────────────────────────────────────────────");
        System.out.println("Método seleccionado: " + metodoPago.getNombre());
        System.out.println("Monto total: Bs. " + String.format("%.2f", pedido.getPrecio()));
        System.out.println();
        
        boolean pagoExitoso = metodoPago.procesarPago(pedido.getPrecio());
        
        if (pagoExitoso) {
            pedidos.add(pedido);
            System.out.println("✅ PEDIDO COMPLETADO EXITOSAMENTE!");
        } else {
            System.out.println("❌ Error en el procesamiento del pago");
        }
        
        System.out.println("═══════════════════════════════════════════════════════\n\n");
    }
    
    /**
     * Muestra estadísticas de los pedidos procesados
     */
    public void mostrarEstadisticas() {
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║         ESTADÍSTICAS DEL RESTAURANTE                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
        System.out.println("Total de pedidos procesados: " + pedidos.size());
        
        double total = 0;
        for (Pedido p : pedidos) {
            total += p.getPrecio();
        }
        
        System.out.println("Ingreso total: Bs. " + String.format("%.2f", total));
        System.out.println("Promedio por pedido: Bs. " + 
                          String.format("%.2f", pedidos.isEmpty() ? 0 : total / pedidos.size()));
        System.out.println();
    }
}

/**
 * Clase principal con el método main para demostrar el sistema
 */
public class RestaurantOrderSystem {
    
    public static void main(String[] args) {
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║    SISTEMA DE PEDIDOS DE RESTAURANTE                 ║");
        System.out.println("║    Demostración de Patrones de Diseño                ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝\n");
        
        SistemaRestaurante sistema = new SistemaRestaurante();
        
        // ═══════════════════════════════════════════════════════════
        // EJEMPLO: Almuerzo con extras y pago con tarjeta
        // Demuestra los 3 patrones trabajando juntos
        // ═══════════════════════════════════════════════════════════
        System.out.println("📌 PEDIDO: Almuerzo con extras");
        System.out.println("   Factory Method: Crea PedidoAlmuerzo");
        System.out.println("   Decorator: Agrega Queso Extra + Bebida Premium");
        System.out.println("   Strategy: Procesa pago con Tarjeta\n");
        
        sistema.procesarPedido(
            TipoPedido.ALMUERZO,              // Factory Method: crea el pedido base
            true,                              // Decorator: agrega queso extra
            false,                             // Sin porción extra
            true,                              // Decorator: agrega bebida premium
            new PagoTarjeta("1234567890123456") // Strategy: método de pago
        );
        
        // Resumen de patrones utilizados
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║    RESUMEN DE PATRONES IMPLEMENTADOS                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("✓ PATRÓN CREACIONAL - Factory Method:");
        System.out.println("  → FabricaPedidos crea diferentes tipos de pedidos");
        System.out.println("  → El cliente no necesita conocer las clases concretas");
        System.out.println();
        System.out.println("✓ PATRÓN ESTRUCTURAL - Decorator:");
        System.out.println("  → Agrega funcionalidades (extras) dinámicamente");
        System.out.println("  → Permite combinar extras sin modificar código base");
        System.out.println();
        System.out.println("✓ PATRÓN DE COMPORTAMIENTO - Strategy:");
        System.out.println("  → Diferentes métodos de pago intercambiables");
        System.out.println("  → Cada método de pago en su propia clase");
        System.out.println();
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("              ¡DEMOSTRACIÓN COMPLETADA!");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }
}