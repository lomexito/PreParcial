package preparcial;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Automovil {

    public enum TipoCombustible {
        GASOLINA, BIOETANOL, DIESEL, BIODIESEL, GAS_NATURAL
    }

    public enum TipoAutomovil {
        CARRO_DE_CIUDAD, SUBCOMPACTO, COMPACTO, FAMILIAR, EJECUTIVO, SUV
    }

    public enum Color {
        BLANCO, NEGRO, ROJO, NARANJA, AMARILLO, VERDE, AZUL, VIOLETA
    }

    private String marca;
    private int modelo;
    private double motor;
    private TipoCombustible tipoCombustible;
    private TipoAutomovil tipoAutomovil;
    private int numeroPuertas;
    private int cantidadAsientos;
    private double velocidadMaxima;
    private Color color;
    private double velocidadActual;

    private final Vector<RegistroViaje> bitacora = new Vector<>();

    private static int totalAutomoviles = 0;
    private static int contadorRegistros = 0;
    public static final double LIMITE_LEGAL = 120.0;
    private static final double INCREMENTO_DEFECTO = 10.0;

    public Automovil(String marca, int modelo, double motor, TipoCombustible tipoCombustible,
                      TipoAutomovil tipoAutomovil, int numeroPuertas, int cantidadAsientos,
                      double velocidadMaxima, Color color) {
        setMarca(marca);
        setModelo(modelo);
        setMotor(motor);
        setTipoCombustible(tipoCombustible);
        setTipoAutomovil(tipoAutomovil);
        setNumeroPuertas(numeroPuertas);
        setCantidadAsientos(cantidadAsientos);
        setVelocidadMaxima(velocidadMaxima);
        setColor(color);
        this.velocidadActual = 0;
        totalAutomoviles++;
    }

    public Automovil(String marca, int modelo, double velocidadMaxima) {
        this(marca, modelo, 1.6, TipoCombustible.GASOLINA, TipoAutomovil.CARRO_DE_CIUDAD,
                4, 5, velocidadMaxima, Color.BLANCO);
    }

    public Automovil(Automovil otro) {
        this(otro.marca, otro.modelo, otro.motor, otro.tipoCombustible, otro.tipoAutomovil,
                otro.numeroPuertas, otro.cantidadAsientos, otro.velocidadMaxima, otro.color);
        this.velocidadActual = otro.velocidadActual;
    }

    public class RegistroViaje {
        private final String marca;
        private final int modelo;
        private final double velocidad;
        private final String evento;

        public RegistroViaje(String evento) {

            this.marca = Automovil.this.marca;
            this.modelo = Automovil.this.modelo;
            this.velocidad = Automovil.this.velocidadActual;
            this.evento = evento;
            contadorRegistros++;
        }

        public RegistroViaje() {
            this("cambio de velocidad");
        }

        public double getVelocidad() {
            return velocidad;
        }

        public String describir() {
            return String.format("[%s] %s %d - velocidad: %.1f km/h (%s)",
                    evento, marca, modelo, velocidad,
                    velocidad > LIMITE_LEGAL ? "EXCESO DE VELOCIDAD" : "dentro del limite");
        }
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) {
        if (marca == null || marca.isBlank()) {
            System.out.println("Error: la marca no puede estar vacia.");
            return;
        }
        this.marca = marca;
    }

    public int getModelo() { return modelo; }
    public void setModelo(int modelo) {
        if (modelo < 1900 || modelo > 2100) {
            System.out.println("Error: año de modelo invalido: " + modelo);
            return;
        }
        this.modelo = modelo;
    }

    public double getMotor() { return motor; }
    public void setMotor(double motor) {
        if (motor <= 0) {
            System.out.println("Error: el cilindraje del motor debe ser positivo.");
            return;
        }
        this.motor = motor;
    }

    public TipoCombustible getTipoCombustible() { return tipoCombustible; }
    public void setTipoCombustible(TipoCombustible tipoCombustible) {
        if (tipoCombustible == null) {
            System.out.println("Error: tipo de combustible no puede ser nulo.");
            return;
        }
        this.tipoCombustible = tipoCombustible;
    }

    public TipoAutomovil getTipoAutomovil() { return tipoAutomovil; }
    public void setTipoAutomovil(TipoAutomovil tipoAutomovil) {
        if (tipoAutomovil == null) {
            System.out.println("Error: tipo de automovil no puede ser nulo.");
            return;
        }
        this.tipoAutomovil = tipoAutomovil;
    }

    public int getNumeroPuertas() { return numeroPuertas; }
    public void setNumeroPuertas(int numeroPuertas) {
        if (numeroPuertas < 2 || numeroPuertas > 6) {
            System.out.println("Error: numero de puertas invalido: " + numeroPuertas);
            return;
        }
        this.numeroPuertas = numeroPuertas;
    }

    public int getCantidadAsientos() { return cantidadAsientos; }
    public void setCantidadAsientos(int cantidadAsientos) {
        if (cantidadAsientos < 1) {
            System.out.println("Error: la cantidad de asientos debe ser positiva.");
            return;
        }
        this.cantidadAsientos = cantidadAsientos;
    }

    public double getVelocidadMaxima() { return velocidadMaxima; }
    public void setVelocidadMaxima(double velocidadMaxima) {
        if (velocidadMaxima <= 0) {
            System.out.println("Error: la velocidad maxima debe ser positiva.");
            return;
        }
        this.velocidadMaxima = velocidadMaxima;
    }

    public Color getColor() { return color; }
    public void setColor(Color color) {
        if (color == null) {
            System.out.println("Error: el color no puede ser nulo.");
            return;
        }
        this.color = color;
    }

    public double getVelocidadActual() { return velocidadActual; }

    public boolean setVelocidadActual(double velocidadActual) {
        if (velocidadActual < 0 || velocidadActual > velocidadMaxima) {
            System.out.printf("Error: velocidad %.1f km/h invalida para %s %d (max %.1f).%n",
                    velocidadActual, marca, modelo, velocidadMaxima);
            bitacora.add(new RegistroViaje("intento invalido de set velocidad"));
            return false;
        }
        this.velocidadActual = velocidadActual;
        bitacora.add(new RegistroViaje("set velocidad"));
        return true;
    }

    public Vector<RegistroViaje> getBitacora() { return bitacora; }

    public void acelerar() {
        acelerar(INCREMENTO_DEFECTO);
    }

    public void acelerar(double incremento) {
        if (incremento < 0) {
            System.out.println("Error: el incremento de aceleracion no puede ser negativo.");
            return;
        }
        double nueva = velocidadActual + incremento;
        if (nueva > velocidadMaxima) {
            System.out.printf("No se puede acelerar: %.1f km/h superaria la velocidad maxima (%.1f).%n",
                    nueva, velocidadMaxima);
            bitacora.add(new RegistroViaje("intento invalido de acelerar"));
            return;
        }
        velocidadActual = nueva;
        bitacora.add(new RegistroViaje("acelerar"));
    }

    public void acelerar(double incremento, int veces) {
        for (int i = 0; i < veces; i++) {
            double antes = velocidadActual;
            acelerar(incremento);
            if (velocidadActual == antes) {
                break;
            }
        }
    }

    public void desacelerar() {
        desacelerar(INCREMENTO_DEFECTO);
    }

    public void desacelerar(double decremento) {
        if (decremento < 0) {
            System.out.println("Error: el decremento no puede ser negativo.");
            return;
        }
        double nueva = velocidadActual - decremento;
        if (nueva < 0) {
            System.out.printf("No se puede desacelerar: %.1f km/h seria negativo.%n", nueva);
            bitacora.add(new RegistroViaje("intento invalido de desacelerar"));
            return;
        }
        velocidadActual = nueva;
        bitacora.add(new RegistroViaje("desacelerar"));
    }

    public void frenar() {
        velocidadActual = 0;
        bitacora.add(new RegistroViaje("frenar"));
    }

    public double tiempoEstimadoLlegada(double distanciaKm) {
        if (velocidadActual <= 0) {
            System.out.println("No se puede estimar el tiempo: velocidad actual es 0.");
            return Double.POSITIVE_INFINITY;
        }
        return distanciaKm / velocidadActual;
    }

    public double tiempoEstimadoLlegada(double distanciaKm, double velocidadCrucero) {
        if (velocidadCrucero <= 0) {
            System.out.println("No se puede estimar el tiempo: velocidad de crucero invalida.");
            return Double.POSITIVE_INFINITY;
        }
        return distanciaKm / velocidadCrucero;
    }

    public double tiempoEstimadoLlegada(double distanciaKm, int paradas, double minutosPorParada) {
        double base = tiempoEstimadoLlegada(distanciaKm);
        double horasParadas = (paradas * minutosPorParada) / 60.0;
        return base + horasParadas;
    }

    public void mostrar() {
        System.out.printf("%s %d | motor %.1fL | %s | %s | %d puertas | %d asientos | " +
                        "color %s | vel. max %.1f km/h | vel. actual %.1f km/h%n",
                marca, modelo, motor, tipoCombustible, tipoAutomovil,
                numeroPuertas, cantidadAsientos, color, velocidadMaxima, velocidadActual);
    }

    public void mostrar(boolean detallado) {
        mostrar();
        if (detallado) {
            System.out.println("  Bitacora (" + bitacora.size() + " registros):");
            for (RegistroViaje r : bitacora) {
                System.out.println("   - " + r.describir());
            }
        }
    }

    public static Automovil[] filtrar(Automovil[] flota, TipoCombustible criterio) {
        List<Automovil> resultado = new ArrayList<>();
        if (flota != null) {
            for (Automovil a : flota) {
                if (a != null && a.getTipoCombustible() == criterio) resultado.add(a);
            }
        }
        return resultado.toArray(new Automovil[0]);
    }

    public static Automovil[] filtrar(Automovil[] flota, TipoAutomovil criterio) {
        List<Automovil> resultado = new ArrayList<>();
        if (flota != null) {
            for (Automovil a : flota) {
                if (a != null && a.getTipoAutomovil() == criterio) resultado.add(a);
            }
        }
        return resultado.toArray(new Automovil[0]);
    }

    public static Automovil[] filtrar(Automovil[] flota, Color criterio) {
        List<Automovil> resultado = new ArrayList<>();
        if (flota != null) {
            for (Automovil a : flota) {
                if (a != null && a.getColor() == criterio) resultado.add(a);
            }
        }
        return resultado.toArray(new Automovil[0]);
    }

    public static int[] contarPorTipo(Automovil[] flota) {
        int[] contador = new int[TipoAutomovil.values().length];
        if (flota == null) {
            System.out.println("Advertencia: la flota es nula, se retorna el conteo en ceros.");
            return contador;
        }
        for (Automovil a : flota) {
            if (a == null || a.getTipoAutomovil() == null) continue;
            int indice = a.getTipoAutomovil().ordinal();
            if (indice < 0 || indice >= contador.length) continue;
            contador[indice]++;
        }
        return contador;
    }

    public static int getTotalAutomoviles() {
        return totalAutomoviles;
    }

    public static int getContadorRegistros() {
        return contadorRegistros;
    }

    public static Automovil masRapido(Automovil[] flota) {
        if (flota == null) return null;
        Automovil max = null;
        for (Automovil a : flota) {
            if (a == null) continue;
            if (max == null || a.getVelocidadActual() > max.getVelocidadActual()) {
                max = a;
            }
        }
        return max;
    }

    public static double promedioVelocidad(Automovil[] flota) {
        if (flota == null) return 0;
        double suma = 0;
        int count = 0;
        for (Automovil a : flota) {
            if (a == null) continue;
            suma += a.getVelocidadActual();
            count++;
        }
        return count == 0 ? 0 : suma / count;
    }

    public static int infraccionesPorExcesoDeVelocidad(Automovil[] flota) {
        int total = 0;
        if (flota == null) return total;
        for (Automovil a : flota) {
            if (a == null) continue;
            for (RegistroViaje r : a.getBitacora()) {
                if (r.getVelocidad() > LIMITE_LEGAL) total++;
            }
        }
        return total;
    }

    public static Automovil[] vehiculosPorPuertas(Automovil[] flota, int puertas) {
        List<Automovil> resultado = new ArrayList<>();
        if (flota != null) {
            for (Automovil a : flota) {
                if (a != null && a.getNumeroPuertas() == puertas) resultado.add(a);
            }
        }
        return resultado.toArray(new Automovil[0]);
    }

    public static void promedioMotorPorCombustible(Automovil[] flota, TipoCombustible combustible) {
        double suma = 0;
        int count = 0;
        if (flota != null) {
            for (Automovil a : flota) {
                if (a != null && a.getTipoCombustible() == combustible) {
                    suma += a.getMotor();
                    count++;
                }
            }
        }
        if (count == 0) {
            System.out.println("No hay vehiculos con combustible " + combustible + " para calcular el promedio.");
            return;
        }
        System.out.printf("Promedio de motor para combustible %s: %.2f litros (sobre %d vehiculos)%n",
                combustible, suma / count, count);
    }

    public static void tipoMasFrecuente(Automovil[] flota) {
        int[] contador = contarPorTipo(flota);
        int max = -1;
        for (int c : contador) if (c > max) max = c;

        if (max <= 0) {
            System.out.println("No hay datos suficientes para determinar el tipo mas frecuente.");
            return;
        }

        List<TipoAutomovil> empatados = new ArrayList<>();
        TipoAutomovil[] tipos = TipoAutomovil.values();
        for (int i = 0; i < contador.length; i++) {
            if (contador[i] == max) empatados.add(tipos[i]);
        }

        if (empatados.size() == 1) {
            System.out.println("Tipo mas frecuente: " + empatados.get(0) + " (" + max + " vehiculos)");
        } else {
            System.out.println("Hay empate (" + max + " vehiculos c/u) entre: " + empatados);
        }
    }

    public static void viajesConExcesoDeVelocidad(Automovil[] flota) {
        boolean encontrado = false;
        if (flota != null) {
            for (Automovil a : flota) {
                if (a == null) continue;
                for (RegistroViaje r : a.getBitacora()) {
                    if (r.getVelocidad() > LIMITE_LEGAL) {
                        System.out.println(r.describir());
                        encontrado = true;
                    }
                }
            }
        }
        if (!encontrado) {
            System.out.println("No se registraron viajes con exceso de velocidad.");
        }
    }
}