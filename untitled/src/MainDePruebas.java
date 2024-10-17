import com.daletguimel.conversor.historial.HistorialDeConversiones;
import com.daletguimel.conversor.historial.HistorialManager;
import com.daletguimel.conversor.respuestas.ExchangeRateResponse;
import com.daletguimel.conversor.consultas.SolicitudPairConversion;
import com.daletguimel.conversor.consultas.SolicitudStandard;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainDePruebas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<HistorialDeConversiones> historial = new ArrayList<>();
        HistorialManager historialManager = new HistorialManager();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        boolean continuar = true;

        // Cargar historial, si existe
        try {
            historial = historialManager.cargarHistorial();
        } catch (IOException e) {
            System.out.println("No se pudo cargar el historial: " + e.getMessage());
        }

        while (continuar) {

            System.out.println("\nBienvenido al Conversor de Monedas");
            System.out.println("Elige una opción:");
            System.out.println("1. Consulta estándar de cambio actual");
            System.out.println("2. Convertir una moneda a otra específica");
            System.out.println("3. Mostrar historial de conversiones");
            System.out.println("4. Salir");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingresa la moneda base (ej. ARS): ");
                    String monedaBase = scanner.next();
                    SolicitudStandard solicitudStandard = new SolicitudStandard(monedaBase);
                    try {
                        ExchangeRateResponse respuesta = solicitudStandard.obtenerDatos();
                        System.out.println(solicitudStandard.formatearRespuesta(respuesta));
                    } catch (IOException e) {
                        System.err.println("Error al realizar la solicitud: " + e.getMessage());
                    } catch (InterruptedException e) {
                        System.err.println("La solicitud fue interrumpida: " + e.getMessage());
                        Thread.currentThread().interrupt(); // Restablecer el estado de interrupción
                    }
                    break;
                case 2:
                    System.out.print("Ingresa la moneda base (ej. ARS): ");
                    String baseCurrency = scanner.next();
                    System.out.print("Ingresa la moneda objetivo (ej. USD): ");
                    String targetCurrency = scanner.next();
                    System.out.print("Ingresa el monto a convertir: ");
                    double amount = scanner.nextDouble();
                    SolicitudPairConversion solicitudPairConversion = new SolicitudPairConversion
                            (baseCurrency, targetCurrency, amount);
                    try {
                        ExchangeRateResponse respuesta = solicitudPairConversion.obtenerDatos();
                        System.out.println(solicitudPairConversion.formatearRespuesta(respuesta));

                        // Guardar en historial
                        LocalDateTime horaLocal = LocalDateTime.now();
                        String timeStamp = horaLocal.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
                        historial.add(new HistorialDeConversiones(baseCurrency, targetCurrency, amount,
                                respuesta.conversion_result(), timeStamp));

                    } catch (IOException e) {
                        System.err.println("Error al realizar la solicitud: " + e.getMessage());
                    } catch (InterruptedException e) {
                        System.err.println("La solicitud fue interrumpida: " + e.getMessage());
                        Thread.currentThread().interrupt(); // Restablecer el estado de interrupción
                    }
                    break;
                case 3:
                    System.out.println("\nHistorial de Conversiones: ");
                    for (HistorialDeConversiones conversion : historial) {
                        System.out.println("De " + conversion.getBaseCurrency() + " a " + conversion.getTargetCurrency() + " -> Cantidad: " + conversion.getAmount() +
                                " equivalen a:  " + conversion.getResult() + " / Fecha: " + conversion.getTimestamp());
                    }
                    break;
                case 4:
                    System.out.println("\nGracias por usar el Conversor de Monedas. ¡Hasta luego!");
                    continuar = false;
                    try {
                        historialManager.guardarHistorial(historial);
                    } catch (IOException e) {
                        System.err.println("No se pudo guardar el historial: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
        // Serializar historial a JSON (opcional)
        String historialJson = gson.toJson(historial);
        System.out.println("Historial en formato JSON: " + historialJson);

        scanner.close();
    }
}
