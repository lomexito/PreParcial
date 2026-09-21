package preparcial;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== Parte E.a) Simulacion de una ruta ===");
        Automovil demo = new Automovil("Chevrolet", 2022, 2.0,
                Automovil.TipoCombustible.GASOLINA, Automovil.TipoAutomovil.SUV,
                4, 5, 180, Automovil.Color.AZUL);

        demo.setVelocidadActual(100);
        System.out.println("Velocidad fijada: " + demo.getVelocidadActual() + " km/h");

        demo.acelerar(20);
        System.out.println("Tras acelerar 20: " + demo.getVelocidadActual() + " km/h");

        double horas = demo.tiempoEstimadoLlegada(300);
        System.out.printf("Tiempo estimado para 300 km: %.2f horas%n", horas);

        demo.desacelerar(50);
        System.out.println("Tras desacelerar 50: " + demo.getVelocidadActual() + " km/h");

        demo.frenar();
        System.out.println("Tras frenar: " + demo.getVelocidadActual() + " km/h");

        System.out.println("\n=== Parte E.b) Flota y estadisticas ===");
        Automovil[] flota = new Automovil[]{
                new Automovil("Renault", 2021, 1.6, Automovil.TipoCombustible.GASOLINA,
                        Automovil.TipoAutomovil.CARRO_DE_CIUDAD, 4, 5, 160, Automovil.Color.ROJO),
                new Automovil("Mazda", 2023, 2.0, Automovil.TipoCombustible.GASOLINA,
                        Automovil.TipoAutomovil.SUV, 4, 5, 190, Automovil.Color.NEGRO),
                new Automovil("Toyota", 2020, 1.8, Automovil.TipoCombustible.DIESEL,
                        Automovil.TipoAutomovil.FAMILIAR, 4, 7, 170, Automovil.Color.BLANCO),
                new Automovil("Kia", 2022, 1.4, Automovil.TipoCombustible.GAS_NATURAL,
                        Automovil.TipoAutomovil.COMPACTO, 4, 5, 150, Automovil.Color.AZUL),
                new Automovil("BYD", 2024, 1.5, Automovil.TipoCombustible.BIOETANOL,
                        Automovil.TipoAutomovil.SUV, 4, 5, 185, Automovil.Color.VERDE),
        };

        flota[0].setVelocidadActual(90);
        flota[1].setVelocidadActual(150);
        flota[1].acelerar(35);
        flota[2].setVelocidadActual(60);
        flota[3].setVelocidadActual(20);
        flota[4].setVelocidadActual(170);
        flota[4].acelerar(20);

        for (Automovil a : flota) a.mostrar();

        System.out.println("\nConteo por tipo (indexado por ordinal de TipoAutomovil):");
        int[] conteo = Automovil.contarPorTipo(flota);
        for (Automovil.TipoAutomovil t : Automovil.TipoAutomovil.values()) {
            System.out.println("  " + t + ": " + conteo[t.ordinal()]);
        }

        Automovil masRapido = Automovil.masRapido(flota);
        System.out.println("Vehiculo mas rapido: " +
                (masRapido == null ? "N/A" : masRapido.getMarca() + " " + masRapido.getModelo()
                        + " (" + masRapido.getVelocidadActual() + " km/h)"));

        System.out.printf("Velocidad promedio de la flota: %.2f km/h%n", Automovil.promedioVelocidad(flota));
        System.out.println("Infracciones por exceso de velocidad registradas: "
                + Automovil.infraccionesPorExcesoDeVelocidad(flota));
        System.out.println("Total de automoviles creados (incluye el de la demo): "
                + Automovil.getTotalAutomoviles());

        System.out.println("\n=== Parte E.c) Familias sobrecargadas ===");

        Automovil completo = new Automovil("Ford", 2019, 2.5, Automovil.TipoCombustible.DIESEL,
                Automovil.TipoAutomovil.EJECUTIVO, 4, 5, 200, Automovil.Color.NEGRO);
        Automovil reducido = new Automovil("Suzuki", 2023, 165);
        Automovil copia = new Automovil(completo);
        System.out.println("Constructor completo -> "); completo.mostrar();
        System.out.println("Constructor reducido -> "); reducido.mostrar();
        System.out.println("Constructor copia   -> "); copia.mostrar();

        System.out.println("\nSobrecargas de acelerar():");
        reducido.acelerar();
        System.out.println("  acelerar() -> " + reducido.getVelocidadActual());
        reducido.acelerar(15);
        System.out.println("  acelerar(15) -> " + reducido.getVelocidadActual());
        reducido.acelerar(10, 3);
        System.out.println("  acelerar(10, 3 veces) -> " + reducido.getVelocidadActual());

        System.out.println("\nSobrecargas de tiempoEstimadoLlegada():");
        System.out.printf("  tiempoEstimadoLlegada(120) -> %.2f h%n", reducido.tiempoEstimadoLlegada(120));
        System.out.printf("  tiempoEstimadoLlegada(120, 80) -> %.2f h%n", reducido.tiempoEstimadoLlegada(120, 80));
        System.out.printf("  tiempoEstimadoLlegada(120, 2, 15) -> %.2f h%n",
                reducido.tiempoEstimadoLlegada(120, 2, 15));

        System.out.println("\nSobrecargas de filtrar():");
        System.out.println("  Por combustible GASOLINA: " + contarNombres(
                Automovil.filtrar(flota, Automovil.TipoCombustible.GASOLINA)));
        System.out.println("  Por tipo SUV: " + contarNombres(
                Automovil.filtrar(flota, Automovil.TipoAutomovil.SUV)));
        System.out.println("  Por color AZUL: " + contarNombres(
                Automovil.filtrar(flota, Automovil.Color.AZUL)));

        System.out.println("\nBitacora detallada de flota[1] (" + flota[1].getMarca() + "):");
        flota[1].mostrar(true);

        System.out.println("\n=== Parte E.d) Ruta individual ===");

        int N = 56;
        int r = N % 4;
        System.out.println("N = " + N + " | r = N mod 4 = " + r);

        switch (r) {
            case 0: {
                System.out.println("Ruta 0: vehiculosPorPuertas");
                int puertasBuscadas = N % 3 + 2;
                System.out.println("Buscando vehiculos con " + puertasBuscadas + " puertas...");
                Automovil[] encontrados = Automovil.vehiculosPorPuertas(flota, puertasBuscadas);
                if (encontrados.length == 0) {
                    System.out.println("  No se encontraron vehiculos con esa cantidad de puertas.");
                } else {
                    for (Automovil a : encontrados) a.mostrar();
                }
                break;
            }
            case 1: {
                System.out.println("Ruta 1: promedioMotorPorCombustible");
                Automovil.TipoCombustible[] valores = Automovil.TipoCombustible.values();
                Automovil.TipoCombustible seleccionado = valores[N % valores.length];
                System.out.println("Combustible seleccionado (ordinal " + (N % valores.length) + "): " + seleccionado);
                Automovil.promedioMotorPorCombustible(flota, seleccionado);
                break;
            }
            case 2: {
                System.out.println("Ruta 2: tipoMasFrecuente");
                Automovil.tipoMasFrecuente(flota);
                break;
            }
            case 3: {
                System.out.println("Ruta 3: viajesConExcesoDeVelocidad");

                Automovil rutaDemo = new Automovil("Nissan", 2022, 220);
                rutaDemo.setVelocidadActual(100);
                rutaDemo.acelerar(30);
                Automovil[] flotaConDemo = Arrays.copyOf(flota, flota.length + 1);
                flotaConDemo[flota.length] = rutaDemo;
                Automovil.viajesConExcesoDeVelocidad(flotaConDemo);
                break;
            }
            default:
                break;
        }
    }

    private static String contarNombres(Automovil[] arreglo) {
        StringBuilder sb = new StringBuilder();
        sb.append(arreglo.length).append(" -> [");
        for (int i = 0; i < arreglo.length; i++) {
            sb.append(arreglo[i].getMarca());
            if (i < arreglo.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}