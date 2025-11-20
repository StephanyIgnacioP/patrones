import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Sistema de Procesamiento de Pedidos de Restaurante
 * Implementa: Factory Method, Decorator y Strategy
 */

// ============================================================================
// PATRÓN STRATEGY (Comportamiento)
// Diferentes métodos de pago intercambiables
// ============================================================================

interface MetodoPago {
    boolean procesarPago(double monto);
    String getNombre();
}

class PagoEfectivo implements MetodoPago {
    
    @Override
    public boolean procesarPago(double monto) {
        System.out.println("💵 Procesando pago en EFECTIVO...");
        System.out.println("   Monto a pagar: Bs. " + String.format("%.2f", monto));
        System.out.println("   ✅ Pago en efectivo recibido\n");
        return true;
    }
    
    @Override
    public String getNombre() {
        return "Efectivo";
    }
}

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
// PATRÓN FACTORY METHOD (Creacional)
// Crea diferentes tipos de pedidos según el tipo solicitado
// ============================================================================

enum TipoPedido {
    DESAYUNO,
    ALMUERZO,
    CENA
}

abstract class Pedido {
    protected String nombre;
    protected double precioBase;
    protected String descripcion;
    
    public Pedido(String nombre, double precioBase, String descripcion) {
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.descripcion = descripcion;
    }
    
    public abstract void preparar();
    
    public double getPrecio() {
        return precioBase;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void mostrarResumen() {
        System.out.println("   📋 " + nombre);
        System.out.println("      " + descripcion);
        System.out.println("      Precio: Bs. " + String.format("%.2f", getPrecio()));
    }
}

class PedidoDesayuno extends Pedido {
    
    public PedidoDesayuno() {
        super("Desayuno Completo", 35.00, "Huevos, pan, jugo de naranja, café");
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

class PedidoAlmuerzo extends Pedido {
    
    public PedidoAlmuerzo() {
        super("Almuerzo Ejecutivo", 45.00, "Sopa, plato principal, postre, refresco");
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

class PedidoCena extends Pedido {
    
    public PedidoCena() {
        super("Cena Especial", 55.00, "Entrada, plato fuerte gourmet, vino, postre");
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

class FabricaPedidos {
    
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
// PATRÓN DECORATOR (Estructural)
// Agrega extras a pedidos sin modificar las clases originales
// ============================================================================

abstract class PedidoDecorador extends Pedido {
    protected Pedido pedidoBase;
    
    public PedidoDecorador(Pedido pedidoBase) {
        super(pedidoBase.getNombre(), pedidoBase.getPrecio(), pedidoBase.getDescripcion());
        this.pedidoBase = pedidoBase;
    }
    
    @Override
    public void preparar() {
        pedidoBase.preparar();
    }
}

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
// SISTEMA PRINCIPAL - Integra los 3 patrones
// ============================================================================

class SistemaRestaurante {
    private List<Pedido> pedidos;
    
    public SistemaRestaurante() {
        this.pedidos = new ArrayList<>();
    }
    
    // Método que usa los 3 patrones juntos
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
        
        // 1. FACTORY METHOD: Crear pedido base
        Pedido pedido = FabricaPedidos.crearPedido(tipoPedido);
        System.out.println("✓ Pedido creado: " + pedido.getNombre());
        System.out.println();
        
        // 2. DECORATOR: Agregar extras dinámicamente
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
        
        // Mostrar resumen
        System.out.println("───────────────────────────────────────────────────────");
        System.out.println("RESUMEN DEL PEDIDO:");
        System.out.println("───────────────────────────────────────────────────────");
        pedido.mostrarResumen();
        System.out.println();
        
        // Preparar
        System.out.println("───────────────────────────────────────────────────────");
        System.out.println("PREPARACIÓN:");
        System.out.println("───────────────────────────────────────────────────────");
        pedido.preparar();
        System.out.println("✅ Pedido completamente preparado!\n");
        
        // 3. STRATEGY: Procesar pago con método elegido
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


// ============================================================================
// MAIN - Demostración
// ============================================================================

public class RestaurantOrderSystem {
    
    public static void main(String[] args) {
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║    SISTEMA DE PEDIDOS DE RESTAURANTE                 ║");
        System.out.println("║    Demostración de Patrones de Diseño                ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝\n");
        
        SistemaRestaurante sistema = new SistemaRestaurante();
        
        // Ejemplo: Almuerzo con extras
        System.out.println("📌 PEDIDO: Almuerzo con extras");
        System.out.println("   Factory Method: Crea PedidoAlmuerzo");
        System.out.println("   Decorator: Agrega Queso Extra + Bebida Premium");
        System.out.println("   Strategy: Procesa pago con Tarjeta\n");
        
        sistema.procesarPedido(
            TipoPedido.ALMUERZO,              // Factory Method
            true,                              // Decorator: queso
            false,                             // Sin porción
            true,                              // Decorator: bebida
            new PagoTarjeta("1234567890123456") // Strategy
        );
        
        // Resumen de patrones
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