import com.daletguimel.conversor.consultas.ExchangeRateResponse;
import com.daletguimel.conversor.consultas.SolicitudPairConversion;
import com.daletguimel.conversor.consultas.SolicitudStandard;

import java.io.IOException;
import java.util.Scanner;

public class MainDePruebas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al Conversor de Monedas");
        System.out.println("Elige una opción:");
        System.out.println("1. Consulta estándar de cambio actual");
        System.out.println("2. Convertir una moneda a otra específica");

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                System.out.print("Ingresa la moneda base (ej. ARS): ");
                String monedaBase = scanner.next();
                SolicitudStandard solicitudStandard = new SolicitudStandard(monedaBase);
                try {
                    ExchangeRateResponse respuesta = solicitudStandard.obtenerDatos();
                    System.out.println(solicitudStandard.formatearRespuesta(respuesta));
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
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
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Opción no válida");
        }
        scanner.close();
    }
}
